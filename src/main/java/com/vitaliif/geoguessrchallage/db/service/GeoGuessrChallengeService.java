package com.vitaliif.geoguessrchallage.db.service;

import com.vitaliif.geoguessrchallage.db.entity.GeoGuessrChallengeEntity;
import com.vitaliif.geoguessrchallage.db.entity.GeoGuessrUser;
import com.vitaliif.geoguessrchallage.db.entity.UserChallenge;
import com.vitaliif.geoguessrchallage.db.entity.UserChallengeId;
import com.vitaliif.geoguessrchallage.db.repository.GeoGuessrChallengeRepository;
import com.vitaliif.geoguessrchallage.db.repository.GeoGuessrUserRepository;
import com.vitaliif.geoguessrchallage.db.repository.UserChallengeRepository;
import com.vitaliif.geoguessrchallage.geoguessr.model.GeoGuessrResultItem;
import com.vitaliif.geoguessrchallage.geoguessr.model.GeoGuessrResults;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeoGuessrChallengeService {

    private final GeoGuessrUserRepository userRepository;
    private final GeoGuessrChallengeRepository challengeRepository;

    private final UserChallengeRepository userChallengeRepository;

    public GeoGuessrChallengeService(GeoGuessrUserRepository userRepository,
                                     GeoGuessrChallengeRepository challengeRepository, UserChallengeRepository userChallengeRepository) {
        this.userRepository = userRepository;
        this.challengeRepository = challengeRepository;
        this.userChallengeRepository = userChallengeRepository;
    }

    @Transactional
    public void storeResultsData(GeoGuessrResults results, String gameId) {
        Optional<GeoGuessrChallengeEntity> challengeEntityOptional = challengeRepository.findByGeoGuessrId(gameId);
        if (challengeEntityOptional.isEmpty()) {
            // TODO: Fix in normal way
            throw new RuntimeException("No saved challenge");
        }
        GeoGuessrChallengeEntity entity = challengeEntityOptional.get();
        List<GeoGuessrResultItem> items = results.items();
        for (GeoGuessrResultItem item: items) {
            processItem(item, entity);
        }

    }

    private void processItem(GeoGuessrResultItem item, GeoGuessrChallengeEntity entity) {
        Optional<GeoGuessrUser> byGeoGuessrId = userRepository.findByGeoGuessrId(item.userId());

        GeoGuessrUser geoGuessrUser;
        if (byGeoGuessrId.isEmpty()) {
            geoGuessrUser = createUserToSave(item);
            userRepository.save(geoGuessrUser);
        } else {
            geoGuessrUser = byGeoGuessrId.get();
        }

        attachUserIntoChallenge(item, geoGuessrUser, entity);


    }

    private void attachUserIntoChallenge(GeoGuessrResultItem item, GeoGuessrUser geoGuessrUser, GeoGuessrChallengeEntity challengeEntity) {
        UserChallengeId userChallengeId = new UserChallengeId();
        userChallengeId.setChallengeId(challengeEntity.getId());
        userChallengeId.setUserId(geoGuessrUser.getId());

        Integer totalScore = item.totalScore();

        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setChallenge(challengeEntity);
        userChallenge.setId(userChallengeId);
        userChallenge.setUser(geoGuessrUser);
        userChallenge.setTotalAmount(totalScore);

        userChallengeRepository.save(userChallenge);
    }

    private GeoGuessrUser createUserToSave(GeoGuessrResultItem item) {
        GeoGuessrUser geoGuessrUser = new GeoGuessrUser();
        geoGuessrUser.setGeoGuessrId(item.userId());
        geoGuessrUser.setGeoGuessrName(item.playerName());
        return geoGuessrUser;
    }

}
