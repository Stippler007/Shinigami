
package klassen;

import klassen.karte.flowers.YellowFlower;
import klassen.karte.flowers.BlueFlower;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import klassen.karte.*;
import klassen.karte.fence.*;
import klassen.player.Player;

/**
 *
 * @author Christian
 */
public class Background 
{
    
  private GameObjects[][] map=new GameObjects[100][100];
  
  public static float x;
  public static float y;
  
  public Background(float x,float y)
  {
    this.x=x;
    this.y=y;
  }
  
  public void setMap(GameObjects[][] map) 
  {
    this.map = map;
  }
  
  public void update(float tslf)
  {
//    System.out.format("X Koordinate: %f    Y Koordinate %f Block-X: %f     Block-Y: %f\n",
//            Background.x-400,Background.y-300,Background.x/25-(800/2/25),Background.y/25-(600/2/25));
    
    x+=Player.speedX;
    y+=Player.speedY;
    for (int i =(int)(x/25*-1); i < (int)(x/25*-1)+34; i++) 
    {
      for (int j = (int)(y/25*-1); j < (int)(y/25*-1)+26; j++) 
      {
        if(!(i<0||j<0)&&!(i>map.length-1||j>map[0].length-1))
        {
          map[i][j].update(tslf,(int)(x+i*25),(int)(y+j*25));
        }
        else
        {
          System.out.println("Out of map");
        }
      }
    }
  }

  public void drawBG(Graphics2D g)
  {
    for (int i =(int)(x/25*-1); i < (int)(x/25*-1)+34; i++) {
      for (int j = (int)(y/25*-1); j < (int)(y/25*-1)+26; j++) 
      {
        if(!(i<0||j<0)&&!(i>map.length-1||j>map[0].length-1))
        {
          g.drawImage(map[i][j].getLook(), null, (int)(x+i*25),(int)(y+j*25));
        }
        else
        {
          System.out.println("Out of map");
        }
        
      }
    }
  }

  public GameObjects[][] getMap()
  {
    return map;
  }
}