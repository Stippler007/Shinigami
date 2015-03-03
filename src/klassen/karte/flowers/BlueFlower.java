/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.karte.flowers;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.imageio.ImageIO;
import klassen.ImageFactory;
import klassen.karte.GameObjects;

/**
 *
 * @author Christian
 */
public class BlueFlower extends GameObjects
{
  
  private static BufferedImage look[]=new BufferedImage[1];
  private BufferedImage lookChanged[]=null;
  
  private float animationTime=0f;
  private final float maxAnimationTime=1f;
  
  static
  {
    for (int i = 0; i < 1; i++)
    {
      look[i]=ImageFactory.getIF().getLook("blueFlower"+i);
    }
  }
  public BlueFlower(int brightness)
  {
    super(brightness);
  }
  @Override
  public void update(float tslf, float x, float y)
  {
    if(animationTime<maxAnimationTime)animationTime+=tslf;
    else animationTime-=maxAnimationTime;
    super.update(tslf, x, y);
  }
  
  @Override
  public BufferedImage getLook()
  {
    if(lookChanged!=null)
    {
      for (int i = 0; i < look.length; i++)
      {
        if(animationTime<maxAnimationTime/look.length*i)
        {
          return lookChanged[i];
        }
      }
      return lookChanged[0];
    }
    for (int i = 0; i < look.length; i++)
    {
      if(animationTime<maxAnimationTime/look.length*i)
      {
        return look[i];
      }
    }
    return look[0];
  }

  @Override
  public void setBrightness(int brightness) 
  {
    this.brightness=brightness;
    this.lookChanged=new BufferedImage[look.length];
    for (int i = 0; i < look.length; i++)
    {
      lookChanged[i]=new BufferedImage(25,25,BufferedImage.TYPE_INT_ARGB);
      lookChanged[i].createGraphics().drawImage(look[i],0,0,null);
      RescaleOp rescaleOp = new RescaleOp(1f, brightness, null);
      rescaleOp.filter(lookChanged[i], lookChanged[i]);
    }
  }
}
