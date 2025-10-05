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

/**
 * Represents a WebSocket direct message server for handling real-time communication
 * between users. Each user connects to the server using their unique username.
 *
 * This class is annotated with Spring's `@ServerEndpoint` and `@Component`
 * annotations, making it a WebSocket endpoint that can handle WebSocket
 * connections at the "/chat/{username}" endpoint.
 *
 * Example URL: ws://localhost:8080/chat/username
 *
 * The server provides functionality for users to send direct messages to other users.
 */
@ServerEndpoint("/directmessage/{username}")
@Component
public class DirectMessageServer 
{
    // Store all socket session and their corresponding username
    // Two maps for the ease of retrieval by key
    private static Map < Session, String > sessionUsernameMap = new Hashtable < > ();
    private static Map < String, Session > usernameSessionMap = new Hashtable < > ();

    // server side logger
    private final Logger logger = LoggerFactory.getLogger(DirectMessageServer.class);
    
    /** 
     * @param session
     * @param message
     * @throws IOException
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException 
    {
        // Handle new messages
        logger.info("[onMessage] " + message);

        if(message.startsWith("@")) 
        {
            // Direct message to a user using the format "@username <message>"
            String sender = message.split(" ")[0].substring(1);
            sendSpecificUserMsg(sender, "[DM] " + sessionUsernameMap.get(session) + ": " + message);
            sendSpecificUserMsg(sessionUsernameMap.get(session), "[DM] " + sessionUsernameMap.get(session) + ": " + message);

        } 
        else 
        {
            session.getBasicRemote().sendText("[!] Direct messages should start with '@username'");

        }

    }
    @OnClose
    public void onClose(Session session) throws IOException {
        // Remove session and corresponding username
        logger.info("[onClose] " + sessionUsernameMap.get(session));
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
    }
    @OnError
    public void onError(Session session, Throwable throwable) 
    {
        // Do error handling here
        logger.info("[onError] " + sessionUsernameMap.get(session));

    }
    private void sendSpecificUserMsg(String username, String message)
    {
        try 
        {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);

        } 
        catch (IOException e)
        {
            logger.info("[DM Exception] " + e.getMessage());
            e.printStackTrace();

        }

    }
    
}
