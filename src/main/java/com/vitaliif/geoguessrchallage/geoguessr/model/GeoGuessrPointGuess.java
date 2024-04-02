package com.vitaliif.geoguessrchallage.geoguessr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GeoGuessrPointGuess(@JsonProperty("lat")
                                  double latitude,
                                  @JsonProperty("lng")
                                  double longitude,
                                  boolean timedOut,
                                  Integer roundScoreInPoints) {
}
