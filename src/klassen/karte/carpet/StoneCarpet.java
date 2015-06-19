/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.karte.carpet;

import java.awt.image.BufferedImage;
import klassen.ImageFactory;
import klassen.karte.GameObjects;

/**
 *
 * @author Christian
 */
public class StoneCarpet extends GameObjects {

    public StoneCarpet(int brightness, int x, int y) {
        super(brightness);
        setImage("Stone_Floor_FullBrightness",75,5);
    }

    

}
