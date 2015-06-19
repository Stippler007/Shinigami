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
        setImage("Wand0Brightness",25,5);
        solid = true;
    }

    @Override
    public void update(float tslf, float x, float y) {
        bounding.x = (int) x;
        bounding.y = (int) y;
    }


    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public Rectangle getBounding() {
        return bounding;
    }

}


//S. 291
