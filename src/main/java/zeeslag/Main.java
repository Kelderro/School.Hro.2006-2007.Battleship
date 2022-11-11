/*
 *
 * Created on 9 oktober 2006, 23:09
 *
 */

package zeeslag;

import javax.swing.JOptionPane;

/**
 *
 * @author Rob
 */
public class Main {

  /** Creates a new instance of Main */
  public static void main(String[] args) {
    if (JOptionPane.showConfirmDialog(null, "Do you want to be the server?") == 0) {
      new GameHost();
    } else {
      new GameClient();
    }
  }
}
