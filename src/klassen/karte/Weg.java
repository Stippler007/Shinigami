/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.karte;

import java.awt.image.BufferedImage;

/**
 *
 * @author Stippler
 */
public class Weg extends GameObjects {

    public Weg(int brightness) {
        super(brightness);
        setImage("Weg0");
    }

    @Override
    public BufferedImage getLook() {
        return look;
    }

    @Override
    public void setBrightness(int brighness) {

    }
}
