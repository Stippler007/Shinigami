/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.listener;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import klassen.TextManager;
import klassen.npc.NPC;
import klassen.player.Player;
import klassen.player.Player.Shots;

/**
 *
 * @author Christian
 */
public class KL implements KeyListener{

  public static boolean[] keys=new boolean[1024];
  private Player player;
  private LinkedList<NPC> npcs;

  public KL(Player player, LinkedList<NPC> npcs) {
    this.player = player;
    this.npcs = npcs;
  }
  
  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) 
  {
    keys[e.getKeyCode()]=true;
    if(e.getKeyCode()==KeyEvent.VK_SPACE)
    {
      if(TextManager.getInstance().isTexting())
      {
        TextManager.getInstance().nextText();
      }
      else
      {
        for (NPC npc : npcs)
        {
          if(npc.getBounding().intersects(player.getBounding().x-25,player.getBounding().y-25,75,75))
          {
            TextManager.getInstance().setText(npc.getText());
          }
        }
      }
    }
    if(e.getKeyCode()==KeyEvent.VK_1)
    {
      player.setShot(Shots.BASICSHOT);
    }
    if(e.getKeyCode()==KeyEvent.VK_2)
    {
      player.setShot(Shots.FLAMESHOT);
    }
    if(e.getKeyCode()==KeyEvent.VK_3)
    {
      player.setShot(Shots.ICESHOT);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) 
  {
    keys[e.getKeyCode()]=false;
  }
}
