/*
 *
 * Created on 13 september 2006, 8:47
 *
 */

package zeeslag;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JButton;

/**
 *
 * @author 0777974
 */
public class GameUI extends JFrame implements ActionListener {

  public Game spel;
  public JTextField tf1;
  public JButton done;
  public JButton placeMinesweeper;
  public JButton placeFrigate;
  public JButton placeAircraftCarrier;
  public SeaPanel opponent;
  public SeaPanel own;

  /** Creates a new instance of SpelUI */
  public GameUI(Game spel) {
    /** Define variables */
    JPanel northPanel = new JPanel(new BorderLayout());
    JPanel centerPanel = new JPanel(new BorderLayout());
    JPanel southPanel = new JPanel();
    tf1 = new JTextField();
    done = new JButton();
    placeMinesweeper = new JButton();
    placeFrigate = new JButton();
    placeAircraftCarrier = new JButton();
    own = new SeaPanel(spel);
    opponent = new SeaPanel(spel);

    /** Define global variable: spel */
    this.spel = spel;

    /** Config TextField: TF1 */
    tf1.setText("Waiting for opponent. Port `1337` is made available for an incoming connection.");
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
    enablePlaceMinesweeper(false);
    enableDoneButton(false);
    enablePlaceFrigate(false);
    enablePlaceAircraftCarrier(false);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand() == "done") {
      spel.doneButton();
    } else if (e.getActionCommand() == "placeMinesweeper") {
      spel.createMinesweeper();
    } else if (e.getActionCommand() == "placeFrigate") {
      spel.createFrigate();
    } else if (e.getActionCommand() == "placeAircraftCarrier") {
      spel.createAircraftCarrier();
    }
  }

  public void setText(String text) {
    tf1.setText(text);
  }

  public void enablePlaceMinesweeper(boolean value) {
    placeMinesweeper.setEnabled(value);
  }

  public void enableDoneButton(boolean value) {
    done.setEnabled(value);
  }

  public void enablePlaceAircraftCarrier(boolean value) {
    placeAircraftCarrier.setEnabled(value);
  }

  public void enablePlaceFrigate(boolean value) {
    placeFrigate.setEnabled(value);
  }
}