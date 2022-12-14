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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 0777974
 */
public class GameUI extends JFrame implements ActionListener {

  private Game game;
  private JTextField statusBar;
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
    statusBar = new JTextField();
    done = new JButton();
    placeMinesweeper = new JButton();
    placeFrigate = new JButton();
    placeAircraftCarrier = new JButton();
    own = new Board(game);
    opponent = new Board(game);

    this.game = game;

    statusBar.setText(
        String.format(
            "Waiting for an opponent. Port `%s` is made available for an incoming connection.",
            Game.PORTNUMBER));

    statusBar.setEditable(false);
    statusBar.setBackground(Color.WHITE);

    done.setText("Done");
    done.setVisible(true);
    done.addActionListener(this);
    done.setActionCommand("done");

    placeMinesweeper.setText("Place mine sweeper");
    placeMinesweeper.setVisible(true);
    placeMinesweeper.addActionListener(this);
    placeMinesweeper.setActionCommand("placeMinesweeper");

    placeFrigate.setText("Place frigate");
    placeFrigate.setVisible(true);
    placeFrigate.addActionListener(this);
    placeFrigate.setActionCommand("placeFrigate");

    placeAircraftCarrier.setText("Place aircraft carrier");
    placeAircraftCarrier.setVisible(true);
    placeAircraftCarrier.addActionListener(this);
    placeAircraftCarrier.setActionCommand("placeAircraftCarrier");

    northPanel.add(statusBar);

    centerPanel.setLayout(new GridLayout(1, 1, 10, 10));
    centerPanel.add(own);
    centerPanel.add(opponent);

    southPanel.setLayout(new FlowLayout());
    southPanel.add(done);
    southPanel.add(placeMinesweeper);
    southPanel.add(placeFrigate);
    southPanel.add(placeAircraftCarrier);

    /** Config Window: * */
    getContentPane().add(northPanel, BorderLayout.NORTH);
    getContentPane().add(centerPanel, BorderLayout.CENTER);
    getContentPane().add(southPanel, BorderLayout.SOUTH);
    setTitle("Battleship!");
    setResizable(false);
    setSize(800, 600);
    setVisible(true);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
        logger.debug(
            "A not supported action has been performaned. The action command is '{}'",
            e.getActionCommand());
        break;
    }
  }

  public void setText(String text) {
    statusBar.setText(text);
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
