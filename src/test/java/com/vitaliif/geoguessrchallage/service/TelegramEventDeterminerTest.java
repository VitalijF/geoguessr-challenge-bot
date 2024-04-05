package com.vitaliif.geoguessrchallage.service;

import com.vitaliif.geoguessrchallage.telegram.service.TelegramEvent;
import com.vitaliif.geoguessrchallage.telegram.service.TelegramEventDeterminer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TelegramEventDeterminerTest {

    private TelegramEventDeterminer telegramEventDeterminer;


    @BeforeEach
    public void init() {
        telegramEventDeterminer = new TelegramEventDeterminer();
    }
    @Test
    public void worstPoints() {
        TelegramEvent telegramEvent = telegramEventDeterminer.determineEvent("worst points");

        Assertions.assertSame(TelegramEvent.WORST_POINTS, telegramEvent);
    }
}
