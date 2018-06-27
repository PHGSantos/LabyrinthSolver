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
public class Robot {
     /*
        L - left
        R - right
        U - up
        D - down
    */
    
    private Coordinates coordinates;
    private int crashed;
    
    public Robot(Coordinates c){
        this.coordinates = c;
        this.crashed = 0;
    }

    public void increaseCrashes(){
        this.crashed++;
    }
    
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    
    public int getCrashed() {
        return crashed;
    }

    public void setCrashed(int crashed) {
        this.crashed = crashed;
    }
    
    
}
