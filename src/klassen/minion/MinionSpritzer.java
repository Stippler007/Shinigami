/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.minion;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Christian
 */
public abstract class MinionSpritzer {
    
    protected boolean alive;
    
    protected float x;
    protected float y;
    
    protected Rectangle bounding;
    
    public MinionSpritzer(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    
    
    public abstract Rectangle getBounding();
    public abstract void update(float tslf);
    public abstract BufferedImage getLook();
    
    public abstract void setX(float x);
    public abstract void setY(float y);
    
    public double getTurn(){
        double a=(400)-(bounding.x+bounding.width/2);
        double b=(300)-(bounding.y+bounding.height/2);
        
        double turn=Math.atan(b/a);
        if(a<0){
            turn+=135;
        }
        
        return turn;
    }
    public abstract float getX();
    public abstract float getY();

    public boolean isAlive() {
        return alive;
    }
}
