package com.vitaliif.geoguessrchallage.db.repository;

import com.vitaliif.geoguessrchallage.db.entity.UserChallenge;
import com.vitaliif.geoguessrchallage.db.entity.UserChallengeId;
import org.springframework.data.repository.CrudRepository;

public interface UserChallengeRepository extends CrudRepository<UserChallenge, UserChallengeId> {
}
