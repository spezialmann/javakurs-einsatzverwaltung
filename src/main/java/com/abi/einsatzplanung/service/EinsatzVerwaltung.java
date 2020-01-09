package com.abi.einsatzplanung.service;

import com.abi.einsatzplanung.domain.Einsatz;
import com.abi.einsatzplanung.domain.EinsatzGeber;
import com.abi.einsatzplanung.domain.List;
import com.abi.einsatzplanung.domain.Zeitstempel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EinsatzVerwaltung {

    static int einsatznummer = 1;

    public static EinsatzGeber<Einsatz> wartendeEinsaetze = new EinsatzGeber<>();
    public static List<Einsatz> aktiveEinsaetze = new List<>();
    public static List<Einsatz> archivierteEinsaetze = new List<>();

    public void neuerNotruf(String pBeschreibung, String pOrt, int pPrioritaet) {
        neuerNotruf(pBeschreibung, pOrt, pPrioritaet, 0);
    }

    public void neuerNotruf(String pBeschreibung, String pOrt, int pPrioritaet, int minutesBefore) {
        Einsatz e = new Einsatz(einsatznummer, new Zeitstempel(minutesBefore), pBeschreibung, pOrt, pPrioritaet);
        wartendeEinsaetze.fuegeEin(e);
        einsatznummer++;
    }

    public Einsatz gibNaechstenEinsatz() {
        return wartendeEinsaetze.gibNaechstenEinsatz();
    }

    public void uebertrageNaechstenEinsatz() {
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
