/*
 *
 * Created on 13 september 2006, 8:48
 *
 */

package battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author 0777974
 */
public class GameClient extends Game {

  public static void main(String[] args) {
    new GameClient();
  }

  /** Creates a new instance of SpelClient */
  public GameClient() {
    super();
    makeConnection();
    enableAvailableBoatButtons();
  }

  public void makeConnection() {
    ui.setTitle("Client - BattleShip");

    /** Startup client */
    try {

      ui.setText("Connection via port number '1337'. IP address of the server is unknown.");

      System.out.println("Make a connection");

      /** IP aanvragen */
      String ipAddress = JOptionPane.showInputDialog(null, "Please, provide the IP address of the server", "IP address",
          JOptionPane.QUESTION_MESSAGE);

      /** Een client socket maken */
      socket = new Socket(ipAddress, 1337);

      System.out.println("Connection established with the server");

      ui.setText("Tegenstander gevonden, plaats de boten.");

      /** Data input */
      in = new BufferedReader(new InputStreamReader(
          socket.getInputStream()));

      /** Data output */
      out = new PrintWriter(
          socket.getOutputStream(), true);

      System.out.println("Fully loaded");

      settingUp = true;

    } catch (IOException ex) {
      System.err.println(ex);
    }
  }

  public void doneButton() {
    ui.enableDoneButton(false);
    out.println("done");
    out.flush();
    waitForHostToBeReady();
    ui.setText("Setting up is done, the game will start!");
    System.out.println("Let's get ready to rumble!");
    this.audioManager.PlayStart();
    yourTurn = true;
  }

  public void waitForHostToBeReady() {
    ui.setText("Waiting for host to be ready");
    try {
      while (!in.readLine().equals("done")) {
      }
    } catch (IOException ex) {
      System.err.println(ex);
    }
  }
}
