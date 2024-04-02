package com.vitaliif.geoguessrchallage.geoguessr.service;

import com.vitaliif.geoguessrchallage.db.entity.GeoGuessrChallengeEntity;
import com.vitaliif.geoguessrchallage.db.entity.GeoGuessrChallengeGuessEntity;
import com.vitaliif.geoguessrchallage.db.entity.GeoGuessrChallengePointEntity;
import com.vitaliif.geoguessrchallage.db.entity.GeoGuessrMapType;
import com.vitaliif.geoguessrchallage.db.repository.GeoGuessrChallengeRepository;
import com.vitaliif.geoguessrchallage.db.service.GeoGuessrChallengeService;
import com.vitaliif.geoguessrchallage.geoguessr.dto.PointResult;
import com.vitaliif.geoguessrchallage.geoguessr.model.GeoGuessrChallengeResponse;
import com.vitaliif.geoguessrchallage.geoguessr.model.GeoGuessrResults;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class GeoGuessrService {

    private final GeoGuessrClient geoGuessrClient;
    private final GeoGuessrChallengeRepository challengeRepository;
    private final GeoGuessrChallengeService challengeService;

    public GeoGuessrService(GeoGuessrClient geoGuessrClient,
                            GeoGuessrChallengeRepository challengeRepository,
                            GeoGuessrChallengeService challengeService) {
        this.geoGuessrClient = geoGuessrClient;
        this.challengeRepository = challengeRepository;
        this.challengeService = challengeService;
    }

    public GeoGuessrResults getResults(String gameId) {
        return geoGuessrClient.getResults(gameId);
    }

    @Transactional
    public GeoGuessrResults getTodayResults() {
        LocalDate now = LocalDate.now();
        Optional<GeoGuessrChallengeEntity> firstByDate = challengeRepository.findFirstByDate(now);
        if (firstByDate.isEmpty()) {
            return null;
        } else {
            GeoGuessrChallengeEntity entity = firstByDate.get();
            GeoGuessrResults results = geoGuessrClient.getResults(entity.getGeoGuessrId());
            if (entity.getPoints().isEmpty()) {
                challengeService.storeResultsData(results, entity.getGeoGuessrId());
            }
            return results;
        }
     }

    @Transactional
    public boolean isTodayChallengeAlreadyPresent() {
        return challengeRepository.findFirstByDate(LocalDate.now()).isPresent();
    }

    @Transactional
    public String getTodayChallengeId() {
        return challengeRepository.findFirstByDate(LocalDate.now()).map(GeoGuessrChallengeEntity::getGeoGuessrId).orElse(null);
    }


    @Transactional
    public List<PointResult> getWorstPoints(String userId, Integer numberOfRecords) {
        return StreamSupport.stream(challengeRepository.findAll().spliterator(), false)
                .map(GeoGuessrChallengeEntity::getPoints)
                .flatMap(Collection::stream)
                .map(GeoGuessrChallengePointEntity::getGuesses)
                .flatMap(Collection::stream)
                .filter(f -> f.getUser().getGeoGuessrId().equals(userId))
                .sorted(Comparator.comparingInt(GeoGuessrChallengeGuessEntity::getPoints))
                .limit(numberOfRecords)
                .map(f -> new PointResult(f.getChallengePoint().getLatitude(), f.getChallengePoint().getLongitude(), f.getPoints()))
                .toList();

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
