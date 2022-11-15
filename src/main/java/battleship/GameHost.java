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
import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 0777974
 */
public class GameHost extends Game {

  private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  public GameHost() {
    super();

    ui.setTitle("Server - BattleShip");

    waitForOpponent();

    ui.setText("Found an opponent");

    waitForOpponentToBeDone();
    ui.setText("Opponent is ready");

    // Enable the buttons
    settingUp = true;
    enableAvailableBoatButtons();
  }

  public void waitForOpponent() {

    try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

      /** Luister naar een connectie aanvraag */
      socket = serverSocket.accept();

      /** Data input en output maken */
      in = new BufferedReader(new InputStreamReader(
          socket.getInputStream()));

      out = new PrintWriter(
          socket.getOutputStream(), true);

    } catch (IOException ex) {
      this.logger.error(
          "Exception occured while trying to open port {}", portNumber, ex);
    }
  }

  public void waitForOpponentToBeDone() {
    try {
      String incomingMessage = "";
      while (!incomingMessage.equalsIgnoreCase("done")) {
        incomingMessage = in.readLine();
      }
    } catch (IOException ex) {
      this.logger.error("Error occured", ex);
    }
  }

  public void doneButton() {
    ui.enableDoneButton(false);
    this.logger.debug("done");
    this.audioManager.playStart();
    waitForTurn();
  }
}
