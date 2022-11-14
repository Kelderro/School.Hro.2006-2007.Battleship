package battleship;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioManager {
  public long PlaySplash() {
    return Play("splash.wav");
  }

  public long PlayHit() {
    return Play("explode.wav");
  }

  public long PlayStart() {
    return Play("horn.wav");
  }

  public long PlayBoatSunk() {
    return Play("databan.wav");
  }

  private long Play(String fileName) {
    if (!fileName.endsWith(".wav")) {
      System.out.println("The provided file name is not a wav file based on the extension");
      return 0;
    }

    File file = new File(fileName);

    if (!file.exists()) {
      System.out.println(
          String.format("Unable to find play audio file as file does not exist under path: '%s'.",
              file.getAbsolutePath()));
      return 0;
    }

    AudioInputStream ais;
    try {
      ais = AudioSystem.getAudioInputStream(new File(fileName));

      try {
        Clip clip = AudioSystem.getClip();
        try {
          clip.open(ais);
        } catch (LineUnavailableException e) {
          e.printStackTrace();
        }
        clip.start();
        return clip.getMicrosecondLength() / 1000;
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }
}
