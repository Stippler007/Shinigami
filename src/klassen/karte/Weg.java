/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen.karte;

import java.awt.image.BufferedImage;
import klassen.ImageFactory;

/**
 *
 * @author Stippler
 */
public class Weg extends GameObjects
{
  private static BufferedImage look;
  
  static
  {
    look=ImageFactory.getIF().getLook("Weg0");
  }

  public Weg(int brightness) {
    super(brightness);
  }
  
  @Override
  public BufferedImage getLook() {
    return look;
  }

  @Override
  public void setBrightness(int brighness) 
  {
    
  }
}
