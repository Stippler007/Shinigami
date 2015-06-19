/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.boss.hundeGhoul;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import klassen.Background;
import klassen.ImageFactory;
import klassen.boss.Boss;
import klassen.boss.BossSpritzer;
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
  
  private float speed;
  
  private List<Minion> minions;
  
  private float attackPatternSwitchTimer=0;
  private float attackPatternSwitchTimerMax=30;
  
  private List<BossSpritzer> bossSpritzer;
  
  public HundeGhoul(float x, float y, float speed,GameObjects map[][],Player player, float maxAnimationTime,List<BossSpritzer> bossSpritzer, List<Minion> minions,List<PlayerSpritzer> playerSpritzers)
  {
    super(x, y, speed, 200,
            map,player, playerSpritzers);
    this.x = x;
    this.y = y;
    this.speed = speed;
    this.maxAnimationTime = maxAnimationTime;
    this.minions = minions;
    this.bossSpritzer=bossSpritzer;
    setLook("BigMama", 100, 100,3);
  }
  
  @Override
  public void update(float tslf)
  {
    speedX=0;
    speedY=0;
    
    if(attackPatternSwitchTimer<attackPatternSwitchTimerMax/3)
    {
      attackPattern1();
      System.out.println("attackpattern1");
    }
    else if(attackPatternSwitchTimer<attackPatternSwitchTimerMax/3*2)
    {
      attackPattern2();
      System.out.println("attackpattern2");
    }
    else if(attackPatternSwitchTimer<attackPatternSwitchTimerMax/3*3)
    {
      attackPattern3();
      System.out.println("attackpattern3");
    }
    else
    {
      attackPatternSwitchTimer-=attackPatternSwitchTimerMax;
    }
    attackPatternSwitchTimer+=tslf;
    super.update(tslf);
  }
  private void attackPattern1()
  {
    // Er bewegt sich nicht und schießt Kegelförmig auf dich
    
  }
  private void attackPattern2()
  {
    moveZiel(Background.x+map.length*25/2, Background.y+map[0].length*25/2);
    // Er geht in die Mitte und macht rundschüsse
  }
  private void attackPattern3()
  {
    moveZiel(player.getBounding().x+player.getBounding().width/2, player.getBounding().y+player.getBounding().height/2);
  }

  @Override
  public void draw(Graphics2D g)
  {
    drawHealthBar(g);
    g.drawImage(this.getLook(), null, (int)x, (int)y);
  }
}