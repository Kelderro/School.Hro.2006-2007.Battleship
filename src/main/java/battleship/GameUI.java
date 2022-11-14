/*
 *
 * Created on 13 september 2006, 8:47
 *
 */

package battleship;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import javax.swing.JButton;

/**
 *
 * @author 0777974
 */
public class GameUI extends JFrame implements ActionListener {

  private Game game;
  private JTextField tf1;
  private JButton done;
  private JButton placeMinesweeper;
  private JButton placeFrigate;
  private JButton placeAircraftCarrier;
  protected Board opponent;
  protected Board own;

  private static final Logger logger = LoggerFactory.getLogger(GameUI.class.getName());

  public GameUI(Game game) {
    JPanel northPanel = new JPanel(new BorderLayout());
    JPanel centerPanel = new JPanel(new BorderLayout());
    JPanel southPanel = new JPanel();
    tf1 = new JTextField();
    done = new JButton();
    placeMinesweeper = new JButton();
    placeFrigate = new JButton();
    placeAircraftCarrier = new JButton();
    own = new Board(game);
    opponent = new Board(game);

    /** Define global variable: game */
    this.game = game;

    /** Config TextField: TF1 */
    tf1.setText("Waiting for an opponent. Port `1337` is made available for an incoming connection.");
    tf1.setEditable(false);
    tf1.setBackground(Color.WHITE);

    /** Config Button: done */
    done.setText("Done");
    done.setVisible(true);
    done.addActionListener(this);
    done.setActionCommand("done");

    /** Config Button: placeMineSweeper */
    placeMinesweeper.setText("Place mine sweeper");
    placeMinesweeper.setVisible(true);
    placeMinesweeper.addActionListener(this);
    placeMinesweeper.setActionCommand("placeMineSweeper");

    /** Config Button: placeFrigate */
    placeFrigate.setText("Place frigate");
    placeFrigate.setVisible(true);
    placeFrigate.addActionListener(this);
    placeFrigate.setActionCommand("placeFrigate");

    /** Config Button: placeAircraftCarrier */
    placeAircraftCarrier.setText("Place aircraft carrier");
    placeAircraftCarrier.setVisible(true);
    placeAircraftCarrier.addActionListener(this);
    placeAircraftCarrier.setActionCommand("placeAircraftCarrier");

    /** Config Panel: northPanel */
    northPanel.add(tf1);

    /** Config Panel: centerPanel */
    centerPanel.setLayout(new GridLayout(1, 1, 10, 10));
    centerPanel.add(own);
    centerPanel.add(opponent);

    /** Config Panel: southPanel */
    southPanel.setLayout(new FlowLayout());
    southPanel.add(done);
    southPanel.add(placeMinesweeper);
    southPanel.add(placeFrigate);
    southPanel.add(placeAircraftCarrier);

    /** Config Window: **/
    getContentPane().add(northPanel, BorderLayout.NORTH);
    getContentPane().add(centerPanel, BorderLayout.CENTER);
    getContentPane().add(southPanel, BorderLayout.SOUTH);
    setTitle("Battleship!");
    setResizable(false);
    setSize(800, 600);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    /** Default: disable button in the southPanel **/
    enableDoneButton(false);
    enablePlaceMinesweeper(false);
    enablePlaceFrigate(false);
    enablePlaceAircraftCarrier(false);
  }

  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "done":
        game.doneButton();
        break;
      case "placeMinesweeper":
        game.createMinesweeper();
        break;
      case "placeFrigate":
        game.createFrigate();
        break;
      case "placeAircraftCarrier":
        game.createAircraftCarrier();
        break;
      default:
        this.logger.debug("A not supported action has been performaned. The action command is '{}'",
            e.getActionCommand());
        break;
    }
  }

  public void setText(String text) {
    tf1.setText(text);
  }

  public void enableDoneButton(boolean value) {
    done.setEnabled(value);
  }

  public void enablePlaceMinesweeper(boolean value) {
    placeMinesweeper.setEnabled(value);
  }

  public void enablePlaceAircraftCarrier(boolean value) {
    placeAircraftCarrier.setEnabled(value);
  }

  public void enablePlaceFrigate(boolean value) {
    placeFrigate.setEnabled(value);
  }
}