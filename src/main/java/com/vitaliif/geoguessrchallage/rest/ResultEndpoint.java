package com.vitaliif.geoguessrchallage.rest;

import com.vitaliif.geoguessrchallage.db.service.GeoGuessrChallengeService;
import com.vitaliif.geoguessrchallage.geoguessr.dto.PointResult;
import com.vitaliif.geoguessrchallage.geoguessr.model.GeoGuessrChallengeResponse;
import com.vitaliif.geoguessrchallage.geoguessr.model.GeoGuessrResults;
import com.vitaliif.geoguessrchallage.geoguessr.service.GeoGuessrService;
import com.vitaliif.geoguessrchallage.telegram.service.MessageResultFormatter;
import com.vitaliif.geoguessrchallage.telegram.service.TelegramClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ResultEndpoint {

    private final TelegramClient telegramClient;
    private final MessageResultFormatter messageResultFormatter;

    private final GeoGuessrChallengeService challengeService;
    private final GeoGuessrService geoGuessrService;

    public ResultEndpoint(TelegramClient telegramClient,
                          MessageResultFormatter messageResultFormatter,
                          GeoGuessrChallengeService challengeService,
                          GeoGuessrService geoGuessrService) {
        this.telegramClient = telegramClient;
        this.messageResultFormatter = messageResultFormatter;
        this.challengeService = challengeService;
        this.geoGuessrService = geoGuessrService;
    }

    @GetMapping("/just-results")
    public GeoGuessrResults getResults(@RequestParam("gameId") String gameId) {
        GeoGuessrResults results = geoGuessrService.getResults(gameId);
        challengeService.storeResultsData(results, gameId);

        return results;
    }

    @GetMapping("/points/worst")
    public String postMessageAboutWorstPoints(@RequestParam("userId") String userId,
                                              @RequestParam("name") String userName,
                                              @RequestParam("number") Integer numberOfPoints) {

        final List<PointResult> worstPoints = geoGuessrService.getWorstPoints(userId, numberOfPoints);

        String telegramMessage = messageResultFormatter.formatWorstPoints(worstPoints, userName);
        telegramClient.postMessage(telegramMessage);
        return "OK";
    }

    @GetMapping("/results")
    public String postMessage(@RequestParam("gameId") String gameId) {
        final GeoGuessrResults results = geoGuessrService.getResults(gameId);
        challengeService.storeResultsData(results, gameId);

        String telegramMessage = messageResultFormatter.formatResultsMessage(results);

        telegramClient.postMessage(telegramMessage);

        return "OK";
    }

    @GetMapping("/challenge")
    public String startChallenge() {
        GeoGuessrChallengeResponse geoGuessrChallengeResponse = geoGuessrService.startChallenge();
        String message = messageResultFormatter.formatResultsMessage(geoGuessrChallengeResponse);

        telegramClient.postMessage(message);

        return geoGuessrChallengeResponse.token();
    }
}
