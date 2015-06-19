/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen.karte;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import klassen.ImageFactory;

/**
 *
 * @author Stippler
 */
public class Boden extends GameObjects
{
  public Boden(int brightness) 
  {
    super(brightness);
      setImage("Ground_Wood_PlanksBrightness",25,5);
  }
  
  
//  @Override
//  public BufferedImage getLook() {
//    return look;
//  }
//  
//  @Override
//  public void setBrightness(int brighness) 
//  {
//    RescaleOp rescaleOp = new RescaleOp(1f, brighness, null);
//    rescaleOp.filter(look, look);
//  }
}
