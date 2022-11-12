/*
 *
 * Created on 13 september 2006, 8:48
 *
 */

package battleship;

/**
 *
 * @author 0777974
 */
public abstract class Boat {

  protected int maxSquareCount;
  protected int count = 0;
  protected boolean sunk = false;

  public Square[] squares;

  public Boat() {
  }

  public abstract boolean addSquare(Square square);

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
}
