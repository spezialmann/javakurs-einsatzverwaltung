package com.abi.einsatzplanung.controller;

import com.abi.einsatzplanung.domain.Einsatz;
import com.abi.einsatzplanung.service.EinsatzVerwaltung;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
public class EinsatzController {

    private EinsatzVerwaltung einsatzVerwaltung;

    public EinsatzController(EinsatzVerwaltung einsatzVerwaltung) {
        this.einsatzVerwaltung = einsatzVerwaltung;
        this.einsatzVerwaltung.neuerNotruf("Ruhestörung", "Lange Straße 12", 2, 5);
        this.einsatzVerwaltung.neuerNotruf("Leichter Verkehrsunfall (nur Blechschaden)", "Steinstraße 2", 1, 2);
//        Einsatz einsatz = einsatzVerwaltung.gibNaechstenEinsatz();
//        this.einsatzVerwaltung.uebertrageNaechstenEinsatz();
        this.einsatzVerwaltung.neuerNotruf("Verkehrsunfall mit Personenschaden", "Bergstraße 55", 3, 1);
        this.einsatzVerwaltung.neuerNotruf("Häusliche Gewalt", "Musterweg 123", 3);

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
    
    @GetMapping("/api/v1/einsaetze/notruf")
    public RedirectView neuerNotruf(@RequestParam(name = "beschreibung") String beschreibung, 
                                    @RequestParam(name = "ort") String ort, 
                                    @RequestParam(name = "prio") int prio) {
        this.einsatzVerwaltung.neuerNotruf(beschreibung, ort, prio);
        log.info("Neuer Einsatz: " + beschreibung);
        return new RedirectView("/");
    }
}
