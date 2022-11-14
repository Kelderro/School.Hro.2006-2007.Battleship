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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 0777974
 */
public class GameClient extends Game {

  private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

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

      this.logger.info("Make a connection");

      /** IP aanvragen */
      String ipAddress = JOptionPane.showInputDialog(null, "Please, provide the IP address of the server", "IP address",
          JOptionPane.QUESTION_MESSAGE);

      /** Een client socket maken */
      socket = new Socket(ipAddress, 1337);

      this.logger.info("Connection established with the server");

      ui.setText("Tegenstander gevonden, plaats de boten.");

      /** Data input */
      in = new BufferedReader(new InputStreamReader(
          socket.getInputStream()));

      /** Data output */
      out = new PrintWriter(
          socket.getOutputStream(), true);

      this.logger.info("Fully loaded");

      settingUp = true;

    } catch (IOException ex) {
      this.logger.error("Error occured", ex);
    }
  }

  public void doneButton() {
    ui.enableDoneButton(false);
    this.logger.info("done");
    waitForHostToBeReady();
    ui.setText("Setting up is done, the game will start!");
    this.logger.info("Let's get ready to rumble!");
    this.audioManager.PlayStart();
    yourTurn = true;
  }

  public void waitForHostToBeReady() {
    ui.setText("Waiting for host to be ready");
    try {
      while (!in.readLine().equals("done")) {
      }
    } catch (IOException ex) {
      this.logger.error("Error occured", ex);
    }
  }
}
