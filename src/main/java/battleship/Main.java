/*
 *
 * Created on 9 oktober 2006, 23:09
 *
 */

package battleship;

import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Rob
 */
public class Main {

  public static void main(String[] args) {

    Logger logger = LoggerFactory.getLogger(Main.class.getName());

    logger.info(
        "Battleship made in:\n"
            + "\n\r      ___           ___           ___           ___           ___"
            + "\n\r     /\\__\\         /\\  \\         /\\  \\         /\\  \\         /\\__\\"
            + "\n\r    /:/ _/_       /::\\  \\       /::\\  \\        \\:\\  \\       /:/  /"
            + "\n\r   /:/ /\\__\\     /:/\\:\\  \\     /:/\\ \\  \\        \\:\\  \\     /:/  / "
            + "\n\r  /:/ /:/ _/_   /::\\~\\:\\  \\   _\\:\\~\\ \\  \\       /::\\  \\   /:/  /  "
            + "\n\r /:/_/:/ /\\__\\ /:/\\:\\ \\:\\__\\ /\\ \\:\\ \\ \\__\\     /:/\\:\\__\\ /:/__/   "
            + "\n\r \\:\\/:/ /:/  / \\:\\~\\:\\ \\/__/ \\:\\ \\:\\ \\/__/    /:/  \\/__/ \\:\\  \\   "
            + "\n\r  \\::/_/:/  /   \\:\\ \\:\\__\\    \\:\\ \\:\\__\\     /:/  /       \\:\\  \\  "
            + "\n\r   \\:\\/:/  /     \\:\\ \\/__/     \\:\\/:/  /     \\/__/         \\:\\  \\ "
            + "\n\r    \\::/  /       \\:\\__\\        \\::/  /                     \\:\\__\\"
            + "\n\r     \\/__/         \\/__/         \\/__/                       \\/__/"
            + "\n\r      ___           ___           ___           ___           ___     "
            + "\n\r     /\\  \\         /\\__\\         /\\  \\         /\\  \\         /\\  \\    "
            + "\n\r    /::\\  \\       /::|  |       /::\\  \\       /::\\  \\       /::\\  \\   "
            + "\n\r   /:/\\:\\  \\     /:|:|  |      /:/\\:\\  \\     /:/\\:\\  \\     /:/\\:\\  \\  "
            + "\n\r  /::\\~\\:\\  \\   /:/|:|  |__   /:/  \\:\\__\\   /::\\~\\:\\  \\   /::\\~\\:\\  \\ "
            + "\n\r /:/\\:\\ \\:\\__\\ /:/ |:| /\\__\\ /:/__/ \\:|__| /:/\\:\\ \\:\\__\\ /:/\\:\\ \\:\\__\\"
            + "\n\r \\/__\\:\\/:/  / \\/__|:|/:/  / \\:\\  \\ /:/  / \\:\\~\\:\\ \\/__/ \\/_|::\\/:/  /"
            + "\n\r      \\::/  /      |:/:/  /   \\:\\  /:/  /   \\:\\ \\:\\__\\      |:|::/  / "
            + "\n\r      /:/  /       |::/  /     \\:\\/:/  /     \\:\\ \\/__/      |:|\\/__/  "
            + "\n\r     /:/  /        /:/  /       \\::/__/       \\:\\__\\        |:|  |    "
            + "\n\r     \\/__/         \\/__/         ~~            \\/__/         \\|__|    "
            + "\n\r      ___     "
            + "\n\r     /\\  \\    "
            + "\n\r    /::\\  \\   "
            + "\n\r   /:/\\ \\  \\  "
            + "\n\r  _\\:\\~\\ \\  \\ "
            + "\n\r /\\ \\:\\ \\ \\__\\"
            + "\n\r \\:\\ \\:\\ \\/__/"
            + "\n\r  \\:\\ \\:\\__\\  "
            + "\n\r   \\:\\/:/  /  "
            + "\n\r    \\::/  /   "
            + "\n\r     \\/__/    ");

    int panelReponse =
        JOptionPane.showConfirmDialog(
            null,
            "Do you want to be the server?\n\nClick on [Yes] to be the server."
                + "\nClick on [No] to be the client."
                + "\n\nClose this dialog to leave the game directly.\n",
            "Battleship",
            JOptionPane.YES_NO_OPTION);

    switch (panelReponse) {
      case JOptionPane.YES_OPTION:
        new GameHost();
        break;
      case JOptionPane.NO_OPTION:
        new GameClient();
        break;
      default:
        break;
    }
  }
}
