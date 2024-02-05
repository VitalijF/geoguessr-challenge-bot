package com.vitaliif.geoguessrchallage.geoguessr.service;

import com.vitaliif.geoguessrchallage.db.entity.GeoGuessrChallengeEntity;
import com.vitaliif.geoguessrchallage.db.entity.GeoGuessrMapType;
import com.vitaliif.geoguessrchallage.db.repository.GeoGuessrChallengeRepository;
import com.vitaliif.geoguessrchallage.geoguessr.model.GeoGuessrChallengeResponse;
import com.vitaliif.geoguessrchallage.geoguessr.model.GeoGuessrResults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GeoGuessrService {
    private final GeoGuessrClient geoGuessrClient;
    private final GeoGuessrChallengeRepository challengeRepository;

    public GeoGuessrService(GeoGuessrClient geoGuessrClient,
                            GeoGuessrChallengeRepository challengeRepository) {
        this.geoGuessrClient = geoGuessrClient;
        this.challengeRepository = challengeRepository;
    }

    public GeoGuessrResults getResults(String gameId) {
        GeoGuessrResults results = geoGuessrClient.getResults(gameId);
        return results;
    }

    public GeoGuessrChallengeResponse startChallenge() {
        final GeoGuessrChallengeResponse challenge = geoGuessrClient.startChallenge();
        final GeoGuessrChallengeEntity challengeEntity = initializeChallengeEntity(challenge.token());

        challengeRepository.save(challengeEntity);

        return challenge;
    }

    private GeoGuessrChallengeEntity initializeChallengeEntity(String id) {
        GeoGuessrChallengeEntity challenge = new GeoGuessrChallengeEntity();
        challenge.setDate(LocalDate.now());

        //TODO: remove hardcode
        challenge.setType(GeoGuessrMapType.LVIV);
        challenge.setGeoGuessrId(id);

        return challenge;
    }
}
