/*
 * ZeePanel.java
 *
 * Created on 13 september 2006, 8:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package zeeslag;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;

/**
 *
 * @author 0777974
 */
public class ZeePanel extends JPanel implements ActionListener{
    
    private int breedte;
    
    private Spel spel;
    private JButton[][] buttons;
    
    /** Creates a new instance of ZeePanel */
    public ZeePanel(Spel spel) {
      this.spel = spel;
      buttons   = new JButton[16][16];
      setLayout(new GridLayout(16,16,5,5));
      for (int rij=0;rij<16;rij++)
      {
        for (int kolom=0;kolom<16;kolom++)
        {
            buttons[rij][kolom] = new JButton();
            buttons[rij][kolom].setBackground(Color.CYAN);
            add(buttons[rij][kolom]);
            buttons[rij][kolom].addActionListener(this);
            buttons[rij][kolom].setActionCommand(rij + "," + kolom);
        }
      }
    }
    
    public void setBootKnop(int rij, int kolom, int toestand) {
        switch (toestand) {
            case 0:
                // Boot neerzetten
                buttons[rij][kolom].setBackground(Color.BLACK);
            break;
            case 1:
                // Plons
                buttons[rij][kolom].setBackground(Color.BLUE);
            break;
            case 2:
                // Raak
                buttons[rij][kolom].setBackground(Color.ORANGE);
            break;
            case 3:
                // Boot verloren
                buttons[rij][kolom].setBackground(Color.RED);
            break;
        }
    }
    
    public void actionPerformed(ActionEvent e) {
      String Location[];
      Location = e.getActionCommand().split(",");
      spel.zeePaneelKnop(Integer.parseInt(Location[0]),Integer.parseInt(Location[1]));
    }
}
