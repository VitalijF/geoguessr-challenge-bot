package com.vitaliif.geoguessrchallage.telegram.service;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TelegramEventDeterminer {

    private static final Map<String, TelegramEvent> EVENTS_BY_TEMPLATE = Map.of(
            "start challenge", TelegramEvent.START_CHALLENGE,
            "запускай шарманку",  TelegramEvent.START_CHALLENGE,
            "results", TelegramEvent.GET_TODAY_RESULTS,
            "хто роз'їбав?", TelegramEvent.GET_TODAY_RESULTS,
            "info", TelegramEvent.INFO,
            "worst points", TelegramEvent.WORST_POINTS
    );

    public TelegramEvent determineEvent(String message) {
        return EVENTS_BY_TEMPLATE.getOrDefault(message, TelegramEvent.UNKNOWN);
    }
}
