package com.vitaliif.geoguessrchallage.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "challenge_point_guess", schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "challenge_point_id",
                        "user_id",
                        "challenge_order"
                })
        })
public class GeoGuessrChallengeGuessEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "challenge_point_id")
    private GeoGuessrChallengePointEntity challengePoint;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private GeoGuessrUser user;

    @Column(nullable = false, name = "challenge_order")
    private Integer order;

    @Column(nullable = false)
    private Integer points;

    public GeoGuessrChallengePointEntity getChallengePoint() {
        return challengePoint;
    }

    public void setChallengePoint(GeoGuessrChallengePointEntity challengePoint) {
        this.challengePoint = challengePoint;
    }

    public GeoGuessrUser getUser() {
        return user;
    }

    public void setUser(GeoGuessrUser user) {
        this.user = user;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
