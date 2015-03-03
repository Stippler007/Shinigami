/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package klassen.player;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
*
* @author Christian
*/
public abstract class PlayerSpritzer {
  protected float x;
  protected float y;

  protected float damage;

  protected float animationTime=0;
  protected float maxAnimationTime=0.3f;
  
  protected float speed;

  protected float speedX;
  protected float speedY;
  
  protected Rectangle bounding;

  public PlayerSpritzer(float x, float y,Rectangle bounding,float damage) 
  {
      this.x = x;
      this.y = y;
      this.damage=damage;
      this.bounding=bounding;
  }

  public void update(float tslf)
  {
    x+=Player.speedX;
    y+=Player.speedY;
    
    bounding.x=(int)x;
    bounding.y=(int)y;
  }
  public abstract BufferedImage getLook();
  public Rectangle getBounding()
  {
    return bounding;
  }
  public float getDamage()
  {
    return damage;
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }
  
}
