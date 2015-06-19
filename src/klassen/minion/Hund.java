/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen.minion;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.sound.sampled.Control;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import klassen.Background;
import klassen.ImageFactory;
import klassen.karte.GameObjects;
import klassen.minion.Minion;
import klassen.minion.MinionSpritzer;
import klassen.player.Player;
import klassen.player.PlayerSpritzer;

/**
 *
 * @author Stippler
 */
public class Hund extends Minion{
  
//  private transient BufferedImage[][] attackingLook=new BufferedImage[2][4];
  
  private List<Minion> minions;
  private final float maxAnimationTime=0.3f;
  private float animationTime=0f;
  private double attackingTurn=0;
  
  
  public Hund(float x, float y,float speed,
          GameObjects[][] map,Player player,List<PlayerSpritzer> playerSpritzers,List<Minion> minions) {
    super(x,y,speed,100,map,player,playerSpritzers);
    this.speed=speed;
    this.minions=minions;
    super.setLook("hund", 30, 30, 4, 4);
    bounding=new Rectangle((int)x,(int)y,look[0][0].getWidth(),look[0][0].getHeight());
    damage=1000;
  }

  @Override
  public void setX(float x) 
  {
      this.x = x;
  }
  
  @Override
  public void setY(float y) 
  {
      this.y = y;
  }

  
//  protected void setLookAttacking(String imageName,int startX,int width,int height)
//  {
//    attackingLook=new BufferedImage[2][4];
//    for (int i = 0; i < 2; i++)
//    {
//      for (int j = 0; j < 4; j++)
//      {
//        attackingLook[i][j]=ImageFactory.getIF().getLook(imageName).getSubimage(startX*width+i*width, j*height, width, height);
//      }
//    }
//    bounding.width=width;
//    bounding.height=height;
//  }
  
  @Override
  public void collideMap(float tslf)
  {
    for (int i =(int)(Background.x/25*-1); i < (int)(Background.x/25*-1)+34; i++) 
    {
      for (int j = (int)(Background.y/25*-1); j < (int)(Background.y/25*-1)+26; j++) 
      {
        Rectangle help1=new Rectangle(bounding.x+(int)(speedX),bounding.y+(int)(speedY),bounding.width,bounding.height);
        
        if(!(i<0||j<0)&&!(i>map.length-1||j>map[0].length-1))
        {
          if(map[i][j].isSolid()&&help1.intersects(map[i][j].getBounding()))
          {
            Rectangle help2=map[i][j].getBounding();
            
            double vonlinks  = x + help1.width  - help2.x;
            double vonoben   = y + help1.height - help2.y;
            double vonrechts = help2.x + help2.width  - x;
            double vonunten  = help2.y + help2.height - y;


            if(vonlinks<vonoben&&vonlinks<vonrechts&&vonlinks<vonunten)
            {
              x-=vonlinks;
            }
            else if(vonoben<vonrechts&&vonoben<vonunten)
            {
              y-=vonoben;
            }
            else if(vonrechts<vonunten)
            {
              x+=vonrechts;
            }
            else
            {
              y+=vonunten;
            }

            attacking=false;
          }
          if(map[i][j].getBounding().intersects(bounding.x+bounding.width/2-1, bounding.y+bounding.height/2, 2, 1))
          {
            map[i][j].steppedOn(true);
          }
        }
      }
    }
  }
  
  @Override
  public void draw(Graphics2D g) 
  {
    drawHealthBar(g);
    g.drawImage(getLook(),null, (int)x,(int)y);
  }
  private float zielSpeedX;
  private float zielSpeedY;
  @Override
  public void update(float tslf) 
  {
    x+=Player.speedX;
    y+=Player.speedY;
    if(aggro)
    {
      if(attacking)
      {
        if(animationTime>maxAnimationTime&&animationTime<2*maxAnimationTime)
        {
          x+=zielSpeedX*tslf;
          y+=zielSpeedY*tslf;
        }
        else if(animationTime>maxAnimationTime*2)
        {
          animationTime=0;
          attacking=false;
        }
        animationTime+=tslf;
      }
      else
      {
        super.followPlayer();
        super.moveCockBack(tslf);
        attacking=startAttacking();
        if(animationTime<maxAnimationTime)animationTime+=tslf;
        else
        {
          animationTime-=maxAnimationTime;
        }
      }
      collidePlayer(tslf);
    }
    super.update(tslf);
    collideMinion();
  }
  private boolean startAttacking()
  {
    if(player.getBounding().intersects(x-40, y-40, 80+bounding.width, 80+bounding.height))
    {
      zielSpeedX = (player.getBounding().x+player.getBounding().width/2) - (x+bounding.width/2);
      zielSpeedY = (player.getBounding().y+player.getBounding().height/2) - (y+bounding.height/2);
      
      float help = (float)Math.sqrt(zielSpeedX*zielSpeedX+speedY*speedY);
      
      zielSpeedX/=help;
      zielSpeedY/=help;
      
      zielSpeedX*=speed*5;
      zielSpeedY*=speed*5;
      
      animationTime=0;
      
      attackingTurn=getTurn();
      
      return true;
    }
    return false;
  }
  private void collidePlayer(float tslf)
  {
    if(attacking&&player.getBounding().intersects(bounding))
    {
      Rectangle rect=player.getBounding();

      int nachrechts=(int)(rect.x+rect.width)-bounding.x;
      int nachlinks=(int)(bounding.x+bounding.width)-rect.x;
      int nachunten=(int)(rect.y+rect.height)-bounding.y;
      int nachoben=(int)(bounding.y+bounding.height)-rect.y;
      
      float knockback=700;
      
      if(nachrechts<nachlinks&&nachrechts<nachoben&&nachrechts<nachunten)
      {
        x+=nachrechts;
        player.setKnockbackX(knockback);
      }
      else if(nachlinks<nachoben&&nachlinks<nachunten)
      {
        x-=nachlinks;
        player.setKnockbackX(-knockback);
      }
      else if(nachoben<nachunten)
      {
        y-=nachoben;
        player.setKnockbackY(-knockback);
      }
      else if(nachoben>nachunten)
      {
        y+=nachunten;
        player.setKnockbackY(knockback);
      }
      player.damaged(damage*tslf);
      attacking=false;
    }
  }
  private void collideMinion()
  {
    for (Minion m:minions) 
    {
      if(!m.equals(this))
      {
        Rectangle help1=new Rectangle(bounding.x+(int)(speedX),bounding.y+(int)(speedY),bounding.width,bounding.height);
        if(help1.intersects(m.getBounding()))
        {
          Rectangle help2=m.getBounding();

          double vonlinks  = x + help1.width  - help2.x;
          double vonoben   = y + help1.height - help2.y;
          double vonrechts = help2.x + help2.width  - x;
          double vonunten  = help2.y + help2.height - y;


          if(vonlinks<vonoben&&vonlinks<vonrechts&&vonlinks<vonunten)
          {
            x-=vonlinks;
          }
          else if(vonoben<vonrechts&&vonoben<vonunten)
          {
            y-=vonoben;
          }
          else if(vonrechts<vonunten)
          {
            x+=vonrechts;
          }
          else
          {
            y+=vonunten;
          }
        }
      }
    }
  }
  @Override
  public Rectangle getBounding() 
  {
    return bounding;
  }

  @Override
  public BufferedImage getLook()
  {
    if(!attacking)return super.getLook();
    int j=-1;
    if(attackingTurn>=-Math.PI*0.25&&attackingTurn<=Math.PI*0.25)
    {
      j=1;
    }
    else if(attackingTurn>=Math.PI*0.25&&attackingTurn<=Math.PI*0.5){
      j=0;
    }
    else if(attackingTurn>=Math.PI*0.50&&attackingTurn<=Math.PI*1){
      j=2;
    }
    else{
      j=3;
    }
    for(int i = 0; i < 2; i++) 
    {
      if(animationTime<(float)maxAnimationTime/2*(i))
      {
        return look[i+2][j];
      } 
    }
    return look[3][j];
  }
}