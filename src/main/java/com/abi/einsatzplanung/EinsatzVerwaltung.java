package com.abi.einsatzplanung;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EinsatzVerwaltung {

    static int einsatznummer = 1;

    static EinsatzGeber<Einsatz> wartendeEinsaetze = new EinsatzGeber<>();
    static List<Einsatz> aktiveEinsaetze = new List<>();
    static List<Einsatz> archivierteEinsaetze = new List<>();

    void neuerNotruf(String pBeschreibung, String pOrt, int pPrioritaet) {
        neuerNotruf(pBeschreibung, pOrt, pPrioritaet, 0);
    }

    void neuerNotruf(String pBeschreibung, String pOrt, int pPrioritaet, int minutesBefore) {
        Einsatz e = new Einsatz(einsatznummer, new Zeitstempel(minutesBefore), pBeschreibung, pOrt, pPrioritaet);
        wartendeEinsaetze.fuegeEin(e);
        einsatznummer++;
    }

    Einsatz gibNaechstenEinsatz() {
        return wartendeEinsaetze.gibNaechstenEinsatz();
    }

    void uebertrageNaechstenEinsatz() {
        aktiveEinsaetze.append(wartendeEinsaetze.gibNaechstenEinsatz());
        wartendeEinsaetze.entferneNaechstenEinsatz();
    }

    public void setzeAnkunftZeitstempel(int pEinsatzNummer) {
        aktiveEinsaetze.toFirst();
        boolean gefunden = false;
        while (aktiveEinsaetze.hasAccess() && !gefunden) {
            Einsatz e = aktiveEinsaetze.getContent();
            if(e.getPEinsatzNummer()==pEinsatzNummer) {
                aktiveEinsaetze.remove();
                e.setPAnkunftZeitstempel(new Zeitstempel());
                aktiveEinsaetze.append(e);
                gefunden=true;
            }
            aktiveEinsaetze.next();
        }
    }

    public void archiviereEinsatz(int pEinsatzNummer) {
        aktiveEinsaetze.toFirst();
        boolean gefunden = false;
        while (aktiveEinsaetze.hasAccess() && !gefunden) {
            Einsatz e = aktiveEinsaetze.getContent();
            if(e.getPEinsatzNummer()==pEinsatzNummer) {
                aktiveEinsaetze.remove();
                archivierteEinsaetze.append(e);
                gefunden=true;
            }
            aktiveEinsaetze.next();
        }
    }
}
