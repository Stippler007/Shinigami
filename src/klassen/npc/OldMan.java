/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.npc;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import klassen.ImageFactory;
import klassen.karte.GameObjects;
import klassen.player.Player;

/**
 *
 * @author Christian
 */
public class OldMan extends NPC
{
  private boolean move=true;
  private boolean attacking=false;
  private float animationTime=0;
  private float maxAnimationTime=0.3f;
  
  public OldMan(float x, float y, float speed, GameObjects[][] map, Player player, String text)
  {
    super(x, y, speed, map, player, text);
    setLook("opi", 50, 50);
  }
  
  @Override
  public void update(float tslf)
  {
    if(speedX!=0||speedY!=0)move=true;
    else move=false;
    
    if(animationTime<maxAnimationTime)
    {
      animationTime+=tslf;
    }
    else
    {
      animationTime-=maxAnimationTime;
    }
    
    super.update(tslf);
  }
  
  

//  @Override
//  public double getTurn()
//  {
//    double a=0;
//    double b=0;
//    if(move)
//    {
//      a=speedX;
//      b=speedY;
//    }
//    else
//    {
//      a=(player.getBounding().x+player.getBounding().width/2)-(bounding.x+bounding.width/2);
//      b=(player.getBounding().y+player.getBounding().height/2)-(bounding.y+bounding.height/2);
//    }
//    
//    double turn=Math.atan(b/a);
//    if(a<0){
//      turn+=2.3561944901923;
//    }
//     return turn;
//  }
  
  @Override
  public void draw(Graphics2D g)
  {
    g.drawImage(getLook(), (int)x, (int)y, null);
  }
}
