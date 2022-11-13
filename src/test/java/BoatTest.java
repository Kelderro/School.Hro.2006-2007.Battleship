import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import battleship.Boat;

public class BoatTest {
  @Test
  public void Constructor_MaxSquareCountZero_ThrowsException() {
    // Arrange
    assertThrows(IllegalArgumentException.class, () -> {
      new InvalidBoat(0, "UnitTest");
    });
  }

  @Test
  public void Constructor_MaxSquareCountNegative_ThrowsException() {
    // Arrange
    assertThrows(IllegalArgumentException.class, () -> {
      new InvalidBoat(-1, "UnitTest");
    });
  }

  @Test
  public void Constructor_BoatNameEmpty_ThrowsException() {
    // Arrange
    assertThrows(IllegalArgumentException.class, () -> {
      new InvalidBoat(1, "");
    });
  }

  @Test
  public void Constructor_BoatNameNull_ThrowsException() {
    // Arrange
    assertThrows(IllegalArgumentException.class, () -> {
      new InvalidBoat(1, null);
    });
  }

  @Test
  public void Constructor_BoatNameWhiteSpacesOnly_ThrowsException() {
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
