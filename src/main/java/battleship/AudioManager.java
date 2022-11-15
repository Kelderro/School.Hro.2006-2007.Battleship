package battleship;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioManager {

  private String soundPath = "./battleship/resources/sounds/";
  private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  public long playSplash() {
    return play("splash.wav");
  }

  public long playHit() {
    return play("explode.wav");
  }

  public long playStart() {
    return play("horn.wav");
  }

  public long playBoatSunk() {
    return play("databan.wav");
  }

  private long play(String fileName) {
    if (!canPlayAudio(fileName)) {
      return 0;
    }

    AudioInputStream ais;
    File file = new File(soundPath + fileName);

    try {
      ais = AudioSystem.getAudioInputStream(file);

      Clip clip = AudioSystem.getClip();
      clip.open(ais);
      clip.start();
      return clip.getMicrosecondLength() / 1000;

    } catch (LineUnavailableException e) {
      this.logger.error("Unable to play audio file '{}' as the line is not available", fileName, e);
    } catch (UnsupportedAudioFileException e) {
      this.logger.error("The provided audio file '{}' is not supported", fileName, e);
    } catch (IOException e) {
      this.logger.error("An IO exception occured when trying to play audio file '{}'", fileName, e);
    } catch (Exception e) {
      this.logger.error("An exception occured when trying to play audio file '{}'", fileName, e);
    }
    return 0;
  }

  private boolean canPlayAudio(String fileName) {
    if (AudioSystem.getMixerInfo().length == 0) {
      this.logger.info("No audio system found. Unable to play any sound on this system");
      return false;
    }

    if (!fileName.endsWith(".wav")) {
      this.logger.info("The provided file name is not a wav file based on the extension");
      return false;
    }

    if (!fileName.matches("^([A-Za-z])+\\.wav$")) {
      this.logger.info("The provided filename does not meet the expected regular expression validation.");
      return false;
    }

    File file = new File(soundPath + fileName);

    if (!file.exists()) {
      this.logger.info("Unable to find play audio file as file does not exist under path: '{}'.",
          file.getAbsolutePath());
      return false;
    }

    return true;
  }
}
