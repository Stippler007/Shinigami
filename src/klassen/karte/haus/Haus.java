/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen.karte.haus;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import klassen.ImageFactory;
import klassen.LevelDesign;
import klassen.karte.GameObjects;

/**
 *
 * @author Stippler
 */
public class Haus extends GameObjects
{

  private boolean door=false;
  
  public Haus(int brightness,int x,int y)
  {
    super(brightness, x, y);
    setImage("HausBrightness",275,5);
    solid=true;
  }
}
