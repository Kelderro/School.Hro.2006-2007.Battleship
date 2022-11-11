/*
 * Vak.java
 *
 * Created on 13 september 2006, 8:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package zeeslag;

/**
 *
 * @author 0777974
 */
public class Vak {
    
    public int rij;
    public int kolom;
    public boolean geraakt;
    
    public Boot boot;
    
    /** Creates a new instance of Vak */
    public Vak(int rij, int kolom) {
        this.rij = rij;
        this.kolom = kolom;
    }
    
    public void setBoot(Boot boot) {
        this.boot = boot;
    }
    
    public Boot getBoot() {
      return boot;
    }
    
    public void  setGeraakt() {
        geraakt = true;
    }
    
    public boolean getGeraakt() {
        return geraakt;
    }
}
