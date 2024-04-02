package com.vitaliif.geoguessrchallage.db.repository;

import com.vitaliif.geoguessrchallage.db.entity.GeoGuessrUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GeoGuessrUserRepository extends CrudRepository<GeoGuessrUser, Long> {
    Optional<GeoGuessrUser> findByGeoGuessrId(String geoGuessrId);
}
