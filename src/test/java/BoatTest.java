import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import battleship.Boat;
import battleship.Square;

class BoatTest {
  @Test
  void Constructor_MaxSquareCountZero_ThrowsException() {
    // Arrange
    assertThrows(IllegalArgumentException.class, () -> {
      new UnitTestBoat(0, "UnitTest");
    });
  }

  @Test
  void Constructor_MaxSquareCountNegative_ThrowsException() {
    // Arrange
    assertThrows(IllegalArgumentException.class, () -> {
      new UnitTestBoat(-1, "UnitTest");
    });
  }

  @Test
  void Constructor_BoatNameEmpty_ThrowsException() {
    // Arrange
    assertThrows(IllegalArgumentException.class, () -> {
      new UnitTestBoat(1, "");
    });
  }

  @Test
  void Constructor_BoatNameNull_ThrowsException() {
    // Arrange
    assertThrows(IllegalArgumentException.class, () -> {
      new UnitTestBoat(1, null);
    });
  }

  @Test
  void Constructor_BoatNameWhiteSpacesOnly_ThrowsException() {
    // Arrange
    assertThrows(IllegalArgumentException.class, () -> {
      new UnitTestBoat(1, "  ");
    });
  }

  @Test
  void PlaceBoatOnUnclaimedSquare_ReturnTrue() {
    // Arrange
    UnitTestBoat unitTestBoat = new UnitTestBoat(5, "UnitTest");
    Square square = new Square(1, 0);

    // Act
    Boolean claim = unitTestBoat.claimSquare(square);

    // Assert
    assertTrue(claim);
  }

  @Test
  void PlaceBoatOnClaimedSquare_ReturnFalse() {
    // Arrange
    UnitTestBoat unitTestBoat = new UnitTestBoat(5, "UnitTest");
    Square square = new Square(1, 0);
    square.setBoat(unitTestBoat);

    // Act
    Boolean claim = unitTestBoat.claimSquare(square);

    // Assert
    assertFalse(claim);
  }

  @Test
  void PlaceBoat_Diagonal_Failure() {
    UnitTestBoat unitTestBoat = new UnitTestBoat(5, "UnitTest");
    Square square = new Square(0, 0);
    Boolean claim = unitTestBoat.claimSquare(square);

    assertTrue(claim);

    square = new Square(1, 1);
    claim = unitTestBoat.claimSquare(square);

    assertFalse(claim);
  }

  @Test
  void HorizontalBoat_SkipBlock_ReturnFalse() {
    UnitTestBoat unitTestBoat = new UnitTestBoat(5, "UnitTest");
    Square square = new Square(1, 0);

    Boolean claim = unitTestBoat.claimSquare(square);

    assertTrue(claim);

    square = new Square(2, 0);
    claim = unitTestBoat.claimSquare(square);

    assertTrue(claim);

    square = new Square(3, 0);
    claim = unitTestBoat.claimSquare(square);

    assertTrue(claim);

    square = new Square(0, 0);
    claim = unitTestBoat.claimSquare(square);

    assertTrue(claim);

    square = new Square(5, 0);
    claim = unitTestBoat.claimSquare(square);

    assertFalse(claim);
  }

  @Test
  void VerticalBoat_SkipBlock_ReturnFalse() {
    UnitTestBoat unitTestBoat = new UnitTestBoat(5, "UnitTest");
    Square square = new Square(0, 1);

    Boolean claim = unitTestBoat.claimSquare(square);

    assertTrue(claim);

    square = new Square(0, 2);
    claim = unitTestBoat.claimSquare(square);

    assertTrue(claim);

    square = new Square(0, 3);
    claim = unitTestBoat.claimSquare(square);

    assertTrue(claim);

    square = new Square(0, 0);
    claim = unitTestBoat.claimSquare(square);

    assertTrue(claim);

    square = new Square(0, 5);
    claim = unitTestBoat.claimSquare(square);

    assertFalse(claim);
  }

  private class UnitTestBoat extends Boat {
    public UnitTestBoat(int maxSquareCount, String boatName) {
      super(maxSquareCount, boatName);
    }
  }
}
