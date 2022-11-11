/*
 * Spel.java
 *
 * Created on 13 september 2006, 8:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package zeeslag;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import javax.swing.Timer;
import java.net.Socket;
import java.net.URL;
import javax.swing.JOptionPane;

/**
 *
 * @author 0777974
 */
public abstract class Spel extends Applet implements ActionListener {
    
    protected           boolean opstellen               = false;
    private             boolean spelen                  = false;
    private             boolean bootmaken               = false;
    protected           boolean aandebeurt              = false;
    private             boolean gewonnen                = false;
    public              int     breedte                 = 16;
    private             int     aantalMijnenVegers      = 0;
    private             int     maxAantalMijnenVegers   = 3;
    private             int     aantalFregatten         = 0;
    private             int     maxAantalFregatten      = 2;
    private             int     aantalVliegdekSchepen   = 0;
    private             int     maxAantalVliegdekSchepen = 1;
    private             int     maxAantalBoten          = maxAantalMijnenVegers + maxAantalFregatten + maxAantalVliegdekSchepen;
    private             int     aantalBoten             = 0;
    private     static  int     PLONS                   = 1;
    private     static  int     RAAK                    = 2;
    private     static  int     GEZONKEN                = 3;
    private     static  int     VERLOREN                = 4;

    private     Boot                  boot;
    private     Boot[]                boten;
    public      Vak[][]               vakjes;
    protected   SpelUI                ui;
    protected   BufferedReader        in;
    protected   PrintWriter           out;
    private     Timer                 timer;
    protected   Socket                socket;
    
    /** Alleen voor Stephan en Rob */
    public      AudioClip             clipStartGame;
    public      AudioClip             clipPlons;
    public      AudioClip             clipRaak;
    public      AudioClip             clipGezonken;
    
    /** Creates a new instance of Spel */
    public Spel() {

      /** Nieuw spelUI aanmaken */
      ui    = new SpelUI(this);
      
      /** Geluid laden */
      File fileStartGame  = new File("horn.wav");
      File filePlons      = new File("plons.wav");
      File fileRaak       = new File("explode.wav");
      File fileGezonken   = new File("databan.wav");
      
      try {
        clipStartGame = Applet.newAudioClip(fileStartGame.toURL());
        clipPlons     = Applet.newAudioClip(filePlons.toURL());
        clipRaak      = Applet.newAudioClip(fileRaak.toURL());
        clipGezonken  = Applet.newAudioClip(fileGezonken.toURL());
      } catch( MalformedURLException e ) {
        System.out.println("Fout bij geluidbestand(en)");
      }
      
      /** Timer aanmaken */
      timer = new Timer(1000, this);
      
      /** Aanmaken van de array */
      vakjes = new Vak[16][16];
      
      /** Aanmaken van de vakjes */
      for(int rij=0;rij<16;rij++) {
        for(int kolom=0;kolom<16;kolom++) {
          vakjes[rij][kolom] = new Vak(rij, kolom);
        }
      }
      
      /** Aanmaken van de boten */
      boten = new Boot[maxAantalBoten];
    }
    
    public abstract void klaarKnop();
    
    public void maakMijnenVeger() {
        boot = new MijnenVeger();
        aantalMijnenVegers++;
        this.disableBootKnoppen();
    }
    
    public void maakFregat() {
        boot = new Fregat();
        aantalFregatten++;
        this.disableBootKnoppen();
    }
    
    public void maakVliegdekSchip() {
        boot = new VliegdekSchip();
        aantalVliegdekSchepen++;
        this.disableBootKnoppen();
    }
    
    public void zeePaneelKnop(int rij, int kolom) {
        Vak vak = vakjes[rij][kolom];

        if (opstellen) {
          if (boot.addVak(vak)){
            vak.setBoot(boot);
            ui.eigen.setBootKnop(rij,kolom,0);
            if (boot.compleet()){
              boten[aantalBoten] = boot;
              aantalBoten++;
              this.enableJuisteBootKnoppen();
              System.out.println("\tBootnummer[" + this.aantalBoten + "] is succesvol geplaatst.\n\tTotaal aantal boten nodig: " + this.maxAantalBoten);
              if (this.maxAantalBoten == this.aantalBoten){
                System.out.println("\nKlaar met boten plaatsen!\n");
                opstellen = false;
                spelen    = true;
                ui.enableKlaarKnop(true);
              }
            }
          }
        } else if (aandebeurt) {
          aandebeurt = false;
          System.out.print("Gok [" + rij + "," + kolom + "] was ");
          out.println("gok," + rij + "," + kolom);
          out.flush();
          String strLine = "";
          try {
            while(strLine.equals("")) {
              strLine = in.readLine();
            }
            
            int toestand = 0;
            if (strLine.equals("plons"))
              toestand = 1;
            else if (strLine.equals("raak"))
              toestand = 2;
            else if (strLine.equals("gezonken"))
              toestand = 3;
            else if (strLine.equals("verloren"))
              toestand = 4;
            else {
              toestand = 5;
            }
            
            switch(toestand) {
              case 1: clipPlons.play();     break;
              case 2: clipRaak.play();      break;
              case 3: clipGezonken.play();  break;
            }
            
            System.out.println(strLine);
            if (toestand==5) {
              JOptionPane.showMessageDialog(null,"Fout is opgedoken!\n\nIk snap niet wat dit is:\n" + strLine);
              System.exit(0);
            } else if (toestand==4) {
              JOptionPane.showMessageDialog(null,"U heeft zeeslag gewonnen\nWorship\n\nNow get a life!");
              System.exit(0);
            }
            ui.tegenstander.setBootKnop(rij, kolom, toestand);
          } catch(IOException ex) {
            System.err.println(ex);
          }
         timer.start();
         ui.setText("Wacht op beurt!");
        }
    }

    public void wachtOpBeurt() {
      String[] arrStrLine = new String[3];
      try {
        arrStrLine[0] = "";
        arrStrLine[1] = "";
        arrStrLine[2] = "";

        while(!arrStrLine[0].equals("gok")) {
          arrStrLine = in.readLine().split(",");
        }
      } catch(IOException ex) {
        System.err.println(ex);
      }
      System.out.print("Gok binnengekomen ["+ arrStrLine[1] +","+ arrStrLine[2] + "] is ");
      int intToestand = checkPoging(Integer.parseInt(arrStrLine[1]),Integer.parseInt(arrStrLine[2]));
      ui.eigen.setBootKnop(Integer.parseInt(arrStrLine[1]),Integer.parseInt(arrStrLine[2]),intToestand);
      String strToestand = "";
      
      switch(intToestand) {
        case 1: clipPlons.play();     break;
        case 2: clipRaak.play();      break;
        case 3: clipGezonken.play();  break;
      }
      
      switch(intToestand) {
        case 1: strToestand = "plons";    break;
        case 2: strToestand = "raak";     break;
        case 3: strToestand = "gezonken"; break;
        case 4: strToestand = "verloren"; break;
        default:
          out.println("Cheating? Spel wordt afgesloten.");
          System.exit(0);
      }

      out.println(strToestand);
      out.flush();
      System.out.println(strToestand);
      
      if (intToestand==4) {
        JOptionPane.showMessageDialog(null,"Verloren! WTF waarom!!");
        System.exit(0);
      }
      aandebeurt = true;
      ui.setText("Je bent aan de beurt!");
    }

    public int checkPoging(int rij, int kolom) {
      Vak vak = vakjes[rij][kolom];
      Boot boot = vak.getBoot();
      int toestand = 1;
      
      if (boot != null) {
        vak.setGeraakt();
        toestand = 2;
        if (boot.checkGezonken()) {
          toestand = 3;
          if (checkVerloren()) {
            toestand = 4;
          }
        }
        ui.eigen.setBootKnop(rij,kolom,toestand);
      }
      return toestand;
    }
    
    public boolean checkVerloren() {
      for(int i=0;i<boten.length;i++) {
        if (!boten[i].isGezonken()) {
          return false;
        }
      }
      return true;
    }
    
    public void actionPerformed(ActionEvent e) {
      timer.stop();
      this.wachtOpBeurt();
    }
    
    public void enableJuisteBootKnoppen() {
      if (aantalFregatten != maxAantalFregatten) {
        ui.enableMaakFregat(true);
      }
      
      if (aantalMijnenVegers != maxAantalMijnenVegers) {
        ui.enableMaakMijnenVeger(true);
      }
      
      if (aantalVliegdekSchepen != maxAantalVliegdekSchepen) {
        ui.enableMaakVliegdekSchip(true);
      }
    }
    
    public void disableBootKnoppen() {
      ui.enableMaakFregat(false);
      ui.enableMaakMijnenVeger(false);
      ui.enableMaakVliegdekSchip(false);
    }
}
