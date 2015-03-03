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
  private boolean move=true;
  private BufferedImage look[]=new BufferedImage[3];
  private boolean attacking=false;
  private float animationTime=0;
  private float maxAnimationTime=0.3f;
  
  public Guard(float x, float y, float speed, GameObjects[][] map, Player player, String text)
  {
    super(x, y, speed, new Rectangle((int)x, (int)y, 
                                     ImageFactory.getIF().getLook("Guard_hinten_00").getWidth(),
                                     ImageFactory.getIF().getLook("Guard_hinten_00").getHeight()),
                                     map, player, text);
    
    speedY=50;
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
  
  @Override
  public BufferedImage getLook()
  {

    if(attacking)
    {
      
    }
    else
    {
      double turn=getTurn();
      if(turn>=-Math.PI*0.25&&turn<=Math.PI*0.25)
      {
        for (int i = 0; i < look.length; i++)
        {
          look[i]=ImageFactory.getIF().getLook("Guard_seite_0"+i);
        }
      }
      else if(turn>=Math.PI*0.25&&turn<=Math.PI*0.5){
        for (int i = 0; i < look.length; i++) {
          look[i]=ImageFactory.getIF().getLook("Guard_vorne_0"+i);
        }
      }
      else if(turn>=Math.PI*0.50&&turn<=Math.PI*1){
        for (int i = 0; i < look.length; i++) {
          look[i]=ImageFactory.getIF().getLook("Guard_seite_links_0"+i);
        }
      }
      else{
        for (int i = 0; i < look.length; i++) 
        {
          look[i]=ImageFactory.getIF().getLook("Guard_hinten_0"+i);
        }
      }
    }
    if(move){
      for (int i = 0; i < look.length-1; i++) {
        if(animationTime<(float)maxAnimationTime/(look.length-1)*(i+1))return look[i+1];
      }
    }
    return look[0];
  }

  @Override
  public double getTurn()
  {
    double a=speedX;
    double b=speedY;

    double turn=Math.atan(b/a);
    if(a<0){
      turn+=2.3561944901923;
    }
     return turn; 
  }
  
  @Override
  public void draw(Graphics2D g)
  {
    g.drawImage(getLook(), (int)x, (int)y, null);
  }
}
