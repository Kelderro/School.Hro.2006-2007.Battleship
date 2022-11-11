/*
 * SpelHost.java
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
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author 0777974
 */
public class SpelHost extends Spel {
  
    public static void main(String[] args) {
        new SpelHost();
    }
    
    /** Creates a new instance of SpelHost */
    public SpelHost() {
      super();

      ui.setTitle("Server - ZeeSlag");
      
      // Wait for a 
      wachtOpTegenstander();

      out.println("ZeeSlag for:\n" +
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
      
      ui.setText("Tegenstander gevonden");
      
      // Wait for ready from the 
      wachtOpKlaarClient();
      ui.setText("Tegenstander is klaar");
      
      // Enable the buttons
      opstellen = true;
      enableJuisteBootKnoppen();
    }
    
    public void wachtOpTegenstander() {
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

      } catch(IOException ex){
        System.err.println(ex);
      }
    }
    
    public void wachtOpKlaarClient() {
      try {
        while(!in.readLine().equals("klaar")) {
        }
      } catch(IOException ex) {
        System.err.println(ex);
      }
    }
    
    public void klaarKnop() {
        ui.enableKlaarKnop(false);
        out.println("klaar");
        out.flush();
        clipStartGame.play();
        wachtOpBeurt();
    }
}
