package com.vitaliif.geoguessrchallage.db.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_challenge")
public class UserChallenge {

    @EmbeddedId
    private UserChallengeId id;

    private Integer totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private GeoGuessrUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("challengeId")
    @JoinColumn(name = "challenge_id")
    private GeoGuessrChallengeEntity challenge;

    public UserChallengeId getId() {
        return id;
    }

    public void setId(UserChallengeId id) {
        this.id = id;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public GeoGuessrUser getUser() {
        return user;
    }

    public void setUser(GeoGuessrUser user) {
        this.user = user;
    }

    public GeoGuessrChallengeEntity getChallenge() {
        return challenge;
    }

    public void setChallenge(GeoGuessrChallengeEntity challenge) {
        this.challenge = challenge;
    }
}