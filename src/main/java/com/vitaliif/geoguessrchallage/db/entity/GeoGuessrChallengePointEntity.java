package com.vitaliif.geoguessrchallage.db.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "challenge_point", schema = "public",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "latitude",
                "longitude",
                "challenge_id"
        })
})
public class GeoGuessrChallengePointEntity  extends AbstractEntity {
    @Column(nullable = false)
    private Double latitude;
    @Column(nullable = false)
    private Double longitude;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "challenge_id")
    private GeoGuessrChallengeEntity challenge;

    @Column(nullable = false, name = "challenge_order")
    private Integer order;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "challengePoint")
    private List<GeoGuessrChallengeGuessEntity> guesses = new ArrayList<>();

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public GeoGuessrChallengeEntity getChallenge() {
        return challenge;
    }

    public void setChallenge(GeoGuessrChallengeEntity challenge) {
        this.challenge = challenge;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<GeoGuessrChallengeGuessEntity> getGuesses() {
        return guesses;
    }

    public void setGuesses(List<GeoGuessrChallengeGuessEntity> guesses) {
        this.guesses = guesses;
    }
}
