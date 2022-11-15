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

  public GameClient() {
    super();
    establishConnection();
    enableAvailableBoatButtons();
  }

  public void establishConnection() {
    ui.setTitle("Client - BattleShip");

    /** Startup client */
    try {

      String message = String.format("Connection via port number '%s'. Waiting for IP address of the server.",
          this.portNumber);

      ui.setText(message);

      this.logger.info(message);

      String ipAddress = JOptionPane.showInputDialog(null, "Please, provide the IP address of the server", "IP address",
          JOptionPane.QUESTION_MESSAGE);

      socket = new Socket(ipAddress, this.portNumber);

      this.logger.info("Connection established with the server");

      ui.setText("Connection established, please place your boats.");

      /** Data input */
      in = new BufferedReader(new InputStreamReader(
          socket.getInputStream()));

      /** Data output */
      out = new PrintWriter(
          socket.getOutputStream(), true);

      this.logger.debug("Fully loaded");

      settingUp = true;

    } catch (IOException ex) {
      this.logger.error("Error occured", ex);

      JOptionPane.showMessageDialog(ui,
          String.format("Failed to setup a connection. Exception message: %s", ex.getMessage()), "Failed to connect",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  public void doneButton() {
    ui.enableDoneButton(false);
    this.logger.info("done");
    waitForHostToBeReady();
    ui.setText("Setting up is done, the game will start!");
    this.logger.info("Let's get ready to rumble!");
    this.audioManager.playStart();
    yourTurn = true;
  }

  public void waitForHostToBeReady() {
    ui.setText("Waiting for host to be ready");
    try {
      String incomingMessage = "";
      while (!incomingMessage.equalsIgnoreCase("done")) {
        incomingMessage = in.readLine();
      }
    } catch (IOException ex) {
      this.logger.error("Error occured", ex);
    }
  }
}
