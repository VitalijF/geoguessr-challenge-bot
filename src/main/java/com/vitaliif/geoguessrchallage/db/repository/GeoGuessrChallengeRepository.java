package com.vitaliif.geoguessrchallage.db.repository;

import com.vitaliif.geoguessrchallage.db.entity.GeoGuessrChallengeEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface GeoGuessrChallengeRepository extends CrudRepository<GeoGuessrChallengeEntity, Long> {

    Optional<GeoGuessrChallengeEntity> findByGeoGuessrId(String geoGuessrID);

    Optional<GeoGuessrChallengeEntity> findFirstByDate(LocalDate date);
}
