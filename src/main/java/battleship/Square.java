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
public class Square {

    protected int row;
    protected int column;
    private boolean hit;

    private Boat boat;

    public Square(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setHit() {
        hit = true;
    }

    public boolean getHit() {
        return hit;
    }
}
