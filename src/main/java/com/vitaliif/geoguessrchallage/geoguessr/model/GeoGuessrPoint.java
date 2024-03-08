package com.vitaliif.geoguessrchallage.geoguessr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GeoGuessrPoint(@JsonProperty("lat")
                             double latitude,
                             @JsonProperty("lng")
                             double longitude) {
}
