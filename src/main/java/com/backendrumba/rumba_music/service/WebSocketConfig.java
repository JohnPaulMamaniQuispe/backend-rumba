package com.backendrumba.rumba_music.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Prefijo para los destinos de salida
        config.setApplicationDestinationPrefixes("/app"); // Prefijo para las rutas de mensajes entrantes
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // Este es el endpoint WebSocket al que se conecta el cliente
                .setAllowedOrigins("*") // Permite todas las solicitudes cross-origin
                .withSockJS(); // Activa soporte para SockJS si WebSocket no es compatible
    }
}
