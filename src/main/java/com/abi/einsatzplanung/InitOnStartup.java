package com.abi.einsatzplanung;

import com.abi.einsatzplanung.domain.Einsatz;
import com.abi.einsatzplanung.service.EinsatzVerwaltung;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.abi.einsatzplanung.service.EinsatzVerwaltung.*;

@Slf4j
@Component
public class InitOnStartup implements CommandLineRunner {

    final EinsatzVerwaltung einsatzVerwaltung;

    public InitOnStartup(EinsatzVerwaltung einsatzVerwaltung) {
        this.einsatzVerwaltung = einsatzVerwaltung;
    }

    @Override
    public void run(String...args) throws Exception {
        log.info("Init Lists");

        einsatzVerwaltung.neuerNotruf("Ruhestörung", "Lange Straße 12", 2, 5);
        einsatzVerwaltung.neuerNotruf("Leichter Verkehrsunfall (nur Blechschaden)", "Steinstraße 2", 1, 1);

        Einsatz einsatz = einsatzVerwaltung.gibNaechstenEinsatz();
        einsatzVerwaltung.uebertrageNaechstenEinsatz();

        einsatzVerwaltung.neuerNotruf("Verkehrsunfall mit Personenschaden", "Bergstraße 55", 3);

        einsatzVerwaltung.uebertrageNaechstenEinsatz();

        log.warn(" ");
        log.warn("#################### wartend #######################");
        wartendeEinsaetze.toFirst();
        while (wartendeEinsaetze.hasAccess()) {
            Einsatz e = (Einsatz) wartendeEinsaetze.getContent();
            log.warn(e.toString());
            wartendeEinsaetze.next();
        }

        log.warn(" ");
        log.warn("#################### aktiv #######################");
        aktiveEinsaetze.toFirst();
        while (aktiveEinsaetze.hasAccess()) {
            Einsatz e = aktiveEinsaetze.getContent();
            log.warn(e.toString());
            aktiveEinsaetze.next();
        }
    }
}
