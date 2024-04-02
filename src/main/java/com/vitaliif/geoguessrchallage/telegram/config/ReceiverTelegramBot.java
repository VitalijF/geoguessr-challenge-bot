package com.vitaliif.geoguessrchallage.telegram.config;

import com.vitaliif.geoguessrchallage.telegram.service.TelegramFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Chat;
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
        if (update.hasMessage() && update.getMessage().getChat() != null) {
            Chat chat = update.getMessage().getChat();
            if (chat.getId().equals(properties.getChatId()) && update.getMessage().getText() != null &&
                    update.getMessage().getText().startsWith("@" + properties.getBotName())) {
                String message = update.getMessage().getText().replace("@" + properties.getBotName() + " ", "").toLowerCase();
                factory.processEvent(message);
            }
        } else if (update.hasChannelPost() && update.getChannelPost().getText() != null && update.getChannelPost().getText().startsWith("@" + properties.getBotName())) {
            String message = update.getChannelPost().getText().replace("@" + properties.getBotName() + " ", "").toLowerCase();
            factory.processEvent(message);
        }
    }
}
