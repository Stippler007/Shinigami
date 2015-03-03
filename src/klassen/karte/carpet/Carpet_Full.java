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
public class Carpet_Full extends GameObjects
{
  private BufferedImage look;
  
  public Carpet_Full(int brightness,int x,int y)
  {
    super(brightness);
    look=ImageFactory.getIF().getLook("Carpet_Full").getSubimage(x*25, y*25, 25, 25);
  }
  
  @Override
  public BufferedImage getLook()
  {
    return look;
  }

  @Override
  public void setBrightness(int brighness) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
}
