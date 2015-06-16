/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen.karte.fence;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import klassen.karte.GameObjects;

/**
 *
 * @author ffrie_000
 */
public class FenceHintenRechts extends GameObjects
{
    public FenceHintenRechts(int brightness)
  {
    super(brightness);
    setImage("FenceHintenRechts");
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
