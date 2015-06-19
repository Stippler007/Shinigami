
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice.*;
import javazoom.jl.player.Player;

/**
 *
 * @author Christian
 */
public class Music {

    private static Music music;
    private static Random r = new Random();
    private Thread randomGrasStep;

    private Music() {
    }

    public static Music play() {
        if (music == null) {
            music = new Music();
        }
        return music;
    }

//    public void canonInD() {
//        //String path="C:\\Users\\Christian\\Desktop\\TheGame\\src\\sounds\\blabla\\CanonInD.mp3";
////    String path=System.getProperty("user.dir")+File.separator+"sounds"+File.separator+"blabla"+File.separator+"CanonInD.mp3";
//        URL path = getClass().getResource("../sounds/PlayerSounds/Fireshot.mp3");
//        System.out.println(path.toString());
//        MusicPlayer mp = new MusicPlayer(path.getFile());
//        Thread t = new Thread(mp);
//        t.start();
//    }
//
//    public void randomGrasStep() throws Exception
//    {
////    String path=System.getProperty("user.dir")+File.separator+"sounds"+File.separator+"blabla"+File.separator+"Grassstep_"+(r.nextInt(5)+1)+".mp3";
//        if (randomGrasStep == null || !randomGrasStep.isAlive()) {
//            URL path = getClass().getResource("../sounds/PlayerSounds/Grassstep_" + (r.nextInt(5) + 1) + ".mp3");
//            MusicPlayer mp = new MusicPlayer(path.getFile());
//            Thread t = new Thread(mp);
//            randomGrasStep = t;
//            t.start();
//        }
//    }
}