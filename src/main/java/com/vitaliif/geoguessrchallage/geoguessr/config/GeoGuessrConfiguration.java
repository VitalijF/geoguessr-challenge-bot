package com.vitaliif.geoguessrchallage.geoguessr.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GeoGuessrConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }


    @Bean
    public RestTemplate restTemplate(ObjectMapper objectMapper) {
        return new RestTemplateBuilder()
                .messageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }
}
