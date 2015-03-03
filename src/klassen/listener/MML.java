/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import klassen.GUI;
import static klassen.listener.ML.x;
import static klassen.listener.ML.y;

/**
 *
 * @author Christian
 */
public class MML implements MouseMotionListener{
    
  public static int x;
  public static int y;

  private GUI f;

  public MML(GUI f) {
    this.f = f;
  }
  @Override
  public void mouseDragged(MouseEvent e) 
  {
    x=(int)(e.getX()/f.getxScaling());
    y=(int)(e.getY()/f.getyScaling());
  }
  @Override
  public void mouseMoved(MouseEvent e) 
  {
    x=(int)(e.getX()/f.getxScaling());
    y=(int)(e.getY()/f.getyScaling());
  }
}
