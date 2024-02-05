package com.vitaliif.geoguessrchallage.geoguessr.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "geoguessr")
@Configuration
public class GeoGuessrProperties {

    private String token;
    private String loginUrl;
    private String highScoresUrl;
    private String challengesUrl;
    private String defaultMapId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getHighScoresUrl() {
        return highScoresUrl;
    }

    public void setHighScoresUrl(String highScoresUrl) {
        this.highScoresUrl = highScoresUrl;
    }

    public String getChallengesUrl() {
        return challengesUrl;
    }

    public void setChallengesUrl(String challengesUrl) {
        this.challengesUrl = challengesUrl;
    }

    public String getDefaultMapId() {
        return defaultMapId;
    }

    public void setDefaultMapId(String defaultMapId) {
        this.defaultMapId = defaultMapId;
    }
}
