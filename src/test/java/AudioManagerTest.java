import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.*;

import battleship.AudioManager;
import javax.sound.sampled.AudioSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AudioManagerTest {
  AudioManager audioManager;

  @BeforeEach
  void CreateAudioManager() {
    // Only run audio manager tests if there are audio mixers
    // for example on the build server these tests will fail
    assumeFalse(AudioSystem.getMixerInfo().length == 0);
    this.audioManager = new AudioManager();
  }

  @Test
  void PlaySplashAudio_ReturnAudioLength() {
    Long length = this.audioManager.playSplash();

    assertTrue(length > 0);
  }

  @Test
  void PlayHitAudio_ReturnAudioLength() {
    Long length = this.audioManager.playHit();

    assertTrue(length > 0);
  }

  @Test
  void PlayStartAudio_ReturnAudioLength() {
    Long length = this.audioManager.playStart();

    assertTrue(length > 0);
  }

  @Test
  void PlayBoatSunkAudio_ReturnAudioLength() {
    Long length = this.audioManager.playBoatSunk();

    assertTrue(length > 0);
  }
}
