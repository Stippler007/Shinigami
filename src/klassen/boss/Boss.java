/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.boss;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import klassen.Background;
import klassen.ImageFactory;
import klassen.karte.GameObjects;
import klassen.player.BasicShot;
import klassen.player.FireShot;
import klassen.player.Player;
import klassen.player.PlayerSpritzer;

/**
 *
 * @author Christian
 */
public abstract class Boss implements Serializable
{
  protected boolean isAlive;

  protected Player player;
  protected List<PlayerSpritzer> playerSpritzers;
  
  protected transient BufferedImage look[][];
  
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
  protected float animationTime;
  protected float maxAnimationTime;
  
  protected String imageName;
  protected int length;
  
  public Boss(float x, float y,float speed,float maxLive,
          GameObjects[][] map,Player player,List<PlayerSpritzer> playerSpritzers) 
  {
    this.x = x;
    this.y = y;
    this.speed=speed;
    this.player=player;
    this.playerSpritzers=playerSpritzers;
    this.live=maxLive;
    this.maxLive=maxLive;
    this.map=map;
    this.aggroBox=new Rectangle((int)x-300, (int)y-200, 600, 400);
    isAlive=true;
    bounding=new Rectangle((int)x, (int)y, 50, 50);
  }
  
  private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        BufferedImage img = ImageFactory.getIF().getLook(imageName);
        setLook(imageName, img.getWidth(), img.getHeight(),3);
    }

  public void setPlayer(Player player)
  {
    this.player = player;
  }

  public void setPlayerSpritzers(List<PlayerSpritzer> playerSpritzers)
  {
    this.playerSpritzers = playerSpritzers;
  }

  public void setMap(GameObjects[][] map)
  {
    this.map = map;
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

  public void setLook(String imageName,int width,int height,int length)
  {
    this.length=length;
    this.imageName = imageName;
    look=new BufferedImage[length][4];
    
    for (int i = 0; i < length; i++)
    {
      for (int j = 0; j < 4; j++)
      {
        look[i][j]=ImageFactory.getIF().getLook(imageName).getSubimage(i*width, j*height, width, height);
      }
    }
    bounding.width=look[0][0].getWidth();
    bounding.height=look[0][0].getHeight();
  }
  
  public Rectangle getBounding(){
    return bounding;
  }
  public void update(float tslf)
  {
    if(animationTime<=maxAnimationTime)animationTime+=tslf;
    else if(animationTime>maxAnimationTime)animationTime-=maxAnimationTime;
    
    x+=Player.speedX;
    y+=Player.speedY;
    
    x+=speedX*tslf;
    y+=speedY*tslf;
    
    playerSpritzerCollision(tslf);
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
  protected void moveZiel(float zielX,float zielY)
  {
    float speedX = (zielX) - (x+bounding.width/2);
    float speedY = (zielY) - (y+bounding.height/2);
    
    float help = (float)Math.sqrt(speedX*speedX+speedY*speedY);
    
    speedX/=help;
    speedY/=help;
    
    speedX*=speed;
    speedY*=speed;
    
    this.speedX+=speedX;
    this.speedY+=speedY;
  }
  public void collideMap(float tslf)
  {
    for (int i =(int)((Background.x+x)/25*-1)-2; i < (int)((Background.x+x)/25*-1)+34; i++) 
    {
      for (int j = (int)((Background.y+y)/25*-1)-2; j < (int)((Background.y+y)/25*-1)+26; j++) 
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
          }
          if(map[i][j].getBounding().intersects(bounding.x+bounding.width/2-1, bounding.y+bounding.height/2, 2, 1))
          {
            map[i][j].steppedOn(true);
          }
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
  public BufferedImage getLook()
  {
    int j=-1;
    double turn=getTurn();
    if(turn>=-Math.PI*0.25&&turn<=Math.PI*0.25)
    {
      j=1;
    }
    else if(turn>=Math.PI*0.25&&turn<=Math.PI*0.5){
      j=0;
    }
    else if(turn>=Math.PI*0.50&&turn<=Math.PI*1){
      j=2;
    }
    else{
      j=3;
    }
    for (int i = 0; i < look.length; i++) 
    {
      if(animationTime<(float)maxAnimationTime/(look.length)*(i))return look[i][j];
    }
    return look[0][0];
  }
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
