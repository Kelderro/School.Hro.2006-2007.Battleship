/*
 * Boot.java
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
public abstract class Boot {
    
    protected int       maxaantal;
    protected int       aantal      = 0;
    protected boolean   gezonken    = false;
    
    public Vak[] bootonderdelen;
    
    /** Creates a new instance of Boot */
    public Boot() {
    }
    
    public abstract boolean addVak(Vak vak);
    
    public boolean compleet() {
      return (aantal == maxaantal);
    }
    
    public boolean checkGezonken() {
      Vak vak;
      for(int i=0;i<aantal;i++) {
        vak = bootonderdelen[i];
        if (!vak.getGeraakt()) {
          gezonken = false;
          return false;
        }
      }
      gezonken = true;
      return true;
    }
    
    public boolean isGezonken() {
      return gezonken;
    }
}
