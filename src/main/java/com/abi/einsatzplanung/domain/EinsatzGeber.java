package com.abi.einsatzplanung.domain;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class EinsatzGeber<ContentType> extends List {


    public void fuegeEin(Einsatz pEinsatz) {
        if(pEinsatz!=null) {
            if(this.isEmpty()) {
                this.append(pEinsatz);
            }
            else {
                this.toFirst();
                Einsatz current = (Einsatz)this.getContent();
                while (this.hasAccess() && (pEinsatz.getPPrioritaet() <= current.getPPrioritaet()) ) {
                    this.next();
                }
                if(this.hasAccess()) {
                    this.insert(pEinsatz);
                }
                else {
                    this.append(pEinsatz);
                }
            }
        }
    }

    public Einsatz gibNaechstenEinsatz() {
        Einsatz ret = null;
        this.toFirst();
        if(this.hasAccess()) {
            ret = (Einsatz)this.getContent();
        }
        return ret;
    }

    public void entferneNaechstenEinsatz() {
        this.toFirst();
        if(this.hasAccess()) {
            this.remove();
        }
    }
}