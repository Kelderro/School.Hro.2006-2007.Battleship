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

/**
 *
 * @author 0777974
 */
public class GameHost extends Game {

  public static void main(String[] args) {
    new GameHost();
  }

  /** Creates a new instance of GameHost */
  public GameHost() {
    super();

    ui.setTitle("Server - BattleShip");

    waitForOpponent();

    out.println("BattleShip for:\n" +
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

    ui.setText("Found an opponent");

    waitForOpponentToBeDone();
    ui.setText("Opponent is ready");

    // Enable the buttons
    settingUp = true;
    enableAvailableBoatButtons();
  }

  public void waitForOpponent() {
    /** Start up server */
    try {
      /** Een server socket maken */
      ServerSocket serverSocket = new ServerSocket(1337);

      /** Luister naar een connectie aanvraag */
      socket = serverSocket.accept();

      /** Data input en output maken */
      in = new BufferedReader(new InputStreamReader(
          socket.getInputStream()));

      out = new PrintWriter(
          socket.getOutputStream(), true);

    } catch (IOException ex) {
      System.err.println(ex);
    }
  }

  public void waitForOpponentToBeDone() {
    try {
      while (!in.readLine().equals("done")) {
      }
    } catch (IOException ex) {
      System.err.println(ex);
    }
  }

  public void doneButton() {
    ui.enableDoneButton(false);
    out.println("done");
    out.flush();
    clipStartGame.play();
    waitForTurn();
  }
}
