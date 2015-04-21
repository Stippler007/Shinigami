package klassen.karte;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import klassen.ImageFactory;
import klassen.Music;
import klassen.boss.Hund;
import klassen.player.Player;

/**
 *
 * @author Christian
 */
public class Gras extends GameObjects
{
  private static BufferedImage look;
  private BufferedImage lookChanged=null;

  static
  {
    look=ImageFactory.getIF().getLook("Gras0");
  }
  public Gras(int brightness)
  {
    super(brightness);
    bounding=new Rectangle(0,0,look.getWidth(),look.getHeight());
    solid=false;
  }

  @Override
  public void update(float tslf, float x, float y)
  {
    if(brightnessChanged)
    {
      setBrightness(brightness);
      brightnessChanged=false;
    }
    if(currentBrightnessChanged)
    {
      setBrightness(currentBrightness);
      currentBrightnessChanged=false;
    }
    super.update(tslf, x, y);
  }
  
  @Override
  public BufferedImage getLook()
  {
    if(lookChanged!=null)
    {
      return lookChanged;
    }
    return look;
  }

  @Override
  public void playerSteppedOn(Player player)
  {
    if(player.isMove())
    {
      Music.play().randomGrasStep();
    }
  }
  
  @Override
  public void setBrightness(int brightness) 
  {
    this.brightness=brightness;
    lookChanged=new BufferedImage(25,25,BufferedImage.TYPE_INT_ARGB);
    lookChanged.createGraphics().drawImage(look,0,0,null);
    RescaleOp rescaleOp = new RescaleOp(1f, brightness, null);
    rescaleOp.filter(lookChanged, lookChanged);
  }
}


//S. 291