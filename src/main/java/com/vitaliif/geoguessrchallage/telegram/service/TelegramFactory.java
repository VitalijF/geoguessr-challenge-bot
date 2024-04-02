package com.vitaliif.geoguessrchallage.telegram.service;

import com.vitaliif.geoguessrchallage.geoguessr.model.GeoGuessrChallengeResponse;
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
            case GET_TODAY_RESULTS -> {
                GeoGuessrResults todayResults = service.getTodayResults();
                yield messageResultFormatter.formatResultsMessage(todayResults);
            }
            case START_CHALLENGE -> {
                String todayChallengeId = service.getTodayChallengeId();
                if (todayChallengeId != null) {
                    yield messageResultFormatter.formatChallengeAlreadyExist(todayChallengeId);
                } else {
                    GeoGuessrChallengeResponse geoGuessrChallengeResponse = service.startChallenge();
                    yield messageResultFormatter.formatResultsMessage(geoGuessrChallengeResponse);
                }
            }
            case INFO -> messageResultFormatter.formatInfoMessage();
            default -> messageResultFormatter.formatUnknownMessage();
        };

        telegramClient.postMessage(formattedMessage);
    }
}
