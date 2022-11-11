/*
 *
 * Created on 13 september 2006, 8:47
 *
 */

package zeeslag;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;

/**
 *
 * @author 0777974
 */
public class SeaPanel extends JPanel implements ActionListener {

    private int size;

    private Game game;
    private JButton[][] buttons;

    /** Creates a new instance of SeaPanel */
    public SeaPanel(Game game) {
        this.game = game;
        buttons = new JButton[16][16];
        setLayout(new GridLayout(16, 16, 5, 5));
        for (int row = 0; row < 16; row++) {
            for (int column = 0; column < 16; column++) {
                buttons[row][column] = new JButton();
                buttons[row][column].setBackground(Color.CYAN);
                add(buttons[row][column]);
                buttons[row][column].addActionListener(this);
                buttons[row][column].setActionCommand(row + "," + column);
            }
        }
    }

    public void setBoatButton(int row, int column, int condition) {
        switch (condition) {
            case 0:
                // Boot neerzetten
                buttons[row][column].setBackground(Color.BLACK);
                break;
            case 1:
                // Splash / missed
                buttons[row][column].setBackground(Color.BLUE);
                break;
            case 2:
                // Hit
                buttons[row][column].setBackground(Color.ORANGE);
                break;
            case 3:
                // Boot verloren
                buttons[row][column].setBackground(Color.RED);
                break;
        }
    }

    public void actionPerformed(ActionEvent e) {
        String Location[];
        Location = e.getActionCommand().split(",");
        game.zeePaneelKnop(Integer.parseInt(Location[0]), Integer.parseInt(Location[1]));
    }
}
