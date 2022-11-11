/*
 *
 * Created on 10 oktober 2006, 10:15
 *
 */

package zeeslag;

/**
 *
 * @author Rob
 */
public class AircraftCarrier extends Boat {

  public AircraftCarrier() {
    maxSquareCount = 5;
    squares = new Square[maxSquareCount];
  }

  public boolean addSquare(Square square) {
    System.out.println("Aircraft Carrier:\n\tNumber of squares placed: " + count + "\n\tTotal amount of squares:\t"
        + squares.length);
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

  private boolean isPart(Square square) {
    /**
     * Deze functie is speciaal gecodeerd door:
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
    System.out.println("\tNa " + count + " keuze(s) en een techniek hoogstandje is bepaald dat we "
        + ((sameRow) ? "in dezelfde rij moeten blijven"
            : (sameColumn) ? "in dezelfde kolom moeten blijven" : "overal heen mogen"));

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
