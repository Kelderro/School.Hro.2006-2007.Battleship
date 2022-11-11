/*
 * SpelClient.java
 *
 * Created on 13 september 2006, 8:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package zeeslag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author 0777974
 */
public class SpelClient extends Spel {
    
    public static void main(String[] args) {
        new SpelClient();
    }
    
    /** Creates a new instance of SpelClient */
    public SpelClient() {
        super();
        maakVerbinding();
        enableJuisteBootKnoppen();
    }
    
    public void maakVerbinding() {
      ui.setTitle("Client - ZeeSlag");
      /** Startup client */
      try {
        
        ui.setText("Verbinding loopt via port '1337'. IP adres van de server onbekend.");
        
        System.out.println("Verbinding maken");
        
        /** IP aanvragen */
        String ipAddress = JOptionPane.showInputDialog(null,"Geef het IP adres van de server","IP adres",JOptionPane.QUESTION_MESSAGE);
        
        /** Een client socket maken */
        socket = new Socket(ipAddress,1337);
        
        System.out.println("Verbinding gemaakt");
        
        ui.setText("Tegenstander gevonden, plaats de boten.");
        
        /** Data input */
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        
        /** Data output */
        out = new PrintWriter(
                socket.getOutputStream(), true);
        
        System.out.println("Volledig geladen");
        
        opstellen = true;
        
      } catch(IOException ex){
        System.err.println(ex);
      }
    }
    
    public void klaarKnop() {
        ui.enableKlaarKnop(false);
        out.println("klaar");
        out.flush();
        wachtOpHostKlaar();
        ui.setText("Opzetten is klaar, spel begint!");
        System.out.println("Let's get ready to rumble!");
        clipStartGame.play();
        aandebeurt = true;
    }
    
    public void wachtOpHostKlaar() {
      ui.setText("Wachten op host klaar");
      try {
        while(!in.readLine().equals("klaar")) {
        }
      } catch(IOException ex){
        System.err.println(ex);
      }
    }
}
