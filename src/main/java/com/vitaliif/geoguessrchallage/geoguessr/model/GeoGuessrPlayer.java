package com.vitaliif.geoguessrchallage.geoguessr.model;

import java.util.List;

public record GeoGuessrPlayer(GeoGuessrTotalScore totalScore,
                              GeoGuessrTotalDistance totalDistance,
                              List<GeoGuessrPointGuess> guesses) {

}
