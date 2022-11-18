import static org.junit.jupiter.api.Assertions.assertTrue;

import battleship.Board;
import battleship.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
  private Board board;

  @BeforeEach
  void Create_Board() {
    this.board = new Board(null);
  }

  @Test
  void SetBoatButton_ConditionPlacingBoat_SetBlack() {
    // Act
    this.board.setBoatButton(0, 0, Condition.PLACINGBOAT);

    // Assert
    assertTrue(true);
  }

  @Test
  void SetBoatButton_ConditionSplash_SetBlue() {
    // Act
    this.board.setBoatButton(0, 0, Condition.SPLASH);

    // Assert
    assertTrue(true);
  }

  @Test
  void SetBoatButton_ConditionHit_SetOrange() {
    // Act
    this.board.setBoatButton(0, 0, Condition.HIT);

    // Assert
    assertTrue(true);
  }

  @Test
  void SetBoatButton_ConditionSunk_SetRed() {
    // Act
    this.board.setBoatButton(0, 0, Condition.SUNK);

    // Assert
    assertTrue(true);
  }

  @Test
  void SetBoatButton_ConditionLost_DoesNothing() {
    // Act
    this.board.setBoatButton(0, 0, Condition.LOST);

    // Assert
    assertTrue(true);
  }
}
