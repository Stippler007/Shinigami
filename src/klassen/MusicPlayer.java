/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Christian
 */
public class MusicPlayer implements Runnable
{
  Player player;
  
  public MusicPlayer(String path)
  {
    //"C:\\Users\\Christian\\Desktop\\TheGame\\src\\sounds\\CanonInD.mp3"
    try
    {
      File file =new File(path);
      FileInputStream fis = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(fis);
      
      try{
        Player player = new Player(bis);
        this.player=player;
      }
      catch(JavaLayerException ex){
      }
    }
    catch(IOException ex){
    }
  }
  
  
  @Override
  public void run()
  {
    try
    {
      player.play();
    } catch (JavaLayerException ex)
    { 
      System.out.println("False path");
    }
  }
  
}
