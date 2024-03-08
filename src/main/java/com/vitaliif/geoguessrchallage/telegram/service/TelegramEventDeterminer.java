package com.vitaliif.geoguessrchallage.telegram.service;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TelegramEventDeterminer {

    private static final Map<String, TelegramEvent> eventsByTemplate = Map.of(
            "start challenge", TelegramEvent.START_CHALLENGE,
            "запускай шарманку",  TelegramEvent.START_CHALLENGE,
            "results", TelegramEvent.GET_TODAY_RESULTS,
            "хто роз'їбав?", TelegramEvent.GET_TODAY_RESULTS
    );

    public TelegramEvent determineEvent(String message) {
        return eventsByTemplate.getOrDefault(message, TelegramEvent.UNKNOWN);
    }
}
