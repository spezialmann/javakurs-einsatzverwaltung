package com.abi.einsatzplanung.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Einsatz {
    private int pEinsatzNummer;
    private String pBeschreibung;
    private String pEinsatzort;
    private int pPrioritaet;

    private Zeitstempel pNotrufZeitstempel;
    private Zeitstempel pAnkunftZeitstempel;
    private Zeitstempel pAbschlussZeitstempel;

    public Einsatz(int einsatzNummer, Zeitstempel notrufZeitstempel, String beschreibung, String einsatzort, int prioritaet) {
        this.pEinsatzNummer = einsatzNummer;
        this.pNotrufZeitstempel = notrufZeitstempel;
        this.pBeschreibung = beschreibung;
        this.pEinsatzort = einsatzort;
        this.pPrioritaet = prioritaet;
    }

}
