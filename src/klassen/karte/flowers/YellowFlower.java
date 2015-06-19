/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.karte.flowers;

import java.awt.image.BufferedImage;
import klassen.karte.GameObjects;

/**
 *
 * @author Christian
 */
public class YellowFlower extends GameObjects {

    /*
     df = disk free, welche partitions gemountet sind
     umount um das wieder loszuwerden
     ls -l = alle verzeichnisse anzeigen
     fdisk -l umbuntu-14.10-beta2-desktop-amd64.iso
     */
    private float animationTime = 0f;
    private final float maxAnimationTime = 1f;

    public YellowFlower(int brightness) {
        super(brightness);
        setImage("yellowFlower0Brightness",25,5);
    }

    @Override
    public void update(float tslf, float x, float y) {
        if (animationTime < maxAnimationTime) {
            animationTime += tslf;
        } else {
            animationTime -= maxAnimationTime;
        }
        super.update(tslf, x, y);
    }

    

    @Override
    public void setBrightness(int brighness) {

    }
}
