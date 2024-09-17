package com.backendrumba.rumba_music.controller;

import com.backendrumba.rumba_music.model.PlayMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/play/{roomCode}")
    @SendTo("/topic/room/{roomCode}")
    public PlayMessage sendPlayMessage(PlayMessage message, @DestinationVariable String roomCode) {
        // Este mensaje se envía a todos los clientes suscritos a /topic/room/{roomCode}
        System.out.println("Mensaje recibido en la sala " + roomCode);
        System.out.println("Enviando mensaje: " + message);
        return message;
    }
}
