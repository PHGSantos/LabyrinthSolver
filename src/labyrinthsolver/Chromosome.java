/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthsolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro H. Gomes
 */
public class Chromosome {
    
    private Labyrinth labyrinth;
    private Robot robot;
    private ArrayList<String> steps;
    private int size;
    private int limit; //numero minimo de passos para resolver o labirinto
    private double score;
    
    
    
    public Chromosome(int size, Labyrinth labyrinth){
        this.score = 1000.0;
        this.size = size;
        this.labyrinth = labyrinth;
        this.limit = labyrinth.getMinSteps();
        this.robot = new Robot(labyrinth.getStart());
        this.steps = new ArrayList<>();
        
    }
    
    
    public void track(){
        
        int x,y; 
        
        String[][] labirinto = this.labyrinth.getLabyrinth(); 
        
        //System.out.println("Inicio:("+this.robot.getCoordinates().getX()+","+this.robot.getCoordinates().getY()+")");
        
        for(int i = 0; i < this.steps.size() ; i++){
            
            x = robot.getCoordinates().getX();//linhas
            y = robot.getCoordinates().getY();//colunas
            int n;//cordenada de teste
            switch (steps.get(i)) {
                case "DOWN":
                    n = x + 1;
                    if (!this.labyrinth.isWall(n, y)){
                        robot.getCoordinates().setX(x++);
                    }else{
                        this.robot.increaseCrashes();
                    }
                    break;
                case "LEFT":
                    n = y - 1;
                    if (!this.labyrinth.isWall(x, n)){
                        y--;
                        robot.getCoordinates().setY(y);
                    }else{
                        this.robot.increaseCrashes();
                    }
                    break;
                case "RIGHT":
                    n = y + 1;
                    if (!this.labyrinth.isWall(x, n)){
                        y++;
                        robot.getCoordinates().setY(y);
                    }else{
                        this.robot.increaseCrashes();
                    }
                    break;
                case "UP":
                    n = x - 1;
                    if (!this.labyrinth.isWall(n, y)){
                        x--;
                        robot.getCoordinates().setX(x);
                    }else{
                        this.robot.increaseCrashes();
                    }
                    break;
            //se for parado, faz nada
                default:
                    break;
            }
            
            //System.out.println("step #"+i+":"+"("+x+","+y+")");
        }
    }

    
    public void randomize(){
        
        //this.steps = new ArrayList<>();
        Random r = new Random();
        int randomValue;
        
        for(int i = 0; i < this.size; i++){
            randomValue = r.nextInt(50); //numeros de 0 a 3
            if(0 <= randomValue && randomValue <= 9){
                this.steps.add(i, "DOWN");
            }else if(10 <= randomValue && randomValue <= 19){
                this.steps.add(i, "LEFT");
            }else if(20 <= randomValue && randomValue <= 29){
                this.steps.add(i, "RIGHT");
            }else if(30 <= randomValue && randomValue <= 39){
                this.steps.add(i, "UP");
            }else if(40 <= randomValue && randomValue <= 49){
                this.steps.add(i, "STOPPED");    
            }else{
                System.out.println("Ramdomization Error: random >= 50 (r = "+randomValue);
            }
        }
    }

    public void display(){
    
        for(int i = 0; i < this.size; i++){
            System.out.print("["+this.steps.get(i)+"]");
        }
    
    }
    
    public double evaluate(){
        track();
        double crashPenalty = 20;
        double endPrize = 50;
        double distancePrize = 30;
        double stepsPenalty = 10;
        
        System.out.println("\nStart:"+score);
        
        int crashes = this.robot.getCrashed();  
        
        //atualiza o escore/fitness para cada batida.
        this.score = score - crashes*crashPenalty;
        System.out.println("Crashes:"+score);
        
        //atualiza para quem atingiu o final
        if(this.robot.getCoordinates().equals(labyrinth.getEnd())){
            this.score = score + endPrize;
            System.out.println("End:"+score);
        }
        
        
        //para a distancia em relação ao final - distancia manhattan 
        
        int xr = this.robot.getCoordinates().getX();
        int yr = this.robot.getCoordinates().getY();
        
        int xe = this.labyrinth.getEnd().getX();
        int ye = this.labyrinth.getEnd().getY();
        
        
        double distance = Math.abs(xr-xe) + Math.abs(yr-ye); //|x1-x2|+|y1-y2|
        this.score = score + (1/distance)*distancePrize;
        System.out.println("Manhattan:"+score + " | distance = "+distance);
         
        //recompensar pelo numero de passos gastos?
        int difference = this.steps.size() - this.limit;
        this.score = score - difference*stepsPenalty;
        System.out.println("Difference:"+score);
        
        return score;
    }
    
    //Crossover com 1 ponto de corte: Cromossomo A é o this, e o Cromossomo B é o other 
    public Chromosome[] crossover(Chromosome other){
        
        Chromosome[] filhos = new Chromosome[2];
        
        if(this.size < limit || other.getSize() < limit){
            System.out.println("CROSSOVER FAIL");
            return null;
        }
        
        //sorteia os pontos de corte
        Random r = new Random();
        int cut_point_A = r.nextInt(this.size);//retorna um int aleatorio entre 0 e size-1;
        int cut_point_B = r.nextInt(other.getSize());
        
        //System.out.println("CPA:"+cut_point_A);
        //System.out.println("CPB:"+cut_point_B);
        
        //System.out.println("C1:"+this.steps.size());
        //System.out.println("C2:"+other.getSteps().size());
        
        
        String s1 = null;
        
        List A_LEFT = this.steps.subList(0, cut_point_A);
        
        List A_RIGHT = this.steps.subList(cut_point_A, this.steps.size());
                
        List B_LEFT =  other.getSteps().subList(0, cut_point_B);
        
        List B_RIGHT =  other.getSteps().subList(cut_point_B, other.getSteps().size());

        s1 = A_LEFT.toString() +" "+ B_RIGHT.toString();
        String s2 = s1.replace(",", "").replace("[", "").replace("]", "");
        
        s1= null;
        
        s1 = A_LEFT.toString() +" "+ B_RIGHT.toString();
        String s3 = s1.replace(",", "").replace("[", "").replace("]", "");
        
        String[] v1 = s2.split(" ");
        String[] v2 = s3.split(" ");
        
       
        ArrayList<String> l1 = new ArrayList<>(); 
        ArrayList<String> l2 = new ArrayList<>();
        
        for(int i = 0; i < v1.length; i++){
            l1.add(i, v1[i]);
            l2.add(i, v2[i]);
        }
       
        Chromosome child1 = new Chromosome(l1.size(), this.labyrinth);
        child1.setSteps(l1);
        Chromosome child2 = new Chromosome(l2.size(), this.labyrinth);
        child2.setSteps(l2);
        
        filhos[0] = child1;
        filhos[1] = child2;
        
        return filhos;
    }
    
    //Mutar o cromossomo inteiro?(da no mesmo que randomizar denovo) Ou mutar uma parte específica? Ou mutar somente se houver batida?
    public Chromosome mutation(){
        
        double change_rate = 0.500;
        double add_rate = 0.750;
        double remove_rate = 1.000;
        double mutation_rate = Math.random(); //numero entre 0.0 e 1.0
        //3 Possibilidades -> trocar movimento, adicionar elemento no vetor, ou removê-lo.
        
        Chromosome child = null;
            
            if(0.0 <= mutation_rate && mutation_rate <= change_rate){ //[0.0,0.6]
                
                child = mutation_change_steps(this.steps, mutation_rate);//muda tudo
            
            }else if(change_rate < mutation_rate && mutation_rate <= add_rate){ //(0.6,0.8]
                
                child = mutation_add_step(this.steps, mutation_rate); //add no final
            
            }else if(add_rate < mutation_rate && mutation_rate <= remove_rate){//(0.8,1.0]
                
                //Se steps.size() < limit, mata o filho?
                child = mutation_remove_step(this.steps);//remove no final
            
            
            }else{
                System.out.println("EVOLUTION ERROR: mutation_rate > 1.0");
            }
        
        
        return child;
    }

    public Robot getRobot() {
        return robot;
    }

    public double getScore() {
        return score;
    }
    
    public Chromosome mutation_change_steps(ArrayList<String> child_steps,double x){
        
        int range = child_steps.size();
        
        for(int i = 0; i < range; i++){
            if(0.0 <= x && x <= 0.1){
                child_steps.set(i, "DOWN");
            }else if(0.1 < x && x <= 0.2){
                child_steps.set(i, "LEFT");
            }else if(0.2 < x && x <= 0.3){
                child_steps.set(i, "RIGHT");
            }else if(0.3 < x && x <= 0.4){
                child_steps.set(i, "UP");
            }else if(0.4 < x && x <= 0.5){
                child_steps.set(i, "STOPPED");    
            }
        }
        
        Chromosome child = new Chromosome(child_steps.size(),this.labyrinth);
        child.setSteps(child_steps);
        
        return child;
    }
    
    //A posição que eu removo tem que ser aleatória? estou removendo sempre o ultimo;
    public Chromosome mutation_add_step(ArrayList<String> child_steps, double x){
          
        //No Inicio, tamanho do filho = tamanho do pai, mas como a ideia é adiciona +1 ao filho, temos:
        //Se Pai = {A B C}  Filho ={A B C D} -> Last(F) = Last(P) + 1 = (Size(P) - 1) + 1 -> Last(filho) = Size(pai) 
        int last_index = child_steps.size();
        
        if(0.500 < x && x <= 0.550){
            child_steps.add(last_index, "DOWN");
        }else if(0.550 < x && x <= 0.600){
            child_steps.add(last_index, "LEFT");
        }else if(0.600 < x && x <= 0.650){
            child_steps.add(last_index, "RIGHT");
        }else if(0.650 < x && x <= 0.700){
            child_steps.add(last_index, "UP");
        }else if(0.700 < x && x <= 0.750){
            child_steps.add(last_index, "STOPPED");    
        }
           
       Chromosome child = new Chromosome(child_steps.size(),this.labyrinth); 
       child.setSteps(child_steps);
       
       return child;
    }
    
    public Chromosome mutation_remove_step(ArrayList<String> child_steps){
          
        int last_index = child_steps.size();
        child_steps.remove(last_index-1);
        
        Chromosome child = new Chromosome(child_steps.size(),this.labyrinth);
        child.setSteps(child_steps);
        
        return child;
    
    }
    
    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Labyrinth getLabyrinth() {
        return labyrinth;
    }

    public void setLabyrinth(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }
    
}
