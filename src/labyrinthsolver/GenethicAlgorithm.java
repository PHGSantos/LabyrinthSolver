/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthsolver;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author User-PC
 */
public class GenethicAlgorithm {
    
    private ArrayList<Chromosome> population;
    private Labyrinth labyrinth;
    
    public GenethicAlgorithm(Labyrinth labyrinth){
        this.population = new ArrayList<>();
        this.labyrinth = labyrinth;
    }
    
    
    public void run(int numGenerations, int populationSize, int chromosomeSize){
        Chromosome best;
        ArrayList<Chromosome> newPopulation = new ArrayList<>();
        
        initializePopulation(populationSize, chromosomeSize);
        
        for(int currentGen = 0; currentGen < numGenerations; currentGen++){
            double evaluationsSum = this.evaluationsSum();
            newPopulation.clear();
            for(int indivudualsGenerated = 0; currentGen < numGenerations; currentGen+=2){
                int indexParent1 = selectParent(evaluationsSum);
                int indexParent2 = selectParent(evaluationsSum);
                
                Chromosome[] children = this.population.get(indexParent1).crossover(this.population.get(indexParent2));
                newPopulation.add(children[0].mutation());
                newPopulation.add(children[1].mutation());
            }
            this.population.clear();
            this.population.addAll(newPopulation);
            best = getBestSolution();
            System.out.println("Geração #"+currentGen+"->"+best+" com score " + best.getScore());
        }
    }
    
    public void initializePopulation(int populationSize, int chromosomeSize){
        this.population.clear();
        for(int i = 0; i < populationSize; i++){
            this.population.add(new Chromosome(chromosomeSize, this.labyrinth));
        }
    
        System.out.println("População criada com " + populationSize +" individuos");
    
    }
    
    public Chromosome getBestSolution(){
        double refScore = Double.NEGATIVE_INFINITY;
        Chromosome bestSolution = null;
        for (int i = 0; i < this.population.size(); i++){
            Chromosome c = this.population.get(i);
            double score = c.evaluate();
            if(score > refScore){
                bestSolution  = c;
            }
        }
        return bestSolution;
    }

    private double evaluationsSum() {
        double sum = 0;
        for (Chromosome c :this.population) {
            double score  = c.evaluate();
            sum = sum + score;
        }
        return sum;
    }

    private int selectParent(double evaluationsSum) {
        int x = -1;
        double randomValue = Math.random()*evaluationsSum;
        double sum = 0;
        Iterator <Chromosome> it = this.population.iterator();
        
        do{
            sum = sum + it.next().evaluate();
            x++;
        }while((it.hasNext()) && evaluationsSum < randomValue);
        
        return x;
    }
    
    
}
