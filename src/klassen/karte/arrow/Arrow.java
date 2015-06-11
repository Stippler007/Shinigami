/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.karte.arrow;

import java.awt.image.BufferedImage;
import klassen.ImageFactory;
import klassen.LevelDesign;
import klassen.karte.GameObjects;
import klassen.player.Player;

/**
 *
 * @author Stippler
 */
public class Arrow extends GameObjects {

    private transient LevelDesign ld;
    private int id;
    private float startX;
    private float startY;

    public Arrow(int brightness, int id, float startX, float startY, LevelDesign ld) {
        super(brightness);
        this.startX = startX;
        this.startY = startY;
        this.id = id;
        this.ld = ld;
        setImage("Gras0");
    }

    @Override
    public void playerSteppedOn(Player player) {
        ld.loadLevel(id, startX, startY);
        //ld.buildMap(id);
    }

    @Override
    public BufferedImage getLook() {
        return look;
    }

    @Override
    public void setBrightness(int brightness) {

    }
}
