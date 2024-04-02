package com.vitaliif.geoguessrchallage.geoguessr.service;

import com.vitaliif.geoguessrchallage.geoguessr.config.GeoGuessrProperties;
import com.vitaliif.geoguessrchallage.geoguessr.model.GeoGuessrChallenge;
import com.vitaliif.geoguessrchallage.geoguessr.model.GeoGuessrChallengeResponse;
import com.vitaliif.geoguessrchallage.geoguessr.model.GeoGuessrResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class GeoGuessrClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoGuessrService.class);

    private final RestTemplate restTemplate;
    private final GeoGuessrProperties properties;


    public GeoGuessrClient(RestTemplate restTemplate,
                           GeoGuessrProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }
    public GeoGuessrResults getResults(String gameId) {
        final ResponseEntity<GeoGuessrResults> response;

        try {
            response = restTemplate.exchange(
                    properties.getHighScoresUrl() + "/" + gameId,
                    HttpMethod.GET,
                    new HttpEntity<>(buildAuthorizationToken()),
                    GeoGuessrResults.class
            );
        } catch (Exception e) {
            LOGGER.error("Error happens for id = " + gameId );
            throw e;
        }

        return response.getBody();
    }

    public GeoGuessrChallengeResponse startChallenge() {
        final ResponseEntity<GeoGuessrChallengeResponse> response = restTemplate.exchange(
                properties.getChallengesUrl(),
                HttpMethod.POST,
                new HttpEntity<>(buildGeoGuessrChallengeInput(), buildAuthorizationToken()),
                GeoGuessrChallengeResponse.class
        );

        return response.getBody();
    }

    private GeoGuessrChallenge buildGeoGuessrChallengeInput() {
        return new GeoGuessrChallenge(properties.getDefaultMapId(), true, false, false, 60, 5);
    }

    private MultiValueMap<String, String> buildAuthorizationToken() {
        final String token = properties.getToken();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Cookie", "_ncfa=" + token);

        return headers;
    }

    private String extractNcfaToken(ResponseEntity<Object> response) {
        HttpHeaders headers = response.getHeaders();

        //TODO: FIx in in normal way
        String cookie = headers.get("Set-Cookie").get(1);
        String[] parts = cookie.split("=", 2);

        if (parts.length > 1) {
            // Further splitting to get the desired part before ';'
            String[] valueParts = parts[1].split(";", 2);
             return valueParts[0];
        } else {
           throw new IllegalStateException("No cookies");
        }
    }
}
