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
  
  private float animationTime=0f;
  private final float maxAnimationTime=1f;
  
  
  public BlueFlower(int brightness)
  {
    super(brightness);
      setImage("blueFlower");
  }
  @Override
  public void update(float tslf, float x, float y)
  {
    if(animationTime<maxAnimationTime)animationTime+=tslf;
    else animationTime-=maxAnimationTime;
    super.update(tslf, x, y);
  }
  
}
