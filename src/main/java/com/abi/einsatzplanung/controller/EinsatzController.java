package com.abi.einsatzplanung.controller;

import com.abi.einsatzplanung.domain.Einsatz;
import com.abi.einsatzplanung.service.EinsatzVerwaltung;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class EinsatzController {
    private EinsatzVerwaltung einsatzVerwaltung;

    public EinsatzController(EinsatzVerwaltung einsatzVerwaltung) {
        this.einsatzVerwaltung = einsatzVerwaltung;
        this.einsatzVerwaltung.neuerNotruf("Ruhestörung", "Lange Straße 12", 2, 5);
        this.einsatzVerwaltung.neuerNotruf("Leichter Verkehrsunfall (nur Blechschaden)", "Steinstraße 2", 1, 1);
        Einsatz einsatz = einsatzVerwaltung.gibNaechstenEinsatz();
        this.einsatzVerwaltung.uebertrageNaechstenEinsatz();
        this.einsatzVerwaltung.neuerNotruf("Verkehrsunfall mit Personenschaden", "Bergstraße 55", 3);


    }

    @GetMapping(value = "/api/v1/einsaetze/wartend")
    public List<Einsatz> getWartendeEinsaetze() {
        List<Einsatz> ret = new ArrayList<>();

        this.einsatzVerwaltung.wartendeEinsaetze.toFirst();
        while (EinsatzVerwaltung.wartendeEinsaetze.hasAccess()) {
            ret.add((Einsatz) EinsatzVerwaltung.wartendeEinsaetze.getContent());
            einsatzVerwaltung.wartendeEinsaetze.next();
        }
        return ret;
    }
}
