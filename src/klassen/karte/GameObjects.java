/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.karte;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import klassen.player.Player;

/**
 *
 * @author Christian
 */
public abstract class GameObjects implements Serializable {
  
  protected int brightness=0;
  protected boolean brightnessChanged;
  
  protected int currentBrightness=0;
  protected boolean currentBrightnessChanged;
  
  protected boolean stepped=false;
  protected boolean solid=false;
  protected boolean heated=false;
  protected boolean frozen=false;
  protected boolean thorny=false;
  protected Rectangle bounding;

  public GameObjects(int brightness)
  {
    bounding=new Rectangle(0,0,25,25);
  }
  public void steppedOn(boolean stepped)
  {
    this.stepped=stepped;
  }

  public void update(float tslf,float x,float y)
  {
    if(currentBrightness!=brightness)
    {
      if(currentBrightness-20*tslf<brightness)
      {
        currentBrightness-=20*tslf;
      }
      else if(currentBrightness+20*tslf>brightness)
      {
        currentBrightness-=20*tslf;
      }
      else
      {
        currentBrightnessChanged=true;
        currentBrightness=brightness;
      }
    }
    
    bounding.x=(int)x;
    bounding.y=(int)y;
  }

  public void playerSteppedOn(Player player)
  {
    
  }
  
  public void setCurrentBrightness(int currentBrightness)
  {
    this.currentBrightness=currentBrightness;
    currentBrightnessChanged=true;
  }
  
  public void setBrightness(int brightness)
  {
    this.brightness=brightness;
    this.brightnessChanged=true;
  }
  
  public abstract BufferedImage getLook();
  
  public Rectangle getBounding()
  {
    return bounding;
  }
  public boolean isSolid()
  {
      return solid;
  }

  public boolean isStepped()
  {
    return stepped;
  }

  public boolean isHeated()
  {
    return heated;
  }

  public boolean isFrozen()
  {
    return frozen;
  }

  public boolean isThorny()
  {
    return thorny;
  }
}
