/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.karte.fence;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import klassen.ImageFactory;
import klassen.karte.GameObjects;

/**
 *
 * @author Christian
 */
public class FenceVorneRechts extends GameObjects {

    public FenceVorneRechts(int brightness) {
        super(brightness);
        setImage("FenceVorneRechtsBrightness",25,5);
        solid = true;
        
    }

    @Override
    public void update(float tslf, float x, float y) {
        bounding.x = (int) x;
        bounding.y = (int) y;
    }


    @Override
    public boolean isSolid() {
        return solid;
    }

    @Override
    public Rectangle getBounding() {
        return bounding;
    }

}
