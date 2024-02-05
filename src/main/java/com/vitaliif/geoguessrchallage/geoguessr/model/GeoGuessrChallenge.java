package com.vitaliif.geoguessrchallage.geoguessr.model;

public record GeoGuessrChallenge(String map,
                                 boolean forbidMoving,
                                 boolean forbidRotating,
                                 boolean forbidZooming,
                                 Integer timeLimit,
                                 Integer rounds) {
}
