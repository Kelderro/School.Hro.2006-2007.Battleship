package boats;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import battleship.boats.AircraftCarrier;
import org.junit.jupiter.api.Test;

class AircraftCarrierTest {
  @Test
  void Construct_AircraftCarrier() {
    AircraftCarrier boat = new AircraftCarrier();

    // Assert
    assertNotNull(boat);
  }
}
