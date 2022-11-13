import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import battleship.Boat;

class BoatTest {
  @Test
  void Constructor_MaxSquareCountZero_ThrowsException() {
    // Arrange
    assertThrows(IllegalArgumentException.class, () -> {
      new InvalidBoat(0, "UnitTest");
    });
  }

  @Test
  void Constructor_MaxSquareCountNegative_ThrowsException() {
    // Arrange
    assertThrows(IllegalArgumentException.class, () -> {
      new InvalidBoat(-1, "UnitTest");
    });
  }

  @Test
  void Constructor_BoatNameEmpty_ThrowsException() {
    // Arrange
    assertThrows(IllegalArgumentException.class, () -> {
      new InvalidBoat(1, "");
    });
  }

  @Test
  void Constructor_BoatNameNull_ThrowsException() {
    // Arrange
    assertThrows(IllegalArgumentException.class, () -> {
      new InvalidBoat(1, null);
    });
  }

  @Test
  void Constructor_BoatNameWhiteSpacesOnly_ThrowsException() {
    // Arrange
    assertThrows(IllegalArgumentException.class, () -> {
      new InvalidBoat(1, "  ");
    });
  }

  private class InvalidBoat extends Boat {
    public InvalidBoat(int maxSquareCount, String boatName) {
      super(maxSquareCount, boatName);
    }
  }
}
