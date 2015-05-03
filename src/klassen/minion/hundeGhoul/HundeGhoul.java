/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.minion.hundeGhoul;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import klassen.ImageFactory;
import klassen.boss.Boss;
import klassen.karte.GameObjects;
import klassen.minion.Minion;
import klassen.player.Player;
import klassen.player.PlayerSpritzer;

/**
 *
 * @author Christian
 */
public class HundeGhoul extends Boss
{
  private float x;
  private float y;
  
  private static BufferedImage look[]=new BufferedImage[2];
  
  private float speed;
  
  private float animationTime;
  private float maxAnimationTime;
  
  private LinkedList<Minion> minions;
  
  private float attackPatternSwitchTimer=0;
  private float attackPatternSwitchTimerMax=30;
  
  static
  {
    for (int i = 1; i <= look.length; i++)
    {
      look[i]=ImageFactory.getIF().getLook("BigMamaVorne0"+i);
    }
  }
  public HundeGhoul(float x, float y, float speed,GameObjects map[][],Player player, float maxAnimationTime, LinkedList<Minion> minions,LinkedList<PlayerSpritzer> playerSpritzers)
  {
    super(x, y, speed, 200, new Rectangle((int)x,(int)y,look[0].getWidth(),look[0].getHeight()), map,player, playerSpritzers);
    this.x = x;
    this.y = y;
    this.speed = speed;
    this.animationTime = animationTime;
    this.maxAnimationTime = maxAnimationTime;
    this.minions = minions;
  }

  @Override
  public void update(float tslf)
  {
    if(attackPatternSwitchTimer<attackPatternSwitchTimerMax/3)
    {
      attackPattern1();
    }
    else if(attackPatternSwitchTimer<attackPatternSwitchTimerMax/3*2)
    {
      attackPattern2();
    }
    else if(attackPatternSwitchTimer<attackPatternSwitchTimerMax/3*3)
    {
      attackPattern3();
    }
    else
    {
      attackPatternSwitchTimer-=attackPatternSwitchTimerMax;
    }
    super.update(tslf);
  }
  private void attackPattern1()
  {
    // Er bewegt sich nicht und schießt Kegelförmig auf dich
    
  }
  private void attackPattern2()
  {
    // Er geht in die Mitte und macht rundschüsse
  }
  private void attackPattern3()
  {
    moveZiel(player.getBounding().x+player.getBounding().width/2, player.getBounding().y+player.getBounding().height/2);
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
}
