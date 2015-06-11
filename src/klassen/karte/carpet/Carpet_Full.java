/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.karte.carpet;

import java.awt.image.BufferedImage;
import klassen.karte.GameObjects;

/**
 *
 * @author Christian
 */
public class Carpet_Full extends GameObjects {

    public Carpet_Full(int brightness, int x, int y) {
        super(brightness, x, y);
        setImage("Carpet_Full");
    }

    @Override
    public BufferedImage getLook() {
        return look;
    }
}
