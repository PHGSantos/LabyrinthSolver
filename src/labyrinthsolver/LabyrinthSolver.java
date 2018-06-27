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
        Labyrinth labyrinth = new Labyrinth("test_labyrinth1.txt");
        labyrinth.show();
        
        Chromosome c = new Chromosome(10, labyrinth);
        c.randomize();
        c.display();
    }
    
}
