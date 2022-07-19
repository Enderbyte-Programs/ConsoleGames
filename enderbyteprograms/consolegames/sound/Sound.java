package enderbyteprograms.consolegames.sound;

import enderbyteprograms.consolecolours;
import enderbyteprograms.enderlib;
import enderbyteprograms.consolegames.shared;

import java.io.BufferedInputStream;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.FileInputStream;

public class Sound {
    public String filename;
    public String url;
    public Thread plt;
    public Clip clip;
    public Sound(String u,String fname) {
        filename = enderlib.getcwd() + "/assets" + fname;
        url = u;
        shared.assetslist.add(this);
    }
    
    public synchronized void play() {
        plt = new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
              public void run() {
                try {
                  clip = AudioSystem.getClip();
                  AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(new File(filename))));
                  clip.open(inputStream);
                  clip.start();
                   
                } catch (Exception e) {
                  System.err.println(consolecolours.RED_BRIGHT + e.getMessage() + consolecolours.RESET);
                }
              }
            });
        plt.start();
    }
    public void stop() {
      clip.stop();
    }
}
