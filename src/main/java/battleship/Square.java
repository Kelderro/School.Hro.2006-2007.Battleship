/*
 *
 * Created on 13 september 2006, 8:48
 *
 */

package zeeslag;

/**
 *
 * @author 0777974
 */
public class Square {

    public int row;
    public int column;
    public boolean hit;

    public Boat boat;

    /** Creates a new instance of Square */
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
