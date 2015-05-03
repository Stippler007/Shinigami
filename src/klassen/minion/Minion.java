/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package klassen.minion;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import klassen.Background;
import klassen.karte.GameObjects;
import klassen.boss.Hund;
import klassen.player.BasicShot;
import klassen.player.FireShot;
import klassen.player.Player;
import static klassen.player.Player.speedX;
import static klassen.player.Player.speedY;
import klassen.player.PlayerSpritzer;

/**
*
* @author Christian
*/
public abstract class Minion 
{
  protected boolean isAlive;

  protected Player player;
  protected LinkedList<PlayerSpritzer> playerSpritzers;
  
  protected float x;
  protected float y;
  
  protected float live;
  protected float maxLive;
  
  protected float speed;
  protected float speedX;
  protected float speedY;
  
  protected boolean aggro=false;
  protected Rectangle aggroBox;
  
  protected float damage=100f;
  
  protected Rectangle bounding;
  
  protected GameObjects[][] map;
  
  public Minion(float x, float y,float speed,float maxLive,Rectangle bounding,
          GameObjects[][] map,Player player,LinkedList<PlayerSpritzer> playerSpritzers) 
  {
    this.x = x;
    this.y = y;
    this.speed=speed;
    this.bounding=bounding;
    this.player=player;
    this.playerSpritzers=playerSpritzers;
    this.live=maxLive;
    this.maxLive=maxLive;
    this.map=map;
    this.aggroBox=new Rectangle((int)x-300, (int)y-200, 600, 400);
    isAlive=true;
  }
  // So Act 1, S1: Overture Paul Shapera
  public void setX(float x) 
  {
    this.x = x;
  }
  public void setY(float y) 
  {
    this.y = y;
  }
  public void setIsAlive(boolean isAlive) 
  {
    this.isAlive = isAlive;
  }
  public Rectangle getBounding(){
    return bounding;
  }
  public void update(float tslf)
  {
    if(aggro)
    {
      playerSpritzerCollision(tslf);

      speedX*=tslf;
      speedY*=tslf;

      x+=speedX;
      y+=speedY;

      bounding.x=(int)x;
      bounding.y=(int)y;
    }
    else if(aggroBox.intersects(player.getBounding()))
    {
      aggro=true;
      aggroBox=null;
    }
    else
    {
      aggroBox.x=(int)x-300;
      aggroBox.y=(int)y-200;
    }
    collideMap(tslf);
    bounding.x=(int)x;
    bounding.y=(int)y;
  }
  public void playerSpritzerCollision(float tslf)
  {
    int i=0;
    while(i<playerSpritzers.size())
    {
      PlayerSpritzer playerSpritzer=playerSpritzers.get(i);
      if(playerSpritzer.getBounding().intersects(bounding))
      {
        if(playerSpritzer instanceof BasicShot&&((BasicShot)playerSpritzer).getStatus()==BasicShot.Status.SHOOTING)
        {
          Rectangle rect=playerSpritzer.getBounding();
          
          int nachrechts=(int)(rect.x+rect.width)-bounding.x;
          int nachlinks=(int)(bounding.x+bounding.width)-rect.x;
          int nachunten=(int)(rect.y+rect.height)-bounding.y;
          int nachoben=(int)(bounding.y+bounding.height)-rect.y;
          
          if(nachrechts<nachlinks&&nachrechts<nachoben&&nachrechts<nachunten)
          {
            x+=nachrechts;
            knockbackX=300;
          }
          else if(nachlinks<nachoben&&nachlinks<nachunten)
          {
            x-=nachlinks;
            knockbackX=-300;
          }
          else if(nachoben<nachunten)
          {
            y-=nachoben;
            knockbackY=-300;
          }
          else if(nachoben>nachunten)
          {
            y+=nachunten;
            knockbackY=300;
          }
          live-=playerSpritzer.getDamage();
          playerSpritzers.remove(i);
        }
        if(playerSpritzer instanceof FireShot)
        {
          live-=playerSpritzer.getDamage()*tslf;
          i++;
        }
        else
        {
          i++;
        }
      }
      else
      {
        i++;
      }
    }
  }
  public void collideMap(float tslf)
  {
    for (int i =(int)((Background.x+x)/25*-1)-2; i < (int)((Background.x+x)/25*-1)+34; i++) 
    {
      for (int j = (int)((Background.y+y)/25*-1)-2; j < (int)((Background.y+y)/25*-1)+26; j++) 
      {
        Rectangle help1=new Rectangle(bounding.x+(int)(speedX),bounding.y+(int)(speedY),bounding.width,bounding.height);
          System.out.println(i+" "+j);
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
        }
        if(map[i][j].getBounding().intersects(bounding.x+bounding.width/2-1, bounding.y+bounding.height/2, 2, 1))
        {
          map[i][j].steppedOn(true);
        }
      }
    }
  }
  private float knockbackX=0;
  private float knockbackY=0;
  private float backKnockback=300f;
  public void followPlayer()
  {
    speedX = (player.getBounding().x+player.getBounding().width/2) - (x+bounding.width/2);
    speedY = (player.getBounding().y+player.getBounding().height/2) - (y+bounding.height/2);
    
    float help = (float)Math.sqrt(speedX*speedX+speedY*speedY);
    
    speedX/=help;
    speedY/=help;
    
    speedX*=speed;
    speedY*=speed;
  }
  public void moveCockBack(float tslf)
  {
    if(knockbackX!=0)
    {
      if(knockbackX>0)
      {
        knockbackX-=backKnockback*tslf;
        if(knockbackX<0)knockbackX=0;
      }
      else{
        knockbackX+=backKnockback*tslf;
        if(knockbackX>0)knockbackX=0;
      }
    }
    
    if(knockbackY!=0)
    {
      if(knockbackY<0)
      {
        knockbackY+=backKnockback*tslf;
        if(knockbackY>0)knockbackY=0;
      }
      else{
        knockbackY-=backKnockback*tslf;
        if(knockbackY<0)knockbackY=0;
      }
    }
    x+=knockbackX*tslf;
    y+=knockbackY*tslf;
  }
  public abstract BufferedImage getLook();
  public void draw(Graphics2D g)
  {
    drawHealthBar(g);
    g.rotate( getTurn(),  bounding.x+bounding.width/2,  bounding.y+bounding.height/2);
//    g.drawImage(m.getLook(), null, m.getBounding().x, m.getBounding().y);
    g.setColor(Color.black);
    g.fill(bounding);
    g.rotate(-getTurn(),  bounding.x+bounding.width/2,  bounding.y+bounding.height/2);
  }
  public double getTurn()
  {
    double a=(player.getBounding().x+player.getBounding().width/2)-(bounding.x+bounding.width/2);
    double b=(player.getBounding().y+player.getBounding().height/2)-(bounding.y+bounding.height/2);

    double turn=Math.atan(b/a);
    if(a<0){
      turn+=2.3561944901923;
    }
     return turn; 
  }
  protected void drawHealthBar(Graphics2D g)
  {
    g.setColor(Color.RED);
    g.drawRect((int)x,(int) y-3, bounding.width, 2);
    g.setColor(Color.GREEN);
    g.drawRect((int)x,(int) y-3, (int)((float)bounding.width*(live/maxLive)), 2);
  }
  public float getLive()
  {
    return live;
  }
  public float getX() 
  {
    return x;
  }
  public float getY() {
      return y;
  }
  public boolean isAlive(){
    return isAlive;
  }
}