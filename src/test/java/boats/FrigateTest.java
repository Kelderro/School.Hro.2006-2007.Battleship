package boats;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import battleship.boats.Frigate;
import org.junit.jupiter.api.Test;

class FrigateTest {
  @Test
  void Construct_Frigate() {
    Frigate boat = new Frigate();

    // Assert
    assertNotNull(boat);
  }
}
