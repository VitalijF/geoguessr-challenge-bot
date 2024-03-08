package com.vitaliif.geoguessrchallage.geoguessr.model;

import java.util.List;

public record GeoGuessrGame(GeoGuessrPlayer player,
                            List<GeoGuessrPoint> rounds) {
}
