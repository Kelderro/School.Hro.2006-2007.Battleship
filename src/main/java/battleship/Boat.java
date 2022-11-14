/*
 *
 * Created on 13 september 2006, 8:48
 *
 */

package battleship;

import java.util.Arrays;
import java.util.function.ToIntFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 0777974
 */
public abstract class Boat {

  private int maxSize;
  protected int currentSize = 0;
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

    this.maxSize = maxSquareCount;
    this.squares = new Square[maxSquareCount];
    this.boatName = boatName;
  }

  public boolean complete() {
    return (currentSize == maxSize);
  }

  public boolean checkSunk() {
    Square square;
    for (int i = 0; i < currentSize; i++) {
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

  public boolean claimSquare(Square claimSquare) {
    this.logger.info("{}:\n\tNumber of squares placed: {}\n\tTotal amount of squares:\t{}", boatName, currentSize,
        squares.length);

    if (claimSquare.isClaimed()) {
      this.logger.info(
          "Unable to place boat in square [{}] as it has been claimed. Player need to select another square.",
          claimSquare);
      return false;
    }

    if (isPart(claimSquare)) {
      squares[currentSize++] = claimSquare;
      return true;
    }

    return false;
  }

  /**
   * A boat must consist out of linked squares. This
   * function verifies if the square that is selected
   * to be added to the boat is linked to any of
   * the previously selected squares of the boat.
   * 
   * @param square
   * @return false, the square is not linked to a previous selected square for
   *         this boat
   *         true, the square is linked to a previous selected square for this
   *         boat
   */
  protected boolean isPart(Square square) {
    /**
     * This magnificent function has been coded by:
     * (0777974) - Rob op den Kelder
     * (0777556) - Stephan Klop
     * Westland corp.
     */

    if (currentSize == 0) {
      return true;
    }

    boolean verticalBoat = true;
    boolean horizontalBoat = true;

    if (currentSize == 1) {
      this.logger.info(
          "Only a single piece has been selected for the boat. The player is free to go horizontal or vertical with the current boat.");
    } else {
      if (squares[0].row == squares[1].row) {
        verticalBoat = false;
      } else {
        horizontalBoat = false;
      }

      this.logger.info(
          "The boat is positioned in a {} way.\nThe next piece for the boat must be on the same {} as the previous piece.",
          horizontalBoat ? "horizontal" : "vertical", horizontalBoat ? "row" : "column");
    }

    if (verticalBoat && Boolean.TRUE.equals(isLinked(square, p -> p.row))) {
      return true;
    }

    return horizontalBoat && Boolean.TRUE.equals(isLinked(square, p -> p.column));
  }

  /**
   * Verifies if the new square is at the start or the end of the boat and not
   * some random block on the grid.
   * 
   * @param square           The square that the player want to claim for the
   *                         boat.
   * @param relevantProperty Delegate to retrieve the column or the row value of
   *                         the square object that is going to be claimed.
   * @return true, the square is correctly placed relative to the already placed
   *         squares for the boat. false, otherwise.
   */
  private Boolean isLinked(Square square, ToIntFunction<Square> relevantProperty) {
    // Sort the squares to be certain the first square of the boat is at position 0
    // of the array and the last square of the boat at the end of the array.
    // Depending on how the boat is placed we need to sort by column or by row
    // property.
    Arrays.sort(squares, (obj1, obj2) -> {
      if (obj1 == null) {
        return obj2 == null ? 0 : 1;
      } else if (obj2 == null) {
        return -1;
      }
      return Integer.compare(relevantProperty.applyAsInt(obj1), relevantProperty.applyAsInt(obj2));
    });

    return relevantProperty.applyAsInt(square) == relevantProperty.applyAsInt(squares[0]) - 1
        || relevantProperty.applyAsInt(square) == relevantProperty.applyAsInt(squares[currentSize - 1]) + 1;
  }
}
