package com.vitaliif.geoguessrchallage.telegram.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.vitaliif.geoguessrchallage.telegram.config.TelegramConfigProperties;
import org.springframework.stereotype.Service;

import static com.pengrad.telegrambot.model.request.ParseMode.HTML;
import static com.pengrad.telegrambot.model.request.ParseMode.MarkdownV2;

@Service
public class TelegramClient {

    private final TelegramBot telegramBot;
    private final TelegramConfigProperties properties;

    public TelegramClient(TelegramBot telegramBot,
                          TelegramConfigProperties properties) {
        this.telegramBot = telegramBot;
        this.properties = properties;
    }

    public boolean postMessage(String message) {
        SendMessage sendMessage = new SendMessage(properties.getChatId(), message).parseMode(HTML);
        final SendResponse response = telegramBot.execute(sendMessage);
        return response.isOk();

    }
}
