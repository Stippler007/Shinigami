/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.player;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import klassen.ImageFactory;
import klassen.minion.Minion;

/**
 *
 * @author Stippler
 */
public class FireShotTrap extends PlayerSpritzer
{
  private BufferedImage look;
  private List<PlayerSpritzer> spritzers;
  public boolean alive=true;
  
  public FireShotTrap(float x, float y, float speed, float damage,Player player,List<PlayerSpritzer> spritzers,List<Minion> minions)
  {
    super(x, y, new Rectangle((int)x, (int)y,ImageFactory.getIF().getLook("FireMine").getWidth(),
            ImageFactory.getIF().getLook("FireMine").getHeight()), damage);
    this.spritzers=spritzers;
    look=ImageFactory.getIF().getLook("FireMine");
  }
  public void explode()
  {
    for(float x1 = 0; x1 <= Math.PI * 2; x1 += Math.PI / 6)
    {
      float speedX = (float)Math.cos(x1) * 300;
      float speedY = (float)Math.sin(x1) * 300;
      spritzers.add(new FireShot(x,y,speedX/3,speedY/3,0,damage/2,spritzers));
    }
    alive=false;
  }

  public boolean isAlive()
  {
    return alive;
  }
  
  @Override
  public BufferedImage getLook()
  {
    return look;
  }
}
