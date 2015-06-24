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
 * @author Stippler
 */
public class Sign extends NPC
{
  
  public Sign(float x, float y, GameObjects[][] map, Player player,String str) 
  {
    super(x, y, 0, map, player,str);
//    look=new BufferedImage[1][1];
//    look[0][0]=ImageFactory.getIF().getLook("sign");
      setLook("sign", 25, 25);
    bounding=new Rectangle((int)x,(int)y,25,25);
  }
  
  @Override
  public void update(float tslf) 
  {
    super.update(tslf);
  }

  @Override
  public void draw(Graphics2D g)
  {
    g.drawImage(getLook(),null,(int)x,(int)y);
  }
  
  @Override
  public BufferedImage getLook()
  {
    return look[0][0];
  }
}
