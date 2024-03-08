package com.vitaliif.geoguessrchallage.telegram.config;

import com.vitaliif.geoguessrchallage.telegram.service.TelegramFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ReceiverTelegramBot extends TelegramLongPollingBot {

    private final TelegramConfigProperties properties;
    private final TelegramFactory factory;

    public ReceiverTelegramBot(TelegramConfigProperties properties, TelegramFactory factory) {
        super(properties.getToken());
        this.properties = properties;
        this.factory = factory;
    }

    @Override
    public String getBotUsername() {
        return properties.getBotName();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasChannelPost() && update.getChannelPost().getText() != null && update.getChannelPost().getText().startsWith("@" + properties.getBotName())) {
            String message = update.getChannelPost().getText().replace("@" + properties.getBotName() + " ", "").toLowerCase();
            factory.processEvent(message);
        }
    }
}
