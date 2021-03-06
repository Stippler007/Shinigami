/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import javax.swing.JFrame;
import klassen.boss.Boss;
import klassen.boss.BossSpritzer;
import klassen.karte.GameObjects;
import klassen.listener.KL;
import klassen.minion.Minion;
import klassen.minion.MinionSpritzer;
import klassen.boss.hundeGhoul.HundeGhoul;
import klassen.minion.Hund;
import klassen.npc.Guard;
import klassen.npc.NPC;
import klassen.npc.OldMan;
import klassen.npc.Sign;
import klassen.player.BasicShot;
import klassen.player.FireShot;
import klassen.player.FireShotTrap;
import klassen.player.IceShot;
import klassen.player.Player;
import klassen.player.PlayerSpritzer;

/**
 *
 * @author Christian
 */
public class Main 
{
  
  
  
  public static boolean pause=false;
  public static float tslf;
  
  public static void main(String[] args) 
  {
    int width=800;
    int height=600;
    
//    Music.m.playCanonInD();
    
    Background bg=new Background(0,0);
    
//    Music.play().canonInD();
    
    LinkedList<NPC> npcs=new LinkedList<>();
    
    LinkedList<PlayerSpritzer> playerSpritzers=new LinkedList<PlayerSpritzer>();
    
    LinkedList<BossSpritzer> bossSpritzer=new LinkedList<BossSpritzer>();
    LinkedList<Boss> boss=new LinkedList<Boss>();
    
    LinkedList<MinionSpritzer> minionSpritzer=new LinkedList<MinionSpritzer>();
    LinkedList<Minion> minions=new LinkedList<Minion>();
    Player player=new Player(width,height,300,playerSpritzers,npcs,bg.getMap(),minions);
    
//    LevelDesign ld=new LevelDesign(player,bg,minions,npcs,playerSpritzers);
    LevelDesign ld=LevelDesign.getLevelDesign();
    ld.setLevelDesign(player, bg, minions, npcs, playerSpritzers);
    
    // BackX -847 BackY -1045
    // SpawnX -582 SpawnY -529
    // ld.loadLevel(0, -800, -800);
    ld.loadLevel(0, -582, -529);
//    ld.buildMap(3);
//     ld.loadLevel(0, -800, -800);
//     boss.add(new HundeGhoul(0, 0, 300, bg.getMap(), player, 3, minions, playerSpritzers));
//    ld.loadLevel(2, -582, -529);
    //ld.buildMap(2);
//    boss.add(new HundeGhoul(tslf, tslf, tslf, map, player, tslf, minions, playerSpritzers))
    
//    npcs.add(new OldMan(800, 800, 100, bg.getMap(), player, "Willst mein dick?"));
//    npcs.add(new Sign(900, 800, bg.getMap(), player, "Willst mein dick?"));
//    npcs.add(new Guard(100, 100, 100, bg.getMap(), player, "Willst mein dick?"));
//    minions.add(new Hund(500, 500, 100, bg.getMap(), player, playerSpritzers, minions));
//    minions.add(new Hund(1000,1400,110,bg.getMap(),player,playerSpritzers));
    
    GUI f=new GUI(player,playerSpritzers,boss,bossSpritzer,minions,minionSpritzer,npcs,bg,ld);
    
    f.setUndecorated(true);
    f.setVisible(true);
    f.setSize(800,600);
    f.setResizable(false);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setLocationRelativeTo(null);
    f.setFullscreen();
//    
////    try
////    {
//      String path=System.getProperty("user.dir")+File.separator+"texts"+File.separator+"tutorial"+File.separator+"Tutorial.txt";
//      TextManager.getInstance().setText(DAL.getInstance().loadText(path));
////    }
////    catch (FileNotFoundException ex)
////    {
////      JOptionPane.showMessageDialog(f, ex.toString(), "ES funkt nicht", 0);
////    }
//    
    f.makeStrat();
//    
//    Thread gui=new Thread(f);
//    gui.start();
//////    
//    boss.add(new HundeGhoul(0, 0,100, bg.getMap(), player,0.5f, bossSpritzer, minions, playerSpritzers));
    Thread levelDesign=new Thread(ld);
    levelDesign.start();
//    
//    
//    
    long lastFrame=System.currentTimeMillis();
    while(true)
    {
      long thisFrame=System.currentTimeMillis();
      tslf=(float)(thisFrame-lastFrame)/1000;
      lastFrame=thisFrame;
      
      
      
     if(player.getHealth()>0)player.update(tslf);
      bg.update(tslf); //Muss immer als erstes stehn wegen der Kollisionsabfrage
      for(int i=0;i<playerSpritzers.size();i++)
      {
        playerSpritzers.get(i).update(tslf);

      }
      for (Boss b : boss)
      {
        b.update(tslf);
      }
      for(Minion m:minions)
      {
        m.update(tslf);
      }
      for(MinionSpritzer ms:minionSpritzer)
      {
        ms.update(tslf);
      }
      for (NPC npc : npcs) 
      {
        npc.update(tslf);
      }
//      System.out.println(tslf);
//      Main.collideMinionPlayerSpritzer(minionSpritzer, playerSpritzers);
      Main.collidePlayerSpritzerMap(bg.getMap(), playerSpritzers);
      deleteShit(playerSpritzers,minionSpritzer, minions);
      System.out.println("penis");
      f.repaintScreen();
//      System.out.println(tslf);
      
      try {Thread.sleep(10);} catch (InterruptedException ex) {}
      
      
      if(KL.keys[KeyEvent.VK_ESCAPE])System.exit(0);
    }
  }
  
  private static void deleteShit(LinkedList<PlayerSpritzer> playerSpritzers,LinkedList<MinionSpritzer> minionSpritzers,LinkedList<Minion> minions)
  {
    int i=0;
    while(i<playerSpritzers.size())
    {
      if(playerSpritzers.get(i) instanceof FireShot)
      {
        if(!((FireShot)playerSpritzers.get(i)).isAlive())
        {
          playerSpritzers.remove(i);
        }
        else
        {
          i++;
        }
      }
      else if(playerSpritzers.get(i) instanceof IceShot)
      {
        if(!((IceShot)playerSpritzers.get(i)).isAlive())
        {
          playerSpritzers.remove(i);
        }
        else
        {
          i++;
        }
      }
      else if(playerSpritzers.get(i) instanceof FireShotTrap)
      {
        if(!((FireShotTrap)playerSpritzers.get(i)).isAlive())
        {
          playerSpritzers.remove(i);
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
    
    i=0;
    while(i<minions.size())
    {
      if(minions.get(i).getLive()<=0)
      {
        minions.remove(i);
        i--;
      }
      i++;
    }
  }
  
  private static void collideMinionPlayerSpritzer(LinkedList<MinionSpritzer> minionSpritzer,LinkedList<PlayerSpritzer> playerSpritzer)
  {
    for (int i = 0; i < minionSpritzer.size(); i++) 
    {
      for (int j = 0; j < playerSpritzer.size(); j++) 
      {
        try
        {
          if(minionSpritzer.get(i).getBounding().intersects(playerSpritzer.get(j).getBounding()))
          {
            minionSpritzer.get(i).setAlive(false);
            minionSpritzer.remove(i);
            playerSpritzer.remove(j);
          }
        }
        catch(Exception ex)
        {
          
        }
      }
    }
  }
  
  
  public static void collidePlayerSpritzerMap(GameObjects map[][],LinkedList<PlayerSpritzer> playerSpritzers)
  {
    for (int i =(int)(Background.x/25*-1); i < (int)(Background.x/25*-1)+34; i++) 
    {
      for (int j = (int)(Background.y/25*-1); j < (int)(Background.y/25*-1)+26; j++) 
      {
        if(!(i<0||j<0)&&!(i>map.length-1||j>map[0].length-1))
        {
          int k=0;
          while(k<playerSpritzers.size())
          {
            if(playerSpritzers.get(k).getBounding().intersects(map[i][j].getBounding()))
            {
              if(playerSpritzers.get(k) instanceof BasicShot&&map[i][j].isSolid())
              {
                BasicShot b=(BasicShot)playerSpritzers.get(k);
                if(b.getStatus()==BasicShot.Status.SHOOTING)
                {
                  playerSpritzers.remove(k);
                }
                else if(b.getStatus()==BasicShot.Status.CHARCHING||b.getStatus()==BasicShot.Status.IMPACT)
                {
                  k++;
                }
              }
              else if(playerSpritzers.get(k) instanceof FireShot)
              {
                FireShot f=(FireShot)playerSpritzers.get(k);
                
                map[i][j].setBrightness(100);
                
                if(map[i][j].isSolid())playerSpritzers.remove(k);
                else k++;
              }
              else if(map[i][j].isSolid())
              {
                playerSpritzers.remove(k);
              }
              else
              {
                k++;
              }
            }
            else
            {
              k++;
            }
          }
        }
      }
    }
  }
}
