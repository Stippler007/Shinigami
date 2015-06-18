/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.player;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Stippler
 */
public class FireShotTrap extends PlayerSpritzer
{

  public FireShotTrap(float x, float y, Rectangle bounding, float damage)
  {
    super(x, y, bounding, damage);
  }

  @Override
  public BufferedImage getLook()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
}
