/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.karte.carpet;

import java.awt.image.BufferedImage;
import klassen.ImageFactory;
import klassen.LevelDesign;
import klassen.karte.GameObjects;
import klassen.player.Player;

/**
 *
 * @author Christian
 */
public class FootCarpet extends GameObjects {

    //Passwort asdf1234
    private LevelDesign ld;
    private int id;
    private float backX;
    private float backY;

    public FootCarpet(int brightness, int x, int y, float backX, float backY, int id, LevelDesign ld) {
        super(brightness, x, y);
        setImage("FootCarpet");
        this.ld = ld;
        this.id = id;
        this.backX = backX;
        this.backY = backY;
    }

    @Override
    public void playerSteppedOn(Player player) {
        ld.loadLevel(id, backX, backY);
    }

    @Override
    public BufferedImage getLook() {
        return look;
    }

}
//brotcrunsher
