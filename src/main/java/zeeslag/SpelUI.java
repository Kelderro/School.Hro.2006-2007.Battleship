/*
 * SpelUI.java
 *
 * Created on 13 september 2006, 8:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package zeeslag;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JButton;

/**
 *
 * @author 0777974
 */
public class SpelUI extends JFrame implements ActionListener{

    public  Spel        spel;
    public  JTextField  tf1;
    public  JButton     klaar;
    public  JButton     maakMijnenVeger;
    public  JButton     maakFregat;
    public  JButton     maakVliegdekSchip;
    public  ZeePanel    tegenstander;
    public  ZeePanel    eigen;
    
    /** Creates a new instance of SpelUI */
    public SpelUI(Spel spel)  {  
        /** Define variables */
        JPanel  northPanel  = new JPanel(new BorderLayout());
        JPanel  centerPanel = new JPanel(new BorderLayout());
        JPanel  southPanel  = new JPanel();
        tf1                 = new JTextField();
        klaar               = new JButton();
        maakMijnenVeger     = new JButton();
        maakFregat          = new JButton();
        maakVliegdekSchip   = new JButton();
        eigen               = new ZeePanel(spel);
        tegenstander        = new ZeePanel(spel);  

        /** Define global variable: spel */
        this.spel = spel;
        
        /** Config TextField: TF1 */
        tf1.setText("Wacht op tegenstander. Port: `1337` opengezet.");
        tf1.setEditable(false);
        tf1.setBackground(Color.WHITE);
        
        /** Config Button: klaar */
        klaar.setText("Klaar");
        klaar.setVisible(true);
        klaar.addActionListener(this);
        klaar.setActionCommand("klaar");
        
        /** Config Button: maakMijnenVeger */
        maakMijnenVeger.setText("Maak mijnen veger");
        maakMijnenVeger.setVisible(true);
        maakMijnenVeger.addActionListener(this);
        maakMijnenVeger.setActionCommand("maakMijnenVeger");
            
        /** Config Button: maakFregat */
        maakFregat.setText("Maak fregat");
        maakFregat.setVisible(true);
        maakFregat.addActionListener(this);
        maakFregat.setActionCommand("maakFregat");
            
        /** Config Button: maakFregat */
        maakVliegdekSchip.setText("Maak vliegdekschip");
        maakVliegdekSchip.setVisible(true);
        maakVliegdekSchip.addActionListener(this);
        maakVliegdekSchip.setActionCommand("maakVliegdekschip");
        
        /** Config Panel: northPanel */
        northPanel.add(tf1);
        
        /** Config Panel: centerPanel */
        centerPanel.setLayout(new GridLayout(1,1,10,10));
        centerPanel.add(eigen);
        centerPanel.add(tegenstander);
        
        /** Config Panel: southPanel */
        southPanel.setLayout(new FlowLayout());
        southPanel.add(klaar);
        southPanel.add(maakMijnenVeger);
        southPanel.add(maakFregat);
        southPanel.add(maakVliegdekSchip);
        
        /** Config Window: **/
        getContentPane().add(northPanel,  BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(southPanel,  BorderLayout.SOUTH);
        setTitle("Zeeslagje");
        setResizable(false);
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /** Default: disable button in the southPanel **/
        enableMaakMijnenVeger(false);
        enableKlaarKnop(false);
        enableMaakFregat(false);
        enableMaakVliegdekSchip(false);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "klaar"){
          spel.klaarKnop();
        }       
        else if (e.getActionCommand() == "maakMijnenVeger") {
          spel.maakMijnenVeger();
        }
        else if (e.getActionCommand() == "maakFregat") {
          spel.maakFregat();
        } else if (e.getActionCommand() == "maakVliegdekschip") {
          spel.maakVliegdekSchip();
        }
    }
    
    public void setText(String text) {
        tf1.setText(text);
    }
    
    public void enableMaakMijnenVeger(boolean value) {
        maakMijnenVeger.setEnabled(value);
    }
    
    public void enableKlaarKnop(boolean value) {
        klaar.setEnabled(value);
    }
    
    public void enableMaakVliegdekSchip(boolean value) {
        maakVliegdekSchip.setEnabled(value);
    }
    
    public void enableMaakFregat(boolean value) {
        maakFregat.setEnabled(value);
    }
}