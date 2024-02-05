package com.vitaliif.geoguessrchallage.geoguessr.model;

public record GeoGuessrResultItem(String userId,
                                  String playerName,
                                  Integer totalScore,
                                  GeoGuessrGame game) {
}
