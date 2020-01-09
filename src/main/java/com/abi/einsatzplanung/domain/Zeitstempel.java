package com.abi.einsatzplanung.domain;

import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;

@ToString
public class Zeitstempel {
    private LocalDateTime start;
    private LocalDateTime jetzt;
    private int minutenSeit2000;

    public Zeitstempel() {
        this.init(0);
    }

    public Zeitstempel(int minutesBefore) {
        this.init(minutesBefore);
    }

    private void init(int minutesBefore) {
        Long l = new Long(minutesBefore);
        this.start = LocalDateTime.of(2000, 1,1,0,0,0,0);
        this.jetzt = LocalDateTime.now().minusMinutes(l);

        Duration duration = Duration.between(this.start, this.jetzt);
        Long diff = Math.abs(duration.toMinutes());
        this.minutenSeit2000 = diff.intValue();
    }

    public int gibMinuten() {
        return this.minutenSeit2000;
    }
}
