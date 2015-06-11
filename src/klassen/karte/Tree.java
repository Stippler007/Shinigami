/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.karte;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

/**
 *
 * @author Stippler
 */
public class Tree extends GameObjects {

    public Tree(int brightness, int x, int y) {
        super(brightness, x, y);
        setImage("Tree");
        solid = true;
    }

    @Override
    public void setBrightness(int brightness) {
        look = new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
        look.createGraphics().drawImage(look.getSubimage(subX * 25, subY * 25, 25, 25), 0, 0, null);
        RescaleOp rescaleOp = new RescaleOp(1f, brightness, null);
        rescaleOp.filter(look, look);
    }
}
