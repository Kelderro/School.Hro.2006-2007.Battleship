/*
 * ZeeSlag.java
 *
 * Created on 9 oktober 2006, 23:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package zeeslag;

import javax.swing.JOptionPane;

/**
 *
 * @author Rob
 */
public class ZeeSlag {
  
  /** Creates a new instance of ZeeSlag */
  public static void main(String[] args) {
    if (JOptionPane.showConfirmDialog(null,"Wilt u de server zijn?") == 0) {
      new SpelHost();
    } else {
      new SpelClient();
    }
  }
}
