package com.vitaliif.geoguessrchallage.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "user", schema = "public")
public class GeoGuessrUser extends AbstractEntity {

    private String geoGuessrId;

    private String geoGuessrName;

    @ManyToMany
    @JoinTable(
            name = "user_challenge",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "challenge_id")
    )
    private List<GeoGuessrChallengeEntity> challenges;

    @OneToMany(mappedBy = "user")
    private List<GeoGuessrChallengeGuessEntity> guesses;

    public String getGeoGuessrId() {
        return geoGuessrId;
    }

    public void setGeoGuessrId(String geoGuessrId) {
        this.geoGuessrId = geoGuessrId;
    }

    public String getGeoGuessrName() {
        return geoGuessrName;
    }

    public void setGeoGuessrName(String geoGuessrName) {
        this.geoGuessrName = geoGuessrName;
    }

    public List<GeoGuessrChallengeEntity> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<GeoGuessrChallengeEntity> challenges) {
        this.challenges = challenges;
    }

    public List<GeoGuessrChallengeGuessEntity> getGuesses() {
        return guesses;
    }

    public void setGuesses(List<GeoGuessrChallengeGuessEntity> guesses) {
        this.guesses = guesses;
    }
}
