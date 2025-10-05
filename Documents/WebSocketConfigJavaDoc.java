package db.Websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Configuration class that defines beans for WebSocket setup.
 * This class registers server endpoints, enabling the handling of WebSocket
 * connections. In this case, it facilitates the registration of the "/chat" 
 * endpoint handler with Spring, so that WebSocket requests to "ws://" URLs 
 * are processed correctly.
 */
@Configuration
public class WebSocketConfig {

    /**
     * Declares the ServerEndpointExporter bean. The ServerEndpointExporter is a 
     * Spring-provided server application configurer that, when declared as a bean, 
     * automatically registers beans annotated with @ServerEndpoint.
     * This enables WebSocket endpoints to be managed by Spring and allows them to
     * leverage Spring's programming model.
     *
     * @return the ServerEndpointExporter that will scan beans to register WebSocket endpoints.
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    
}
