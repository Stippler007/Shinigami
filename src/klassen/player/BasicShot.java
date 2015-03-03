/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package klassen.player;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import klassen.ImageFactory;
import klassen.listener.ML;
import klassen.listener.MML;

/**
*
* @author Stippler
*/
public class BasicShot extends PlayerSpritzer
{

  private BufferedImage[] look=new BufferedImage[7];
  private Player player;

  private float speed;
  
  private float speedX;
  private float speedY;
  
  public Status status;
  
  public static boolean canShoot=true;
  
  public enum Status
  {
      CHARCHING,
      SHOOTING,
      IMPACT;
  }
  //Paul Shapera, viel weniger code, 
  public BasicShot(float x, float y, float speed,float maxAniTime,float damage,Player player) 
  {
    super(x, y,
            new Rectangle((int)x,(int)y,ImageFactory.getIF().getLook("BasicShot0").getWidth(),
                    ImageFactory.getIF().getLook("BasicShot0").getHeight()),damage);
    
    
    animationTime=0;
    maxAnimationTime=0.3f;
    
    maxAnimationTime=maxAniTime;
    this.speed=speed;
    this.player=player;
    for (int i = 0; i < look.length-1; i++) 
    {
        look[i]=ImageFactory.getIF().getLook("BasicShot_0"+i);
    }
    look[look.length-1]=ImageFactory.getIF().getLook("BasicShot0");
    bounding=new Rectangle((int)x,(int)y,look[0].getWidth(),look[0].getHeight());
    status=Status.CHARCHING;
    canShoot=false;
  }

  public void setStatus(Status status) 
  {
    if(status==Status.SHOOTING&&this.status==Status.CHARCHING)
    {
      int zielX=MML.x-player.getBounding().width/2;
      int zielY=MML.y-player.getBounding().height/2;
      speedX=zielX-x;
      speedY=zielY-y;
      float maxSpeed=Math.abs(speedX)+Math.abs(speedY);
      speedX/=maxSpeed;
      speedY/=maxSpeed;
      speedX*=speed;
      speedY*=speed;
      look=new BufferedImage[1];
      for (int i = 0; i < look.length; i++) {
          look[i]=ImageFactory.getIF().getLook("BasicShot"+i);
      }
      canShoot=true;
    }
    else if(status==Status.IMPACT)
    {

    }
    this.status = status;
  }
  @Override
  public void update(float tslf) {
    switch(status)
    {
      case CHARCHING:updateCharching(tslf);
        break;
      case SHOOTING:updateShooting(tslf);
        break;
      case IMPACT:updateImpact(tslf);
        break;
    }

    bounding.x=(int)x;
    bounding.y=(int)y;
  }
  private void updateCharching(float tslf)
  {
    double turn=Player.getTurn();
    if(turn>=-Math.PI*0.25&&turn<=Math.PI*0.25)
    {
      x=400-look[0].getWidth()/2+25;
      y=300-look[0].getHeight()/2;
    }
    else if(turn>=Math.PI*0.25&&turn<=Math.PI*0.5)
    {
      x=400-look[0].getWidth()/2;
      y=300-look[0].getHeight()/2+25;
    }
    else if(turn>=Math.PI*0.50&&turn<=Math.PI*1)
    {
      x=400-look[0].getWidth()/2-25;
      y=300-look[0].getHeight()/2;
    }
    else
    {
      x=400-look[0].getWidth()/2;
      y=300-look[0].getHeight()/2-25;
    }
    if(animationTime<maxAnimationTime)animationTime+=tslf;
    if(ML.leftMouseRealeased&&animationTime>=maxAnimationTime)
    {
      setStatus(Status.SHOOTING);
    }
  }
  private void updateShooting(float tslf)
  {
    move(tslf);
  }
  private void updateImpact(float tslf)
  {

  }
  private void move(float tslf)
  {
      x+=Player.speedX;
      y+=Player.speedY;
      x+=speedX*tslf;
      y+=speedY*tslf;
  }
  @Override
  public BufferedImage getLook() 
  {
    for (int i = 0; i < look.length; i++) 
    {
      if(animationTime<(float)maxAnimationTime/(look.length-1)*(i+1))return look[i];
    }
    return null;
  }
  
  public Status getStatus() 
  {
    return status;
  }

  @Override
  public Rectangle getBounding() 
  {
      return bounding;
  }
}
