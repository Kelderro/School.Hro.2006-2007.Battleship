package boats;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import battleship.boats.AircraftCarrier;

public class AircraftCarrierTest {
  private AircraftCarrier _aircraftCarrier;

  @BeforeEach
  void Construct() {
    _aircraftCarrier = new AircraftCarrier();
  }

  @Test
  void SetNullSquareReturnFalse() {
    // Arrange

    // Act
    boolean returnValue = _aircraftCarrier.addSquare(null);

    // Assert
    assertFalse(returnValue);
  }

}
