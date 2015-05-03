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
public class Tree extends GameObjects
{
  private static BufferedImage look;
  private transient BufferedImage lookChanged=null;
  
  private int x;
  private int y;
  
  static
  {
    look=ImageFactory.getIF().getLook("Tree");
  }
  public Tree(int brightness,int x,int y)
  {
    super(brightness);
    this.x=x;
    this.y=y;
    lookChanged=look.getSubimage(x*25, y*25, 25, 25);
    solid=true;
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
    lookChanged=new BufferedImage(25,25,BufferedImage.TYPE_INT_ARGB);
    lookChanged.createGraphics().drawImage(look.getSubimage(x*25, y*25, 25, 25),0,0,null);
    RescaleOp rescaleOp = new RescaleOp(1f, brightness, null);
    rescaleOp.filter(lookChanged, lookChanged);
  }
}
