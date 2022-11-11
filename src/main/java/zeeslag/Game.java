/*
 *
 * Created on 13 september 2006, 8:47
 *
 */

package zeeslag;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import javax.swing.Timer;
import java.net.Socket;
import java.net.URL;
import javax.swing.JOptionPane;

/**
 *
 * @author 0777974
 */
public abstract class Game extends Applet implements ActionListener {

  protected boolean settingUp = false;
  private boolean game = false;
  private boolean bootmaken = false;
  protected boolean yourTurn = false;
  private boolean gewonnen = false;
  public int width = 16;
  private int amountOfMinesweepers = 0;
  private int maxAmountOfMinesweepers = 3;
  private int amountOfFrigates = 0;
  private int maxAmountOfFrigates = 2;
  private int amountOfAircraftCarriers = 0;
  private int maxAmountOfAircraftCarriers = 1;
  private int maxAmountOfBoats = maxAmountOfMinesweepers + maxAmountOfFrigates + maxAmountOfAircraftCarriers;
  private int amountOfBoats = 0;
  private static int SPLASH = 1;
  private static int HIT = 2;
  private static int SUNK = 3;
  private static int LOST = 4;

  private Boat boat;
  private Boat[] boats;
  public Square[][] squares;
  protected GameUI ui;
  protected BufferedReader in;
  protected PrintWriter out;
  private Timer timer;
  protected Socket socket;

  /** Alleen voor Stephan en Rob */
  public AudioClip clipStartGame;
  public AudioClip clipSplash;
  public AudioClip clipHit;
  public AudioClip clipSunk;

  /** Creates a new instance of Spel */
  public Game() {

    /** Create a new spelUI */
    ui = new GameUI(this);

    /** Load sound files */
    File fileStartGame = new File("horn.wav");
    File fileSplash = new File("plons.wav");
    File fileHit = new File("explode.wav");
    File fileSunk = new File("databan.wav");

    try {
      clipStartGame = Applet.newAudioClip(fileStartGame.toURL());
      clipSplash = Applet.newAudioClip(fileSplash.toURL());
      clipHit = Applet.newAudioClip(fileHit.toURL());
      clipSunk = Applet.newAudioClip(fileSunk.toURL());
    } catch (MalformedURLException e) {
      System.out.println("Exception while loading / initializing the sound files");
    }

    /** Create a timer */
    timer = new Timer(1000, this);

    /** Create a square array */
    squares = new Square[16][16];

    /** Create the squares */
    for (int row = 0; row < 16; row++) {
      for (int column = 0; column < 16; column++) {
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
        ui.own.setBoatButton(row, column, 0);
        if (boat.complete()) {
          boats[amountOfBoats] = boat;
          amountOfBoats++;
          this.enableAvailableBoatButtons();
          System.out.println("\tBoatNumber[" + this.amountOfBoats
              + "] is succesvol geplaatst.\n\tTotaal aantal boten nodig: " + this.maxAmountOfBoats);
          if (this.maxAmountOfBoats == this.amountOfBoats) {
            System.out.println("\nDone with placing the boats!\n");
            settingUp = false;
            game = true;
            ui.enableDoneButton(true);
          }
        }
      }
    } else if (yourTurn) {
      yourTurn = false;
      System.out.print("Attempt [" + row + "," + column + "] was ");
      out.println("attempt," + row + "," + column);
      out.flush();
      String strLine = "";
      try {
        while (strLine.equals("")) {
          strLine = in.readLine();
        }

        int condition = 0;
        if (strLine.equals("splash"))
          condition = 1;
        else if (strLine.equals("hit"))
          condition = 2;
        else if (strLine.equals("sunk"))
          condition = 3;
        else if (strLine.equals("lost"))
          condition = 4;
        else {
          condition = 5;
        }

        switch (condition) {
          case 1:
            clipSplash.play();
            break;
          case 2:
            clipHit.play();
            break;
          case 3:
            clipSunk.play();
            break;
        }

        System.out.println(strLine);
        if (condition == 5) {
          JOptionPane.showMessageDialog(null, "An error occured!\n\nI have no idea what this is:\n" + strLine);
          System.exit(0);
        } else if (condition == 4) {
          JOptionPane.showMessageDialog(null, "You have won the game!\nWorship\n\nNow get a life!");
          System.exit(0);
        }
        ui.opponent.setBoatButton(row, column, condition);
      } catch (IOException ex) {
        System.err.println(ex);
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
      System.err.println(ex);
    }
    System.out.print("Incoming attempt [" + arrStrLine[1] + "," + arrStrLine[2] + "] is ");
    int intCondition = checkPoging(Integer.parseInt(arrStrLine[1]), Integer.parseInt(arrStrLine[2]));
    ui.own.setBoatButton(Integer.parseInt(arrStrLine[1]), Integer.parseInt(arrStrLine[2]), intCondition);
    String strCondition = "";

    switch (intCondition) {
      case 1:
        clipSplash.play();
        break;
      case 2:
        clipHit.play();
        break;
      case 3:
        clipSunk.play();
        break;
    }

    switch (intCondition) {
      case 1:
        strCondition = "splash";
        break;
      case 2:
        strCondition = "hit";
        break;
      case 3:
        strCondition = "sunk";
        break;
      case 4:
        strCondition = "lost";
        break;
      default:
        out.println("Cheating? The game will be closed.");
        System.exit(0);
    }

    out.println(strCondition);
    out.flush();
    System.out.println(strCondition);

    if (intCondition == 4) {
      JOptionPane.showMessageDialog(null, "Lost! WTF why!!");
      System.exit(0);
    }
    yourTurn = true;
    ui.setText("It's your turn!");
  }

  public int checkPoging(int row, int column) {
    Square square = squares[row][column];
    Boat boat = square.getBoat();
    int condition = 1;

    if (boat != null) {
      square.setHit();
      condition = 2;
      if (boat.checkSunk()) {
        condition = 3;
        if (checkLost()) {
          condition = 4;
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
