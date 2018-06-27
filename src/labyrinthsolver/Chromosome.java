/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthsolver;

import java.util.Random;

/**
 *
 * @author Pedro H. Gomes
 */
public class Chromosome {
    
    private Labyrinth labyrinth;
    private Robot robot;
    private Move[] steps;
    private int size;
    private int limit;
    private double score;
    
    public Chromosome(int size, Labyrinth labyrinth){
        this.score = 1000.0;
        this.size = size;
        this.labyrinth = labyrinth;
        this.robot = new Robot(labyrinth.getStart());
    }
    
    
    private void track(){
        
        int x = robot.getCoordinates().getX();
        int y = robot.getCoordinates().getY();
        
        String[][] labirinto = this.labyrinth.getLabyrinth(); 
        
        for(int i = 0; i < this.steps.length ; i++){
            if(steps[i].equals(Move.DOWN)){
                robot.getCoordinates().setY(y+1);
            }else if(steps[i].equals(Move.LEFT)){
                robot.getCoordinates().setX(x-1);
            }else if(steps[i].equals(Move.RIGHT)){
                robot.getCoordinates().setY(x+1);
            }else{//UP
                robot.getCoordinates().setY(y-1);
            }
            
            if(labirinto[x][y].equalsIgnoreCase("#")){
                this.robot.increaseCrashes();
            }
        }
    }

    
    public void randomize(){
        
        this.steps = new Move[this.size];
        Random r = new Random();
        int randomValue;
        
        for(int i = 0; i < this.size; i++){
            randomValue = r.nextInt(40); //numeros de 0 a 3
            if(0 <= randomValue && randomValue <= 9){
                this.steps[i] = Move.DOWN;
            }else if(10 <= randomValue && randomValue <= 19){
                this.steps[i] = Move.LEFT;
            }else if(20 <= randomValue && randomValue <= 29){
                this.steps[i] = Move.RIGHT;
            }else if(30 <= randomValue && randomValue <= 39){
                this.steps[i] = Move.UP;
            }else{
                System.out.println(randomValue);
                this.steps[i] = Move.INVALID;
            }
        }
    }

    public void display(){
    
        for(int i = 0; i < this.size; i++){
            System.out.print("["+this.steps[i].toString()+"]");
        }
    
    }
    
    public void evaluate(){
        track();
        double crashPenalty = 100;
        double endPrize = 500;
        int crashes = this.robot.getCrashed();  
        
        //atualiza o escore/fitness para cada batida.
        this.score = score - crashes*crashPenalty;
        
        //atualiza para quem atingiu o final
        if(this.robot.getCoordinates().equals(labyrinth.getEnd()))
            this.score = score + endPrize;
        
        //para a distancia em relação ao final 
        
     
    }
    
    public Move[] getSteps() {
        return steps;
    }

    public void setSteps(Move[] steps) {
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
