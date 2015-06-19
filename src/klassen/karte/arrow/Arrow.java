/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.karte.arrow;

import klassen.Background;
import klassen.LevelDesign;
import klassen.karte.GameObjects;
import klassen.player.Player;

/**
 *
 * @author Stippler
 */
public class Arrow extends GameObjects {

    private String id;
    private float startX;
    private float startY;

    public Arrow(int brightness, float startX, float startY) {
        super(brightness);
        this.startX = startX;
        this.startY = startY;
        setImage("GrasBrightness",25,5);
    }

    public void setID(String id) {
        this.id = id;
    }
    
    public void setStartX(float x) {
        this.startX = x;
    }
    
    public void setStartY(float y) {
        this.startY = y;
    }
    
    @Override
    public void playerSteppedOn(Player player) {
        Background.x = startX;
        Background.y = startY;
        LevelDesign.getLevelDesign().loadNext(id);
    }

}
