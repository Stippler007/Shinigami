/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen.minion.evilguard;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import klassen.ImageFactory;
import klassen.karte.GameObjects;
import klassen.minion.Minion;
import klassen.player.Player;
import klassen.player.PlayerSpritzer;

/**
 *
 * @author Julian
 */
public class EvilGuard extends Minion {

    private BufferedImage look[]=new BufferedImage[2];
    private final float maxAnimationTime=0.3f;
    private float animationTime=0f;
    private boolean attacking = false;
    
    public EvilGuard(float x, float y, float speed, float maxLive, Rectangle bounding, GameObjects[][] map, Player player, List<PlayerSpritzer> playerSpritzers) {
        super(x, y, speed, maxLive, map, player, playerSpritzers);
    }
    
    @Override
    public BufferedImage getLook() {
        if(!attacking)
        {
          double turn=getTurn();
          if(turn>=-Math.PI*0.25&&turn<=Math.PI*0.25)
          {
            for (int i = 1; i < look.length+1; i++)
            {
              look[i-1]=ImageFactory.getIF().getLook("Guard_seite_0"+i);
            }
          }
          else if(turn>=Math.PI*0.25&&turn<=Math.PI*0.5){
            for (int i = 1; i < look.length+1; i++) {
              look[i-1]=ImageFactory.getIF().getLook("Guard_vorne_0"+i);
            }
          }
          else if(turn>=Math.PI*0.50&&turn<=Math.PI*1){
            for (int i = 1; i < look.length+1; i++) {
              look[i-1]=ImageFactory.getIF().getLook("Guard_seite2_0"+i);
            }
          }
          else{
            for (int i = 1; i < look.length+1; i++) 
            {
              look[i-1]=ImageFactory.getIF().getLook("Guard_hinten_0"+i);
            }
          }
        }
        for (int i = 0; i < look.length; i++)
          {
            if(animationTime<maxAnimationTime/look.length*i)
            {
              return look[i];
            }
        }
        return look[0];
    }

    @Override
    public void draw(Graphics2D g) {
        drawHealthBar(g);
        g.rotate( getTurn(),  bounding.x+bounding.width/2,  bounding.y+bounding.height/2);
        g.drawImage(getLook(), null, getBounding().x, getBounding().y);
        g.rotate(-getTurn(),  bounding.x+bounding.width/2,  bounding.y+bounding.height/2);
    }
    
    @Override
    public void update(float tslf) {
        x += Player.speedX;
        y += Player.speedY;
        
        
        
        super.update(tslf);
    }
}
