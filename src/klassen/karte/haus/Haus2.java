/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen.karte.haus;

import klassen.karte.GameObjects;

/**
 *
 * @author ffrie_000
 */
public class Haus2 extends GameObjects
{
    private boolean door=false;
  
  public Haus2(int brightness,int x,int y)
  {
    super(brightness, x, y);
    setImage("Haus2");
    solid=true;
  }
}
