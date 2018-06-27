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
public class Coordinates {
    
    private int x,y;
    
    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object o){
        Coordinates c = (Coordinates) o;
        if(this.getX() == c.getX() && this.getY() == c.getY())
            return true;
        
        return false;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    
}
