/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Christian
 */
public class ImageFactory {

  private static ImageFactory imageFactory;
  
  private HashMap<String,BufferedImage> looks=new HashMap<String, BufferedImage>();
  
  
  
  private ImageFactory(){
    try 
    {
      for (int i = 0; i < 1; i++) 
      {
          looks.put("Player"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/player/player/Player"+i+".png")));
      }
      for (int i = 0; i < 1; i++) {
          looks.put("BasicShot"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/player/player/basicSpritzer/BasicShot"+i+".png")));
      }
      for (int i = 0; i < 14; i++) {
          looks.put("FireShot"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/player/player/fireShot/Fire_Shot_0"+i+".png")));
      }
      for (int i = 0; i <= 10; i++) {
          looks.put("IceShot"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/player/player/iceShot/Ice_Shot_0"+i+".png")));
      }
      for (int i = 0; i < 1; i++) {
          looks.put("BasicShot"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/player/player/basicSpritzer/BasicShot"+i+".png")));
      }
      for (int i = 0; i < 1; i++) {
          looks.put("HundeGhoul"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeGhoul/hundeGhoul/HundeGhoul"+i+".png")));
      }
      loadMinions();
      loadPlayer();
      loadGameObjects();
      loadNPC();
      setBrightness("Gras",25,25);
    } catch (IOException ex) {
      System.out.println("funkt nicht");
    }
  }
  private void loadMinions() throws IOException
  {
    looks.put("BigMama", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeGhoul/hundeGhoul/BigMama.png")));
    looks.put("hund", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeGhoul/hund/DogGhoul.png")));
    for (int i = 1; i <= 2; i++) {
      looks.put("DogGhoul_hinten_0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeGhoul/hund/DogGhoul_hinten_0"+i+".png")));
      looks.put("DogGhoul_seite_0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeGhoul/hund/DogGhoul_seite_0"+i+".png")));
      looks.put("DogGhoul_seite2_0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeGhoul/hund/DogGhoul_seite2_0"+i+".png")));
      looks.put("DogGhoul_vorne_0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeGhoul/hund/DogGhoul_vorne_0"+i+".jpg")));
      
      looks.put("DogGhoul_hinten_0"+i+"_attacking", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeGhoul/hund/DogGhoul_hinten_0"+i+"_attacking.png")));
      looks.put("DogGhoul_seite_0"+i+"_attacking", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeGhoul/hund/DogGhoul_seite_0"+i+"_attacking.png")));
      looks.put("DogGhoul_seite2_0"+i+"_attacking", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeGhoul/hund/DogGhoul_seite2_0"+i+"_attacking.png")));
      looks.put("DogGhoul_vorne_0"+i+"_attacking", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeGhoul/hund/DogGhoul_vorne_0"+i+"_attacking.png")));
    }
    
    for (int i = 0; i < 1; i++)
    {
      looks.put("BigMamaHinten0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeGhoul/hundeGhoul/BigMamaHinten0"+i+".png")));
      looks.put("BigMamaSeiteLinks0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeGhoul/hundeGhoul/BigMamaSeiteLinks0"+i+".png")));
      looks.put("BigMamaSeiteRechts0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeGhoul/hundeGhoul/BigMamaSeiteRechts0"+i+".png"))); 
    }
    
    for (int i = 0; i < 2; i++)
    {
      looks.put("BigMamaVorne0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeGhoul/hundeGhoul/BigMamaVorne0"+i+".png")));
    }
    
    //for (int i = 0; i < 1; i++)
    {
      looks.put("Boss_Shot_Black_XL", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeBoss/Boss_Shot_Black_XL.png")));
    }
    
    //for (int i = 0; i < 1; i++)
    {
       looks.put("Boss_Shot_Blue_XL", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeBoss/Boss_Shot_Blue_XL.png")));
    }
      
    //for (int i = 0; i < 1; i++)
    {
       looks.put("Boss_Shot_Standard_XL", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeBoss/Boss_Shot_Standard_XL.png")));
    }
        
    //for (int i = 0; i < 1; i++)
    {
       looks.put("Boss_Shot_Violett_XL", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/minion/hundeBoss/Boss_Shot_Violett_XL.png")));
    }
  }
  private void loadNPC() throws IOException
  {
    looks.put("sign", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/npc/Sign.jpg")));
    looks.put("guard", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/npc/guard/guard.png")));
    looks.put("opi", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/npc/oldMan/opi.png")));
    for (int i = 0; i < 3; i++)
    {
      looks.put("Guard_hinten_0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/npc/guard/guard_hinten_0"+i+".png")));
      looks.put("Guard_seite_0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/npc/guard/guard_seite_0"+i+".png")));
      looks.put("Guard_seite_links_0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/npc/guard/guard_seite_links_0"+i+".png")));
      looks.put("Guard_vorne_0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/npc/guard/guard_vorne_0"+i+".png")));
      
      looks.put("OldMan_hinten_0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/npc/oldMan/opi_hinten_0"+i+".png")));
      looks.put("OldMan_seite_rechts_0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/npc/oldMan/opi_seite_0"+i+".png")));
      looks.put("OldMan_seite_links_0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/npc/oldMan/opi_seite2_0"+i+".png")));
      looks.put("OldMan_vorne_0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/npc/oldMan/opi_vorn_0"+i+".png")));
    }
  }
  private void loadPlayer() throws IOException
  {
    for (int i = 0; i < 3; i++) {
        looks.put("PlayerHinten"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/player/player/player/character_hinten_0"+i+".png")));
    }
    for (int i = 0; i < 3; i++) {
        looks.put("PlayerRechts"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/player/player/player/character_seite_rechts_0"+i+".png")));
    }
    for (int i = 0; i < 3; i++) {
        looks.put("PlayerLinks"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/player/player/player/character_seite_0"+i+".png")));
    }
    for (int i = 0; i < 3; i++) {
        looks.put("PlayerVorne"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/player/player/player/character_vorne_0"+i+".png")));
    }
    for (int i = 0; i < 6; i++) {
        looks.put("BasicShot_0"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/player/player/basicSpritzer/BasicShot_0"+i+".png")));
    }
    for (int i = 0; i < 1; i++) {
        looks.put("BasicShot", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/player/player/basicSpritzer/BasicShot"+i+".png")));
    }
    looks.put("FireMine", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/player/player/fireShot/Fire_Miene.png")));
  }
  private void loadGameObjects() throws IOException
  {
//    looks.put(null, null)
    looks.put("Carpet_Full", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/carpet/Carpet_Full.png")));
    setBrightness("Carpet_Full",75,75);
    looks.put("Door", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/haus/Door.png")));
    setBrightness("Door",50,50);
    for (int i = 0; i < 1; i++) {
        looks.put("Gras", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/gras/Gras"+i+".png")));
    }
    for (int i = 0; i < 1; i++) {
        looks.put("GrasSteppedOn"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/gras/GrasSteppedOn"+i+".png")));
        setBrightness("GrasSteppedOn"+i,25,25);
    }
    for (int i = 0; i < 1; i++) {
        looks.put("Weg"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/weg/Weg"+i+".png")));
        setBrightness("Weg"+i,25,25);
    }
    for (int i = 0; i < 1; i++) {
        looks.put("Wand"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/wand/Wand"+i+".png")));
        setBrightness("Wand"+i,25,25);
    }
    for (int i = 0; i < 1; i++)
    {
      looks.put("yellowFlower"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/flowers/yellowFlower/yellowFlower"+i+".jpg")));
      setBrightness("yellowFlower"+i,25,25);
    }
    for (int i = 0; i < 1; i++)
    {
      looks.put("blueFlower"+i, ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/flowers/blueFlower/blueFlower"+i+".jpg")));
      setBrightness("blueFlower"+i,25,25);
    }
    for (int i = 0; i < 1; i++)
    {
      looks.put("FenceSeite", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/fence/FenceSeite.jpg")));
      setBrightness("FenceSeite",25,25);
    }
    for (int i = 0; i < 1; i++)
    {
      looks.put("FenceVorneMid", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/fence/FenceVorneMid.jpg")));
      setBrightness("FenceVorneMid",25,25);
    }
    for (int i = 0; i < 1; i++)
    {
      looks.put("FenceVorneLinks", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/fence/FenceVorneLinks.jpg")));
      setBrightness("FenceVorneLinks",25,25);
    }
    for (int i = 0; i < 1; i++)
    {
      looks.put("FenceVorneRechts", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/fence/FenceVorneRechts.jpg")));
      setBrightness("FenceVorneRechts",25,25);
    }
    
    looks.put("FenceHintenMid", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/fence/FenceHintenMid.jpg")));
    setBrightness("FenceHintenMid",25,25);
    
    for (int i = 0; i < 1; i++)
    {
      looks.put("FenceHintenLinks", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/fence/FenceHintenLinks.jpg")));
      setBrightness("FenceHintenLinks",25,25);
    }
    for (int i = 0; i < 1; i++)
    {
      looks.put("FenceHintenRechts", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/fence/FenceHintenRechts.jpg")));
      setBrightness("FenceHintenRechts",25,25);
    }
    
    looks.put("Ground_Wood_Planks", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/haus/boden/Ground_Wood_Planks.png")));
    setBrightness("Ground_Wood_Planks",25,25);
    looks.put("Haus", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/haus/Haus.jpg")));
    setBrightness("Haus",275,275);
    looks.put("Haus2", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/haus/Haus2.png")));
    setBrightness("Haus2",275,275);
    looks.put("Haus3", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/haus/Haus3.png")));
    setBrightness("Haus3",275,275);
    looks.put("Haus4", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/haus/Haus4.png")));
    setBrightness("Haus4",275,275);
    looks.put("Tree", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/tree/Tree.jpg")));
    setBrightness("Tree",50,75);
    looks.put("FootCarpet", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/carpet/Foot_Carpet.png")));
    setBrightness("FootCarpet",50,25);
    looks.put("Stone_Floor_Full", ImageIO.read(getClass().getClassLoader().getResourceAsStream("gfx/gameObjects/carpet/Stone_Floor_Full.png")));
    setBrightness("Stone_Floor_Full",75,75);
  }
  public void setBrightness(String str,int width,int height)
  {
    BufferedImage look=looks.get(str);
    BufferedImage help[] = new BufferedImage[5];
    int startBrightness=-100;
    for (int i = 0; i < help.length; i++)
    {
      help[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      help[i].createGraphics().drawImage(look, 0, 0, null);
      RescaleOp rescaleOp = new RescaleOp(1f, 10*i+startBrightness, null);
      rescaleOp.filter(help[i], help[i]);
//      looks.put(str+"Brightness"+i, help[i]);
    }
    BufferedImage cool=new BufferedImage(help.length*width,height,BufferedImage.TYPE_INT_ARGB);
    Graphics g=cool.getGraphics();
    for (int i = 0; i < help.length; i++)
    {
      g.drawImage(help[i],width*i, 0,null);
    }
    looks.put(str+"Brightness", cool);
  }
  
  public static ImageFactory getIF()
  {
      if(imageFactory==null)imageFactory=new ImageFactory();
      return imageFactory;
  }
  public BufferedImage getLook(String str)
  {
      return looks.get(str);
  }
}
