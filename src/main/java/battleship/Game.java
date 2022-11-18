/*
 *
 * Created on 13 september 2006, 8:47
 *
 */

package battleship;

import battleship.boats.AircraftCarrier;
import battleship.boats.Frigate;
import battleship.boats.Minesweeper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
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
  private int maxAmountOfBoats =
      maxAmountOfMinesweepers + maxAmountOfFrigates + maxAmountOfAircraftCarriers;
  private int amountOfBoats = 0;
  public static final int PORTNUMBER = 1337;

  private Boat boat;
  private Boat[] boats;
  private Square[][] squares;
  protected GameUI ui;
  protected BufferedReader in;
  protected PrintWriter out;
  private Timer timer;
  protected Socket socket;

  protected AudioManager audioManager;

  private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  protected Game() {

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
    this.ui.own.enableBoard(true);
  }

  public void createFrigate() {
    boat = new Frigate();
    amountOfFrigates++;
    this.disableAllBoatButtons();
    this.ui.own.enableBoard(true);
  }

  public void createAircraftCarrier() {
    boat = new AircraftCarrier();
    amountOfAircraftCarriers++;
    this.disableAllBoatButtons();
    this.ui.own.enableBoard(true);
  }

  public void settingUp(int row, int column) {
    Square square = squares[row][column];

    if (!boat.claimSquare(square)) {
      return;
    }

    square.setBoat(boat);
    ui.own.setBoatButton(square.row, square.column, Condition.PLACINGBOAT);

    if (!boat.complete()) {
      return;
    }

    boats[amountOfBoats] = boat;
    amountOfBoats++;
    this.enableAvailableBoatButtons();
    this.ui.own.enableBoard(false);

    this.logger.info(
        "\tBoatNumber[{}] has been placed." + "\n\tTotal amount of boats: {}",
        this.amountOfBoats,
        this.maxAmountOfBoats);

    if (this.maxAmountOfBoats != this.amountOfBoats) {
      return;
    }

    this.logger.info("Done with placing the boats!");
    settingUp = false;
    ui.enableDoneButton(true);
  }

  public void boardButton(int row, int column) {
    if (settingUp) {
      settingUp(row, column);
      return;
    }

    if (yourTurn) {
      yourTurn = false;
      this.logger.info("Attempt [{},{}] was ", row, column);

      out.println(String.format("attempt,%d,%d", row, column));
      out.flush();

      String strLine = "";
      try {
        while (strLine.equals("")) {
          strLine = in.readLine();
        }

        Condition condition = Condition.valueOf(strLine);

        playConditionAudio(condition);

        this.logger.debug(strLine);

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

      while (!arrStrLine[0].equalsIgnoreCase("attempt")) {
        arrStrLine = in.readLine().split(",");
      }
    } catch (IOException ex) {
      this.logger.error("Error occured", ex);
    }
    this.logger.info("Incoming attempt [{},{}] is ", arrStrLine[1], arrStrLine[2]);

    Condition condition =
        checkAttempt(Integer.parseInt(arrStrLine[1]), Integer.parseInt(arrStrLine[2]));
    ui.own.setBoatButton(
        Integer.parseInt(arrStrLine[1]), Integer.parseInt(arrStrLine[2]), condition);

    playConditionAudio(condition);

    String strCondition = condition.toString();

    // Inform the other player about the attempt
    out.println(strCondition);
    out.flush();

    this.logger.debug(strCondition);

    if (condition == Condition.LOST) {
      JOptionPane.showMessageDialog(null, "Lost! WTF why!!");
      System.exit(0);
    }
    yourTurn = true;
    ui.setText("It's your turn!");
  }

  private void playConditionAudio(Condition condition) {
    switch (condition) {
      case SPLASH:
        audioManager.playSplash();
        break;
      case HIT:
        audioManager.playHit();
        break;
      case SUNK:
        audioManager.playBoatSunk();
        break;
      default:
        break;
    }
  }

  public Condition checkAttempt(int row, int column) {
    Square square = squares[row][column];
    Boat squareBoat = square.getBoat();
    Condition condition = Condition.SPLASH;

    if (squareBoat != null) {
      square.setHit();
      condition = Condition.HIT;
      if (squareBoat.checkSunk()) {
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
