package boats;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import battleship.boats.Minesweeper;
import org.junit.jupiter.api.Test;

class MinesweeperTest {
  @Test
  void Construct_MineSweeper() {
    // Act
    Minesweeper boat = new Minesweeper();

    // Assert
    assertNotNull(boat);
  }
}
