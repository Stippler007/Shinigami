package klassen.karte;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import klassen.ImageFactory;
import klassen.Music;
import klassen.minion.Hund;
import klassen.player.Player;

/**
 *
 * @author Christian
 */
public class Gras extends GameObjects {

    public Gras(int brightness) 
    {
      super(brightness);
      bounding = new Rectangle(0, 0, 25, 25);
      solid = false;
//        setBrightness(0);
      setImage("GrasBrightness");
      setBrightness(0);
    }

//    @Override
//    public void update(float tslf, float x, float y) {
//        if (brightnessChanged) {
//            setBrightness(brightness);
//            brightnessChanged = false;
//        }
//        if (currentBrightnessChanged) {
//            setBrightness(currentBrightness);
//            currentBrightnessChanged = false;
//        }
//        super.update(tslf, x, y);
//    }

    @Override
    public void playerSteppedOn(Player player)
    {
        if (player.isMove()) {
            Music.play().randomGrasStep();
        }
    }

//    @Override
//    public void setBrightness(int brightness) {
//        this.brightness = brightness;
//        look = new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
//        look.createGraphics().drawImage(look, 0, 0, null);
//        RescaleOp rescaleOp = new RescaleOp(1f, brightness, null);
//        rescaleOp.filter(look, look);
//    }
}


//S. 291
