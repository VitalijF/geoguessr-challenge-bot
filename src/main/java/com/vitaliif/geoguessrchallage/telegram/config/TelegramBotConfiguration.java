package com.vitaliif.geoguessrchallage.telegram.config;

import com.pengrad.telegrambot.TelegramBot;
import com.vitaliif.geoguessrchallage.telegram.service.TelegramFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramBotConfiguration {

    @Bean
    public TelegramBot telegramBot(@Value("${telegram.token}") String token) {
        return new TelegramBot(token);
    }

    @Bean
    public TelegramBotsApi botsApi(ReceiverTelegramBot telegramBot) throws TelegramApiException {
        final TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(telegramBot);
        return botsApi;
    }

}
