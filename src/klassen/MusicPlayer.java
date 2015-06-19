
package klassen;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Christian
 */
public class MusicPlayer implements Runnable {

    Player player;

    public MusicPlayer(String path) {
        //"C:\\Users\\Christian\\Desktop\\TheGame\\src\\sounds\\CanonInD.mp3"
        try {
//            System.out.println("Enter");
            File file = new File(path);
//            FileInputStream fis = new FileInputStream(file);
//            BufferedInputStream bis = new BufferedInputStream(fis);
//            System.out.println("Leave");
            Player player = new Player(new FileInputStream(file));
            this.player = player;
        } catch (IOException ex) {
            System.out.println("Error: IO "+ex.getMessage());
        } catch (JavaLayerException ex) {
            System.out.println("Error: Lib");
        }
    }

    @Override
    public void run() {
//        try {
//            player.play();
//        } catch (JavaLayerException ex) {
//            System.out.println("False path");
//        }
    }

}