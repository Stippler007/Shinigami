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
public class FenceHintenMid extends GameObjects
{
    public FenceHintenMid(int brightness)
  {
    super(brightness);
    setImage("FenceHintenMidBrightness",25,5);
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
