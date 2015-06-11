package klassen.karte;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import klassen.ImageFactory;
import klassen.karte.GameObjects;

/**
 *
 * @author Christian
 */
public class Wand extends GameObjects {

    public Wand(int brightness) {
        super(brightness);
        setImage("Wand0");
        bounding = new Rectangle(0, 0, look.getWidth(), look.getHeight());
        solid = true;
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

    }
}


//S. 291
