/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.npc;

import klassen.karte.GameObjects;
import klassen.player.Player;

/**
 *
 * @author Christian
 */
public class FatGuy extends NPC
{

  public FatGuy(float x, float y, float speed, GameObjects[][] map, Player player, String text)
  {
    super(x, y, speed, map, player, text);
    setLook("guard", 50, 50);
  }
  @Override
  public void update(float tslf)
  {
    speedX=0;
    speedY=0;
    speedY=50;
    super.update(tslf);
  }
}
