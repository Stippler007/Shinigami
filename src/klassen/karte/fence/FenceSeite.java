/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.karte.fence;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import klassen.ImageFactory;
import klassen.karte.GameObjects;

/**
 *
 * @author Christian
 */
public class FenceSeite extends GameObjects {

    public FenceSeite(int brightness) {
        super(brightness);
        bounding = new Rectangle(0, 0, look.getWidth(), look.getHeight());
        solid = true;
        setImage("fenceSeite");
    }

    @Override
    public void update(float tslf, float x, float y) {
        bounding.x = (int) x;
        bounding.y = (int) y;
    }

    @Override
    public BufferedImage getLook() {
        return look;
    }

    @Override
    public boolean isSolid() {
        return solid;
    }

    @Override
    public Rectangle getBounding() {
        return bounding;
    }

    @Override
    public void setBrightness(int brighness) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
