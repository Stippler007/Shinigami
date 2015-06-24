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
import klassen.player.FireShotTrap;
import klassen.player.Player;
import klassen.player.PlayerSpritzer;

/**
*
* @author Christian
*/
public abstract class Minion implements Serializable
{
  protected boolean isAlive;
  
  protected Player player;
  protected List<PlayerSpritzer> playerSpritzers;
  protected boolean attacking=false;
  
  protected float x;
  protected float y;
  
  protected float live;
  protected float maxLive;
  
  protected float speed;
  protected float speedX;
  protected float speedY;
  
  protected boolean aggro=false;
  protected Rectangle aggroBox;
  
  protected boolean moving=true;
  
  protected float animationTime=0;
  protected float maxAnimationTime=0.3f;
  
  protected float damage=100f;
  
  protected Rectangle bounding;
  
  protected GameObjects[][] map;
  protected transient BufferedImage look[][]=new BufferedImage[4][4];
  protected String imageTag;
  
  
  
  public Minion(float x, float y,float speed,float maxLive,
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
    bounding=new Rectangle((int)x, (int)y, 30, 30);
  }
  
  private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        look=new BufferedImage[4][4];
        BufferedImage bi = ImageFactory.getIF().getLook(imageTag);
        setLook(imageTag, bi.getWidth()/4, bi.getHeight()/4, 4, 4);
    }
  
  public void setLook(String imageName,int width,int height)
  {
      System.out.println(imageName);
      imageTag = imageName;
    for (int i = 0; i < look.length; i++)
    {
      for (int j = 0; j < look[i].length; j++)
      {
          System.out.println(look[i][j]);
          System.out.println(imageName);
          System.out.println(imageTag);
          BufferedImage bi = ImageFactory.getIF().getLook(imageTag);
          System.out.println(bi);
          look[i][j]=ImageFactory.getIF().getLook(imageTag).getSubimage(i*width, j*height, width, height);
      }
    }
    bounding.width=width;
    bounding.height=height;
  }
  
  public void setMap(GameObjects[][] map) {
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
  public Rectangle getBounding(){
    return bounding;
  }
  public void update(float tslf)
  {
    if(!attacking)
    {
      if(animationTime<=maxAnimationTime-maxAnimationTime/10)animationTime+=tslf;
      else if(animationTime>maxAnimationTime-maxAnimationTime/10)animationTime-=maxAnimationTime;
    }
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
        else if(playerSpritzer instanceof FireShotTrap)
        {
          ((FireShotTrap)playerSpritzer).explode();
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
        if(!(i<0||j<0)&&!(i>map.length-1||j>map[0].length-1))
        {
          Rectangle help1=new Rectangle(bounding.x+(int)(speedX),bounding.y+(int)(speedY),bounding.width,bounding.height);
//          System.out.println(i+" "+j);
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
        else
        {
          System.out.println("Out of map");
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
  public void setLook(String imageName,int width,int height,int x,int y)
  {
    imageTag = imageName;
      look=new BufferedImage[x][y];
    for (int i = 0; i < x; i++)
    {
      for (int j = 0; j < y; j++)
      {
        look[i][j]=ImageFactory.getIF().getLook(imageName).getSubimage(i*width, j*height, width, height);
      }
    }
    
    bounding.width=width;
    bounding.height=height;
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
  public BufferedImage getLook()
  {
    if(player == null) {
        return look[0][0];
    }
      
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
    if(moving&&speedX!=0||speedY!=0)
    {
      for(int i = 0; i < 2; i++) 
      {
        if(animationTime<(float)maxAnimationTime/(2)*(i+1))
        {
          return look[i][j];
        } 
      }
    }
    else if(j!=-1)
    {
      return look[0][j];
    }
    System.out.println("No image found! " + j);
    return look[0][0];
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