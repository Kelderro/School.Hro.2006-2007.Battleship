/*
 *
 * Created on 13 september 2006, 8:47
 *
 */

package battleship;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author 0777974
 */
public class Board extends JPanel implements ActionListener {

  private int size = 16;

  private Game game;
  private JButton[][] buttons;

  public Board(Game game) {
    this.game = game;
    buttons = new JButton[size][size];
    setLayout(new GridLayout(size, size, 5, 5));
    for (int row = 0; row < size; row++) {
      for (int column = 0; column < size; column++) {
        buttons[row][column] = new JButton();
        buttons[row][column].setBackground(Color.CYAN);
        add(buttons[row][column]);
        buttons[row][column].addActionListener(this);
        buttons[row][column].setActionCommand(row + "," + column);
        buttons[row][column].setEnabled(false);
      }
    }
  }

  public void enableBoard(Boolean enable) {
    for (int row = 0; row < size; row++) {
      for (int column = 0; column < size; column++) {
        buttons[row][column].setEnabled(enable);
      }
    }
  }

  public void setBoatButton(int row, int column, Condition condition) {
    switch (condition) {
      case PLACINGBOAT:
        buttons[row][column].setBackground(Color.BLACK);
        break;
      case SPLASH:
        buttons[row][column].setBackground(Color.BLUE);
        break;
      case HIT:
        buttons[row][column].setBackground(Color.ORANGE);
        break;
      case SUNK:
        buttons[row][column].setBackground(Color.RED);
        break;
      default:
        break;
    }
  }

  public void actionPerformed(ActionEvent e) {
    String[] location;
    location = e.getActionCommand().split(",");
    game.boardButton(Integer.parseInt(location[0]), Integer.parseInt(location[1]));
  }
}
