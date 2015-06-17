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
public class Guard extends NPC
{
  
  public Guard(float x, float y, float speed, GameObjects[][] map, Player player, String text)
  {
    super(x, y, speed,map, player, text);
    setLook("guard", 50, 50);
  }

  @Override
  public void update(float tslf)
  {
    speedY=50;
    
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
//    double a=speedX;
//    double b=speedY;
//
//    double turn=Math.atan(b/a);
//    if(a<0){
//      turn+=Math.PI;
//    }
//     return turn; 
//  }
  
  @Override
  public void draw(Graphics2D g)
  {
    g.drawImage(getLook(), (int)x, (int)y, null);
  }
}
