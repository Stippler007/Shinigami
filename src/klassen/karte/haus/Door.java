/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen.karte.haus;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import klassen.ImageFactory;
import klassen.LevelDesign;
import klassen.karte.GameObjects;
import klassen.player.Player;

/**
 *
 * @author Stippler
 */
public class Door extends GameObjects
{
  private LevelDesign ld;
  private int levelLoad;
  private int startX;
  private int startY;
  
  public Door(int brightness,int x,int y,LevelDesign ld,int startX,int startY,int id)
  {
    super(brightness);
    this.ld=ld;
    this.levelLoad=id;
    this.startX=startX;
    this.startY=startY;
    setImage("Door",50,5);
  }

  @Override
  public void playerSteppedOn(Player player) 
  {
    ld.loadLevel(levelLoad, startX, startY);
  }
  

  @Override
  public void setBrightness(int brighness) {
    
  }
  
}
