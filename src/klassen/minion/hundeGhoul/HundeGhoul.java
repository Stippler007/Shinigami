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
  
  private static BufferedImage look[]=new BufferedImage[3];
  
  private float speed;
  
  private float animationTime;
  private float maxAnimationTime;
  
  private LinkedList<Minion> minions;
  
  static
  {
    for (BufferedImage look:look)
    {
      
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
  public BufferedImage getLook()
  {
    for (int i = 0; i < look.length; i++) 
    {
      if(animationTime<(float)maxAnimationTime/(look.length-1)*(i+1))return look[i];
    }
    return look[look.length-1];
  }
}
