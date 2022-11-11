/*
 * VliegdekSchip.java
 *
 * Created on 10 oktober 2006, 10:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package zeeslag;

/**
 *
 * @author Rob
 */
public class VliegdekSchip extends Boot {
  
  /** Creates a new instance of VliegdekSchip */
  public VliegdekSchip() {
    maxaantal = 5;
    bootonderdelen = new Vak[maxaantal];
  }
  
      public boolean addVak(Vak vak) {
      System.out.println("VliegdekSchip:\n\tAantal geplaatste onderdelen: " + aantal + "\n\tTotaal aan onderdelen:\t" + bootonderdelen.length);
      if (vak.getBoot() == null){
          if(aantal==0){
              bootonderdelen[aantal++]=vak;
              return true;
          }
          else {
              if (isOnderdeel(vak)) {
                bootonderdelen[aantal++]=vak;
                return true;
              }
          }
      }
      return false;
    }
  
    private boolean isOnderdeel(Vak vak) {
      /** Deze functie is speciaal gecodeerd door:
       * (0777974) - Rob op den Kelder 
       * (0777556) - Stephan Klop
       * Westland corp.
       */
          Vak firstVak = bootonderdelen[0];
          Vak lastVak  = bootonderdelen[aantal-1];

          boolean bolZelfdeKolom  = false;
          boolean bolZelfdeRij    = false;
          for(int i=1;i<aantal;i++) {
            if (bootonderdelen[i-1].kolom == bootonderdelen[i].kolom) {
              bolZelfdeKolom = true;
            } else {
              bolZelfdeRij = true;
            }
          }
          System.out.println("\tNa " + aantal + " keuze(s) en een techniek hoogstandje is bepaald dat we " + ((bolZelfdeRij)?"in dezelfde rij moeten blijven":(bolZelfdeKolom)?"in dezelfde kolom moeten blijven":"overal heen mogen"));
                    
          if (lastVak.kolom == vak.kolom && ((lastVak.rij == vak.rij - 1 || lastVak.rij == vak.rij + 1) || (firstVak.rij == vak.rij - 1 || firstVak.rij == vak.rij + 1))) {
            if ((bolZelfdeKolom && !bolZelfdeRij) || (!bolZelfdeKolom && !bolZelfdeRij)) {
              bootonderdelen[aantal]=vak;
              return true;
            }
          } else if (lastVak.rij == vak.rij && ((lastVak.kolom == vak.kolom - 1 || lastVak.kolom == vak.kolom + 1) || (firstVak.kolom == vak.kolom - 1 || firstVak.kolom == vak.kolom + 1))) {
            if ((bolZelfdeRij && !bolZelfdeKolom) || (!bolZelfdeKolom && !bolZelfdeRij)) {
              bootonderdelen[aantal]=vak;
              return true;
            }
          }
      return false;
    }
}
