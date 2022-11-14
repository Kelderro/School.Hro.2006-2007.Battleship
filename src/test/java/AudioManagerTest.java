import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import battleship.AudioManager;

class AudioManagerTest {
  AudioManager audioManager;

  @BeforeEach
  void CreateAudioManager() {
    this.audioManager = new AudioManager();
  }

  @Test
  void PlaySplashAudio_ReturnAudioLength() {
    Long length = this.audioManager.PlaySplash();

    assertTrue(length > 0);
  }

  @Test
  void PlayHitAudio_ReturnAudioLength() {
    Long length = this.audioManager.PlayHit();

    assertTrue(length > 0);
  }

  @Test
  void PlayStartAudio_ReturnAudioLength() {
    Long length = this.audioManager.PlayStart();

    assertTrue(length > 0);
  }

  @Test
  void PlayBoatSunkAudio_ReturnAudioLength() {
    Long length = this.audioManager.PlayBoatSunk();

    assertTrue(length > 0);
  }
}
