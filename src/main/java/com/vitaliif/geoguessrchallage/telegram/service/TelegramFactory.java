package com.vitaliif.geoguessrchallage.telegram.service;

import com.vitaliif.geoguessrchallage.geoguessr.model.GeoGuessrResults;
import com.vitaliif.geoguessrchallage.geoguessr.service.GeoGuessrService;
import org.springframework.stereotype.Component;

@Component
public class TelegramFactory {

    private final TelegramClient telegramClient;
    private final TelegramEventDeterminer eventDeterminer;
    private final MessageResultFormatter messageResultFormatter;

    private final GeoGuessrService service;

    public TelegramFactory(TelegramClient telegramClient,
                           TelegramEventDeterminer eventDeterminer,
                           MessageResultFormatter messageResultFormatter, GeoGuessrService service) {
        this.telegramClient = telegramClient;
        this.eventDeterminer = eventDeterminer;
        this.messageResultFormatter = messageResultFormatter;
        this.service = service;
    }


    public void processEvent(String message) {
        TelegramEvent telegramEvent = eventDeterminer.determineEvent(message);
        String formattedMessage = switch (telegramEvent) {
            case UNKNOWN -> messageResultFormatter.formatUnknownMessage();
            case GET_TODAY_RESULTS -> {
                GeoGuessrResults todayResults = service.getTodayResults();
                yield messageResultFormatter.formatResultsMessage(todayResults);
            }
            default -> messageResultFormatter.formatUnknownMessage();
        };

        telegramClient.postMessage(formattedMessage);
    }
}
