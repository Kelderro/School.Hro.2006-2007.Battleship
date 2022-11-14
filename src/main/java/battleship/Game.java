/*
 *
 * Created on 13 september 2006, 8:47
 *
 */

package battleship;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import battleship.boats.AircraftCarrier;
import battleship.boats.Frigate;
import battleship.boats.Minesweeper;

import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author 0777974
 */
public abstract class Game implements ActionListener {

  protected boolean settingUp = false;
  protected boolean yourTurn = false;
  private int boardSize = 16;
  private int amountOfMinesweepers = 0;
  private int maxAmountOfMinesweepers = 3;
  private int amountOfFrigates = 0;
  private int maxAmountOfFrigates = 2;
  private int amountOfAircraftCarriers = 0;
  private int maxAmountOfAircraftCarriers = 1;
  private int maxAmountOfBoats = maxAmountOfMinesweepers + maxAmountOfFrigates + maxAmountOfAircraftCarriers;
  private int amountOfBoats = 0;

  private Boat boat;
  private Boat[] boats;
  public Square[][] squares;
  protected GameUI ui;
  protected BufferedReader in;
  protected PrintWriter out;
  private Timer timer;
  protected Socket socket;

  public AudioManager audioManager;

  private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  /** Creates a new instance of Spel */
  public Game() {

    /** Create a new spelUI */
    ui = new GameUI(this);

    /** Load sound files */
    this.audioManager = new AudioManager();

    /** Create a timer */
    timer = new Timer(1000, this);

    /** Create a square array */
    squares = new Square[this.boardSize][this.boardSize];

    /** Create the squares */
    for (int row = 0; row < this.boardSize; row++) {
      for (int column = 0; column < this.boardSize; column++) {
        squares[row][column] = new Square(row, column);
      }
    }

    /** Create the boats */
    boats = new Boat[maxAmountOfBoats];
  }

  public abstract void doneButton();

  public void createMinesweeper() {
    boat = new Minesweeper();
    amountOfMinesweepers++;
    this.disableAllBoatButtons();
  }

  public void createFrigate() {
    boat = new Frigate();
    amountOfFrigates++;
    this.disableAllBoatButtons();
  }

  public void createAircraftCarrier() {
    boat = new AircraftCarrier();
    amountOfAircraftCarriers++;
    this.disableAllBoatButtons();
  }

  public void zeePaneelKnop(int row, int column) {
    Square vak = squares[row][column];

    if (settingUp) {
      if (boat.addSquare(vak)) {
        vak.setBoat(boat);
        ui.own.setBoatButton(row, column, Condition.PLACINGBOAT);
        if (boat.complete()) {
          boats[amountOfBoats] = boat;
          amountOfBoats++;
          this.enableAvailableBoatButtons();
          this.logger.info("\tBoatNumber[" + this.amountOfBoats
              + "] has been placed.\n\tTotal amount of boats: " + this.maxAmountOfBoats);
          if (this.maxAmountOfBoats == this.amountOfBoats) {
            this.logger.info("\nDone with placing the boats!\n");
            settingUp = false;
            ui.enableDoneButton(true);
          }
        }
      }
    } else if (yourTurn) {
      yourTurn = false;
      this.logger.info("Attempt [" + row + "," + column + "] was ");

      String strLine = "";
      try {
        while (strLine.equals("")) {
          strLine = in.readLine();
        }

        Condition condition = Condition.valueOf(strLine);

        PlayConditionAudio(condition);

        this.logger.debug(strLine);
        // if (condition == 5) {
        // JOptionPane.showMessageDialog(null, "An error occured!\n\nI have no idea what
        // this is:\n" + strLine);
        // System.exit(0);
        // } else
        if (condition == Condition.LOST) {
          JOptionPane.showMessageDialog(null, "You have won the game!\nWorship\n\nNow get a life!");
          System.exit(0);
        }
        ui.opponent.setBoatButton(row, column, condition);
      } catch (IOException ex) {
        this.logger.error("Error occured", ex);
      }
      timer.start();
      ui.setText("Wait for your turn!");
    }
  }

  public void waitForTurn() {
    String[] arrStrLine = new String[3];
    try {
      arrStrLine[0] = "";
      arrStrLine[1] = "";
      arrStrLine[2] = "";

      while (!arrStrLine[0].equals("attempt")) {
        arrStrLine = in.readLine().split(",");
      }
    } catch (IOException ex) {
      this.logger.error("Error occured", ex);
    }
    System.out.print("Incoming attempt [" + arrStrLine[1] + "," + arrStrLine[2] + "] is ");
    Condition condition = checkAttempt(Integer.parseInt(arrStrLine[1]), Integer.parseInt(arrStrLine[2]));
    ui.own.setBoatButton(Integer.parseInt(arrStrLine[1]), Integer.parseInt(arrStrLine[2]), condition);

    PlayConditionAudio(condition);

    String strCondition = condition.toString();

    this.logger.debug(strCondition);

    if (condition == Condition.LOST) {
      JOptionPane.showMessageDialog(null, "Lost! WTF why!!");
      System.exit(0);
    }
    yourTurn = true;
    ui.setText("It's your turn!");
  }

  private void PlayConditionAudio(Condition condition) {
    switch (condition) {
      case SPLASH:
        audioManager.PlaySplash();
        break;
      case HIT:
        audioManager.PlayHit();
        break;
      case SUNK:
        audioManager.PlayBoatSunk();
        break;
      default:
        break;
    }
  }

  public Condition checkAttempt(int row, int column) {
    Square square = squares[row][column];
    Boat boat = square.getBoat();
    Condition condition = Condition.SPLASH;

    if (boat != null) {
      square.setHit();
      condition = Condition.HIT;
      if (boat.checkSunk()) {
        condition = Condition.SUNK;
        if (checkLost()) {
          condition = Condition.LOST;
        }
      }
      ui.own.setBoatButton(row, column, condition);
    }
    return condition;
  }

  public boolean checkLost() {
    for (int i = 0; i < boats.length; i++) {
      if (!boats[i].isSunk()) {
        return false;
      }
    }
    return true;
  }

  public void actionPerformed(ActionEvent e) {
    timer.stop();
    this.waitForTurn();
  }

  public void enableAvailableBoatButtons() {
    if (amountOfFrigates != maxAmountOfFrigates) {
      ui.enablePlaceFrigate(true);
    }

    if (amountOfMinesweepers != maxAmountOfMinesweepers) {
      ui.enablePlaceMinesweeper(true);
    }

    if (amountOfAircraftCarriers != maxAmountOfAircraftCarriers) {
      ui.enablePlaceAircraftCarrier(true);
    }
  }

  public void disableAllBoatButtons() {
    ui.enablePlaceFrigate(false);
    ui.enablePlaceMinesweeper(false);
    ui.enablePlaceAircraftCarrier(false);
  }
}
