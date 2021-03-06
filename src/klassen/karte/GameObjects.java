/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.karte;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import klassen.ImageFactory;
import klassen.Music;
import klassen.player.Player;

/**
 *
 * @author Christian
 */
public abstract class GameObjects implements Serializable {

    protected int brightness;
    protected boolean brightnessChanged;

    protected int currentBrightness;
    protected boolean currentBrightnessChanged;
    
    /*
    brightness geht von -100 bis 100 insgesamt 21 bilder
    */
    
    protected boolean stepped = false;
    protected boolean solid = false;
    protected boolean heated = false;
    protected boolean frozen = false;
    protected boolean thorny = false;
    protected Rectangle bounding;

    protected transient BufferedImage look[];
    protected String imageTag = "Wand";
    protected int subX = 0;
    protected int subY = 0;
    protected int width = 0;
    
    public GameObjects(int brightness) {
        this(brightness, 0, 0);
    }

    public GameObjects(int brightness, int subX, int subY) {
        bounding = new Rectangle(0, 0, 25, 25);
        this.brightness = brightness;
        this.subX = subX;
        this.subY = subY;
    }

    public void steppedOn(boolean stepped) 
    {
      this.stepped = stepped;
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        System.out.println(imageTag);
        setImage(imageTag,width,5);
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public void setThorny(boolean thorny) {
        this.thorny = thorny;
    }

    public void setSubX(int subX) {
        this.subX = subX;
    }

    public void setSubY(int subY) {
        this.subY = subY;
    }
    
    public void setImage(String tag, int width,int amount)
    {
      imageTag = tag;
      look=new BufferedImage[amount];
      for (int i = 0; i < look.length; i++)
      {
        System.out.println(""+width);
        look[i]=ImageFactory.getIF().getLook(tag).getSubimage(subX*25+i*width, subY*25, 25, 25);
      }
    }

    public String getImageTag() {
        return imageTag;
    }
    
    public void update(float tslf, float x, float y)
    {
      bounding.x = (int) x;
      bounding.y = (int) y;
    }

    public void playerSteppedOn(Player player)
    {
        System.out.println("step");
    }

    public void setCurrentBrightness(int currentBrightness) 
    {
        this.currentBrightness = currentBrightness;
        currentBrightnessChanged = true;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
        this.currentBrightness=brightness;
        this.brightnessChanged = true;
        System.out.println(currentBrightness);
    }

    public BufferedImage getLook()
    {
      int i=(int)(((float)currentBrightness)/25);
      return look[i];
    }

    public Rectangle getBounding() {
        return bounding;
    }

    public boolean isSolid()
    {
        return solid;
    }

    public boolean isStepped() {
        return stepped;
    }

    public boolean isHeated() {
        return heated;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public boolean isThorny() {
        return thorny;
    }
}
