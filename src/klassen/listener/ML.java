/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import klassen.GUI;

/**
 *
 * @author Christian
 */
public class ML implements MouseListener{
    
  public static boolean leftMousePressed=false;
  public static boolean leftMouseRealeased=false;
  public static boolean leftMouseDragged=false;
  
  public static boolean rightMousePressed=false;
  public static boolean rightMouseRealeased=false;
  public static boolean rightMouseDragged=false;
  
  public static int x;
  public static int y;
  
  private GUI f;
  
  public ML(GUI f) {
    this.f=f;
  }
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x=(int)(e.getX()/f.getxScaling());
        y=(int)(e.getY()/f.getyScaling());
//        switch(e.getButton()){
//            case 0:System.out.println("noButton");
//                break;
//            case 1:System.out.println("Linksklick");
//                break;
//            case 2:System.out.println("Mittelklick");
//                break;
//            case 3:System.out.println("Recthsklick");
//                break;
//        }
        if(e.getButton()==MouseEvent.BUTTON1)
        {
          leftMouseRealeased=false;
          leftMousePressed=true;
          leftMouseDragged=true;
        }
        if(e.getButton()==MouseEvent.BUTTON3)
        {
          rightMouseRealeased=false;
          rightMousePressed=true;
          rightMouseDragged=true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        x=(int)(e.getX()/f.getxScaling());
        y=(int)(e.getY()/f.getyScaling());
//        switch(e.getButton()){
//            case 0:System.out.println("noButton");
//                break;
//            case 1:System.out.println("Linksklick");
//                break;
//            case 2:System.out.println("Mittelklick");
//                break;
//            case 3:System.out.println("Recthsklick");
//                break;
//        }
        if(e.getButton()==MouseEvent.BUTTON1)
        {
          leftMouseRealeased=true;
          leftMousePressed=false;
          leftMouseDragged=false;
        }
        if(e.getButton()==MouseEvent.BUTTON3)
        {
          rightMouseRealeased=true;
          rightMousePressed=false;
          rightMouseDragged=false;
        }
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
