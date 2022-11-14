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

  public static void main(String[] args) {
    new GameHost();
  }

  /** Creates a new instance of GameHost */
  public GameHost() {
    super();

    ui.setTitle("Server - BattleShip");

    waitForOpponent();

    System.out.println("BattleShip for:\n" +
        "\n\r      ___           ___           ___           ___           ___" +
        "\n\r     /\\__\\         /\\  \\         /\\  \\         /\\  \\         /\\__\\" +
        "\n\r    /:/ _/_       /::\\  \\       /::\\  \\        \\:\\  \\       /:/  /" +
        "\n\r   /:/ /\\__\\     /:/\\:\\  \\     /:/\\ \\  \\        \\:\\  \\     /:/  / " +
        "\n\r  /:/ /:/ _/_   /::\\~\\:\\  \\   _\\:\\~\\ \\  \\       /::\\  \\   /:/  /  " +
        "\n\r /:/_/:/ /\\__\\ /:/\\:\\ \\:\\__\\ /\\ \\:\\ \\ \\__\\     /:/\\:\\__\\ /:/__/   " +
        "\n\r \\:\\/:/ /:/  / \\:\\~\\:\\ \\/__/ \\:\\ \\:\\ \\/__/    /:/  \\/__/ \\:\\  \\   " +
        "\n\r  \\::/_/:/  /   \\:\\ \\:\\__\\    \\:\\ \\:\\__\\     /:/  /       \\:\\  \\  " +
        "\n\r   \\:\\/:/  /     \\:\\ \\/__/     \\:\\/:/  /     \\/__/         \\:\\  \\ " +
        "\n\r    \\::/  /       \\:\\__\\        \\::/  /                     \\:\\__\\" +
        "\n\r     \\/__/         \\/__/         \\/__/                       \\/__/" +
        "\n\r      ___           ___           ___           ___           ___     " +
        "\n\r     /\\  \\         /\\__\\         /\\  \\         /\\  \\         /\\  \\    " +
        "\n\r    /::\\  \\       /::|  |       /::\\  \\       /::\\  \\       /::\\  \\   " +
        "\n\r   /:/\\:\\  \\     /:|:|  |      /:/\\:\\  \\     /:/\\:\\  \\     /:/\\:\\  \\  " +
        "\n\r  /::\\~\\:\\  \\   /:/|:|  |__   /:/  \\:\\__\\   /::\\~\\:\\  \\   /::\\~\\:\\  \\ " +
        "\n\r /:/\\:\\ \\:\\__\\ /:/ |:| /\\__\\ /:/__/ \\:|__| /:/\\:\\ \\:\\__\\ /:/\\:\\ \\:\\__\\" +
        "\n\r \\/__\\:\\/:/  / \\/__|:|/:/  / \\:\\  \\ /:/  / \\:\\~\\:\\ \\/__/ \\/_|::\\/:/  /" +
        "\n\r      \\::/  /      |:/:/  /   \\:\\  /:/  /   \\:\\ \\:\\__\\      |:|::/  / " +
        "\n\r      /:/  /       |::/  /     \\:\\/:/  /     \\:\\ \\/__/      |:|\\/__/  " +
        "\n\r     /:/  /        /:/  /       \\::/__/       \\:\\__\\        |:|  |    " +
        "\n\r     \\/__/         \\/__/         ~~            \\/__/         \\|__|    " +
        "\n\r      ___     " +
        "\n\r     /\\  \\    " +
        "\n\r    /::\\  \\   " +
        "\n\r   /:/\\ \\  \\  " +
        "\n\r  _\\:\\~\\ \\  \\ " +
        "\n\r /\\ \\:\\ \\ \\__\\" +
        "\n\r \\:\\ \\:\\ \\/__/" +
        "\n\r  \\:\\ \\:\\__\\  " +
        "\n\r   \\:\\/:/  /  " +
        "\n\r    \\::/  /   " +
        "\n\r     \\/__/    ");

    System.out.flush();

    ui.setText("Found an opponent");

    waitForOpponentToBeDone();
    ui.setText("Opponent is ready");

    // Enable the buttons
    settingUp = true;
    enableAvailableBoatButtons();
  }

  public void waitForOpponent() {
    int portNumber = 1337;
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
          "Exception occured while trying to open port " + portNumber, ex);
    }
  }

  public void waitForOpponentToBeDone() {
    try {
      while (!in.readLine().equals("done")) {
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
