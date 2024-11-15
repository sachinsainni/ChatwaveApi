package com.sachin.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sachin.bean.ChatMessage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MessageHandler extends TextWebSocketHandler {


    private final Set<WebSocketSession> sessions = new HashSet<>();

    // Triggered when a new client connects
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New connection: " + session.getId());
        // sessionWithName.put(session.getId(), "user-" + Math.random());

    }

    // Handle incoming messages from a client
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String receivedMessage = message.getPayload();
        System.out.println("Received message: " + receivedMessage);

        // Parse the received message as JSON
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessage chatMessage = objectMapper.readValue(receivedMessage, ChatMessage.class);  // Deserialize JSON to ChatMessage object

        // Broadcast the message to other clients
        String jsonMessage = objectMapper.writeValueAsString(chatMessage); // Convert ChatMessage object to JSON

        // Broadcast the JSON message to all other connected clients
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen() && !webSocketSession.getId().equals(session.getId())) {
                webSocketSession.sendMessage(new TextMessage(jsonMessage));
            }
        }
    }


    // Triggered when a connection is closed
    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status)
            throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }

    private void sendSessionList(WebSocketSession session) throws Exception {
        // Convert the session IDs to a comma-separated string or JSON array
        String sessionList = sessions.stream()
                .map(WebSocketSession::getId)
                .collect(Collectors.joining(","));

        // Send the session list to the client
        session.sendMessage(new TextMessage("Session List: " + sessionList));
    }
}
