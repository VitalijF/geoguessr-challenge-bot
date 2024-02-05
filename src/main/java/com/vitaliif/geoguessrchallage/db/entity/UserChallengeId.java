package com.vitaliif.geoguessrchallage.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserChallengeId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "challenge_id")
    private Long challengeId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChallengeId that = (UserChallengeId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(challengeId, that.challengeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, challengeId);
    }
}
