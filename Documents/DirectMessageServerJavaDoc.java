package db.Websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@ServerEndpoint("/directmessage/{username}")
@Component
public class DirectMessageServer {

    private final Logger logger = LoggerFactory.getLogger(DirectMessageServer.class);

    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    /**
     * Handles incoming messages from clients, intended for direct messaging.
     * 
     * @param session The WebSocket session from which the message originated.
     * @param message The direct message sent by the client.
     * @throws IOException If an input or output exception occurs.
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        // Implementation omitted for brevity.
    }

    /**
     * Handles the event when a WebSocket session is closed.
     * 
     * @param session The WebSocket session that was closed.
     * @throws IOException If an input or output exception occurs.
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        // Implementation omitted for brevity.
    }

    /**
     * Handles errors that occur during WebSocket communication.
     * 
     * @param session   The WebSocket session in which the error occurred.
     * @param throwable The error that occurred.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        // Implementation
