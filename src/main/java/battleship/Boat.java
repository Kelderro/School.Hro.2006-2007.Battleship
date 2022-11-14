/*
 *
 * Created on 13 september 2006, 8:48
 *
 */

package battleship;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 0777974
 */
public abstract class Boat {

  private int maxSquareCount;
  protected int count = 0;
  protected boolean sunk = false;
  private String boatName;

  private Square[] squares;

  private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  protected Boat(int maxSquareCount, String boatName) {
    if (maxSquareCount < 1) {
      throw new IllegalArgumentException("maxSquareCount cannot zero or lower");
    }

    if (boatName == null || boatName.trim().isEmpty()) {
      throw new IllegalArgumentException("boatName is null, empty or only contains whitespaces");
    }

    this.maxSquareCount = maxSquareCount;
    this.squares = new Square[maxSquareCount];
    this.boatName = boatName;
  }

  public boolean complete() {
    return (count == maxSquareCount);
  }

  public boolean checkSunk() {
    Square square;
    for (int i = 0; i < count; i++) {
      square = squares[i];
      if (!square.getHit()) {
        sunk = false;
        return false;
      }
    }
    sunk = true;
    return true;
  }

  public boolean isSunk() {
    return sunk;
  }

  protected boolean addSquare(Square square) {
    this.logger.info("{}:\n\tNumber of squares placed: {}\n\tTotal amount of squares:\t{}", boatName, count,
        squares.length);
    if (square.getBoat() == null) {
      if (count == 0) {
        squares[count++] = square;
        return true;
      } else {
        if (isPart(square)) {
          squares[count++] = square;
          return true;
        }
      }
    }
    return false;
  }

  protected boolean isPart(Square square) {
    /**
     * This magnificent function has been coded by:
     * (0777974) - Rob op den Kelder
     * (0777556) - Stephan Klop
     * Westland corp.
     */
    Square firstSquare = squares[0];
    Square lastSquare = squares[count - 1];

    boolean sameColumn = false;
    boolean sameRow = false;
    for (int i = 1; i < count; i++) {
      if (squares[i - 1].column == squares[i].column) {
        sameColumn = true;
      } else {
        sameRow = true;
      }
    }

    String nextAction = "need to stay in the same row";

    if (!sameRow) {
      nextAction = (sameColumn)
          ? "need to stay in the same column"
          : "can go everywhere";
    }

    this.logger.info("\tAfter {} decision(s) and same artificial intelligence we decided that we {}", count,
        nextAction);

    if (lastSquare.column == square.column && ((lastSquare.row == square.row - 1 || lastSquare.row == square.row + 1)
        || (firstSquare.row == square.row - 1 || firstSquare.row == square.row + 1))) {
      if ((sameColumn && !sameRow) || (!sameColumn && !sameRow)) {
        squares[count] = square;
        return true;
      }
    } else if (lastSquare.row == square.row
        && ((lastSquare.column == square.column - 1 || lastSquare.column == square.column + 1)
            || (firstSquare.column == square.column - 1 || firstSquare.column == square.column + 1))) {
      if ((sameRow && !sameColumn) || (!sameColumn && !sameRow)) {
        squares[count] = square;
        return true;
      }
    }
    return false;
  }
}
