/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.player;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import klassen.ImageFactory;

/**
 *
 * @author Christian
 */
public class IceShot extends PlayerSpritzer
{
  private static BufferedImage looks[]=new BufferedImage[10];
  private BufferedImage look;
  
  static
  {
    for (int i = 0; i < looks.length; i++)
    {
      looks[i]=ImageFactory.getIF().getLook("Ice_Shot_0"+i);
    }
  }
  public IceShot(float x, float y, Rectangle bounding, float damage)
  {
    
    super(x, y, bounding, damage);
  }

  @Override
  public void update(float tslf)
  {
    super.update(tslf);
  }
  
  @Override
  public BufferedImage getLook()
  {
    return look;
  }
}
