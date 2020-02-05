package com.abi.einsatzplanung.domain;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class EinsatzGeber<ContentType> extends List {

    public void fuegeEin(Einsatz pEinsatz) {

        //neuer Einsatz nicht null?
        if (pEinsatz != null) {
            //Wenn Liste noch komplett leer, dann einfach als ersten Einsatz einfuegen
            if (this.isEmpty()) {
                this.append(pEinsatz);
            } else {
                //Zum Anfang der Liste
                this.toFirst();
                boolean richtigeStelleGefunden = false;
                //"zeiger" an die richtige Stelle in der Liste setzen
                while (this.hasAccess() && !richtigeStelleGefunden) {
                    Einsatz aktueller = (Einsatz) this.getContent();
                    if (pEinsatz.getPPrioritaet() <= aktueller.getPPrioritaet()) {
                        this.next();
                    } else {
                        richtigeStelleGefunden = true;
                    }
                }

                //ist Feld noch nicht belegt, dann anhaengen
                if (!this.hasAccess()) {
                    this.append(pEinsatz);
                } //sonst davor einfuegen
                else {
                    this.insert(pEinsatz);
                }
            }
        }

    }

    public Einsatz gibNaechstenEinsatz() {
        Einsatz ret = null;
        this.toFirst();
        if (this.hasAccess()) {
            ret = (Einsatz) this.getContent();
        }
        return ret;
    }

    public void entferneNaechstenEinsatz() {
        this.toFirst();
        if (this.hasAccess()) {
            this.remove();
        }
    }
}
