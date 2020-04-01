package org.cv.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Created by Ale on 2020/4/1
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter exporter(){
        return new ServerEndpointExporter();
    }
}
