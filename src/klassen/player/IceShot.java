/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.player;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import klassen.ImageFactory;
import klassen.listener.MML;
import klassen.minion.Minion;

/**
 *
 * @author Christian
 */
public class IceShot extends PlayerSpritzer
{
  private boolean alive=true;
  
  private BufferedImage look[]=new BufferedImage[10];
  private List<PlayerSpritzer> spritzers;
  private List<Minion> minions;
  
  private int zielX;
  private int zielY;
  private int n;
  
   
  
  public IceShot(float x, float y, float speed, float damage,Player player,List<PlayerSpritzer> spritzers,List<Minion> minions)
  {
    super(x, y, new Rectangle((int)x, (int)y,ImageFactory.getIF().getLook("IceShot0").getWidth(),
            ImageFactory.getIF().getLook("IceShot0").getHeight()), damage);
    this.spritzers=spritzers;
    
    for (int i = 0; i < look.length; i++)
    {
      look[i]=ImageFactory.getIF().getLook("IceShot"+i);
    }
    
    super.speed=speed;
    n=2;
    
    super.damage=damage;
    int zielX=MML.x-7;
    int zielY=MML.y-7;
    speedX=zielX-x;
    speedY=zielY-y;
    float maxSpeed=Math.abs(speedX)+Math.abs(speedY);
    speedX/=maxSpeed;
    speedY/=maxSpeed;
    speedX*=speed;
    speedY*=speed;
    this.minions=minions;
    
    maxAnimationTime=0.3f;
  }

  public IceShot(float x, float y, float speedX, float speedY, int n, float damage,List<PlayerSpritzer> spritzers) 
  {
    super(x, y, new Rectangle((int)x, (int)y,ImageFactory.getIF().getLook("IceShot0").getWidth(),
            ImageFactory.getIF().getLook("IceShot0").getHeight()), damage/2);
    for (int i = 0; i < look.length; i++)
    {
      look[i]=ImageFactory.getIF().getLook("IceShot"+i);
    }
    this.n=n;
    super.speedX=speedX;
    super.speedY=speedY;
    super.damage=damage/2;
    this.spritzers=spritzers;
  }
  
  @Override
  public void update(float tslf) 
  {
    x+=speedX*tslf;
    y+=speedY*tslf;
    
    if(minions!=null)
    {
      for (Minion m:minions)
      {
        if(m.getBounding().intersects(bounding))
        {
          explode();
        }
      }
    }
    
    if(alive&&animationTime>maxAnimationTime)
    {
      explode();
    }
    else
    {
      animationTime+=tslf;
    }
    
    super.update(tslf);
  }
  private void explode()
  {
    if(n>0)
      {
        for(float x1 = 0; x1 <= Math.PI * 2; x1 += Math.PI / 3)
        {
          float speedX = (float)Math.cos(x1) * 300;
          float speedY = (float)Math.sin(x1) * 300;
          spritzers.add(new IceShot(x,y,speedX/3,speedY/3,n-1,damage/2,spritzers));
         
        }
      }
      else
      {
        animationTime-=maxAnimationTime;
      }
      alive=false;
     
  }
  @Override
  public BufferedImage getLook()
  {
    for (int i = 0; i < look.length; i++) 
    {
      if(animationTime<(float)maxAnimationTime/(look.length-1)*(i+1))return look[i];
    }
    return look[look.length-1];
  }

  public boolean isAlive()
  {
    return alive;
  }
}
