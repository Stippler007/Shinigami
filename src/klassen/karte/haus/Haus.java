/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen.karte.haus;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import klassen.ImageFactory;
import klassen.LevelDesign;
import klassen.karte.GameObjects;

/**
 *
 * @author Stippler
 */
public class Haus extends GameObjects
{
  private static BufferedImage look;
  private transient BufferedImage lookChanged;
  
  private int x;
  private int y;
  
  
  private boolean door=false;
  
  public Haus(int brightness,int x,int y)
  {
    super(brightness, x, y);
    setImage("Haus");
    solid=true;
    this.x=x;
    this.y=y;
  }
  
  @Override
  public BufferedImage getLook() 
  {
    if(lookChanged!=null)return lookChanged;
    return look;
  }

  @Override
  public void setBrightness(int brightness)
  {
    this.brightness=brightness;
    lookChanged=new BufferedImage(25,25,BufferedImage.TYPE_INT_ARGB);
    lookChanged.createGraphics().drawImage(look.getSubimage(x*25, y*25, 25, 25),0,0,null);
    RescaleOp rescaleOp = new RescaleOp(1f, brightness, null);
    rescaleOp.filter(lookChanged, lookChanged);
  }
}
