
package klassen.npc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import klassen.Background;
import klassen.ImageFactory;
import klassen.karte.GameObjects;
import klassen.player.Player;

public abstract class NPC implements Serializable
{
  protected String text;
  
  protected Player player;
  
  protected float x;
  protected float y;
  
  protected float speed;
  protected float speedX;
  protected float speedY;
  
  protected Rectangle bounding;
  protected BufferedImage look[][];
  protected GameObjects[][] map;
  
  protected float animationTime;
  protected float maxAnimationTime;
  
  public NPC(float x, float y,float speed,
          GameObjects[][] map,Player player,String text) 
  {
    this.x = x;
    this.y = y;
    this.speed=speed;
    this.bounding=new Rectangle((int)x,(int)y,50,50);
    this.player=player;
    this.map=map;
    this.text=text;
  }

  public void setX(float x) 
  {
    this.x = x;
  }

  public void setY(float y) 
  {
    this.y = y;
  }

  public void setMap(GameObjects[][] map)
  {
    this.map = map;
  }
  
  public Rectangle getBounding(){
    return bounding;
  }

  public void setLook(String imageName,int width,int height)
  {
    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 4; j++)
      {
        look[i][j]=ImageFactory.getIF().getLook(imageName).getSubimage(i*width, j*height, width, height);
      }
    }
    bounding.width=width;
    bounding.height=height;
  }
  
  public void update(float tslf)
  {
    x+=Player.speedX;
    y+=Player.speedY;
    
    speedX*=tslf;
    speedY*=tslf;
    
    x+=speedX;
    y+=speedY;
    
    collideMap(tslf);
    
    bounding.x=(int)x;
    bounding.y=(int)y;
  }
  
  public void collideMap(float tslf)
  {
    for (int i =(int)Math.abs((x*-1+Background.x)/25)-1; i < (int)Math.abs((x*-1+Background.x)/25)+4; i++) 
    {
      for (int j = (int)Math.abs((y*-1+Background.y)/25)-1; j < (int)Math.abs((y*-1+Background.y)/25)+4; j++) 
      {
        Rectangle help1=new Rectangle(bounding.x+(int)(speedX),bounding.y+(int)(speedY),bounding.width,bounding.height);
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
        if(map[i][j].getBounding().intersects(bounding.x+bounding.width/2, bounding.y+bounding.height/2, 2, 1))
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
  public void draw(Graphics2D g)
  {
    g.rotate( getTurn(),  bounding.x+bounding.width/2,  bounding.y+bounding.height/2);
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
  
  public float getX() 
  {
    return x;
  }

  public float getY() {
      return y;
  }
  
  public String[] getText()
  {
    return text.split("\n");
  }
  public BufferedImage getLook()
  {
    int i=-1;
    double turn=getTurn();
      if(turn>=-Math.PI*0.25&&turn<=Math.PI*0.25)
      {
        i=0;
      }
      else if(turn>=Math.PI*0.25&&turn<=Math.PI*0.5){
        i=1;
      }
      else if(turn>=Math.PI*0.50&&turn<=Math.PI*1){
        i=2;
      }
      else{
        i=3;
      }
    for (int j = 0; j < look.length; j++) 
    {
      if(animationTime<(float)maxAnimationTime/(look.length-1)*(i+1))return look[i][j];
    }
    return look[0][0];
  }
}
