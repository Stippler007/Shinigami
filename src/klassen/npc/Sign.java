/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen.npc;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import klassen.ImageFactory;
import klassen.karte.GameObjects;
import klassen.player.Player;
import klassen.player.PlayerSpritzer;

/**
 *
 * @author Stippler
 */
public class Sign extends NPC
{
  private BufferedImage look=ImageFactory.getIF().getLook("Sign");
  
  public Sign(float x, float y, GameObjects[][] map, Player player,String str) 
  {
    super(x, y, 0, map, player,str);
  }

  @Override
  public void update(float tslf) 
  {
    super.update(tslf);
  }

  @Override
  public void draw(Graphics2D g) 
  {
    g.drawImage(look, null,(int)x,(int)y);
  }
  
  @Override
  public BufferedImage getLook() 
  {
    return look;
  }
}
