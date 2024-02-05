package com.vitaliif.geoguessrchallage.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "challenge")
public class GeoGuessrChallengeEntity extends AbstractEntity {

    private LocalDate date;

    private String geoGuessrId;
    @Enumerated(EnumType.STRING)
    private GeoGuessrMapType type;

    @ManyToMany(mappedBy = "challenges")
    private List<GeoGuessrUser> users;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getGeoGuessrId() {
        return geoGuessrId;
    }

    public void setGeoGuessrId(String geoGuessrId) {
        this.geoGuessrId = geoGuessrId;
    }

    public GeoGuessrMapType getType() {
        return type;
    }

    public void setType(GeoGuessrMapType type) {
        this.type = type;
    }

    public List<GeoGuessrUser> getUsers() {
        return users;
    }

    public void setUsers(List<GeoGuessrUser> users) {
        this.users = users;
    }


}
