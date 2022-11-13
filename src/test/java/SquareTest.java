import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import battleship.Square;
import battleship.boats.Minesweeper;

class SquareTest {
  @Test
  void SetColumnViaConstructorReturnSameValueViaGet() {
    // Arrange
    int column = (int) (Math.random() * (1000 - 0 + 1) + 0);

    // Act
    Square square = new Square(0, column);

    // Assert
    assertEquals(column, square.column);
  }

  @Test
  void SetRowViaConstructorReturnSameValueViaGet() {
    // Arrange
    int row = (int) (Math.random() * (1000 - 0 + 1) + 0);

    // Act
    Square square = new Square(row, 0);

    // Assert
    assertEquals(row, square.row);
  }

  @Test
  void DefaultBoatIsSetToNull() {
    // Arrange
    Square square = new Square(0, 0);

    // Act

    // Assert
    assertNull(square.getBoat());
  }

  @Test
  void SetBoatSameValueAsGetBoat() {
    // Arrange
    Minesweeper boat = new Minesweeper();
    Square square = new Square(0, 0);

    // Act
    square.setBoat(boat);

    // Assert
    assertEquals(boat, square.getBoat());
  }

  @Test
  void DefaultHitIsSetToFalse() {
    // Arrange
    Square square = new Square(0, 0);

    // Act

    // Assert
    assertEquals(false, square.getHit());
  }

  @Test
  void SetHitReturnHitValue() {
    // Arrange
    Square square = new Square(0, 0);

    // Act
    square.setHit();

    // Assert
    assertEquals(true, square.getHit());
  }
}
