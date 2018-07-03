/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthsolver;

/**
 *
 * @author Pedro H. Gomes
 */
public class LabyrinthSolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Labyrinth labyrinth = new Labyrinth("easy.txt");
        labyrinth.show();
        
        //int chromosomeSize = labyrinth.getMinSteps()*2;
        
        //Chromosome c = new Chromosome(chromosomeSize, labyrinth);
        //c.randomize();
        //c.display();
        //c.evaluate();
        //System.out.println(c.getRobot().getCoordinates().toString());
        
        
        
        GenethicAlgorithm GA = new GenethicAlgorithm(labyrinth);
        
        int numGenerations = 20; 
        int populationSize = 400;
        int chromosomeSize = labyrinth.getMinSteps()*2;
        
        GA.run(numGenerations, populationSize, chromosomeSize);
    }
    
}
