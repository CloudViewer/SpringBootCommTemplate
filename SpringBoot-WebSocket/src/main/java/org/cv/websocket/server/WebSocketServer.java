package org.cv.websocket.server;

import org.cv.websocket.entity.Client;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ale on 2020/4/1
 */
@Component
@ServerEndpoint("/WebSocketServer/{uid}")
public class WebSocketServer {

    /**
     * TODO:待完善...
     */

    /**
     * 连接数
     */
    private static long onConnectCount = 0;


    /**
     * 用线程安全的ConcurrentHashMap 来存放客户端信息
     */
    private static ConcurrentHashMap<String, Client> concurrentHashMap = new ConcurrentHashMap<>();


    /**
     * 由WebSocket提供的Session
     */
    private static Session session;

    /**
     * 服务端名称
     */
    private static final String SYS_NICK_NAME = "SYSADMIN";


}
