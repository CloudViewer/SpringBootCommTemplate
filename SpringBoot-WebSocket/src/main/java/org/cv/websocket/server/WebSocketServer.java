package org.cv.websocket.server;

import org.cv.websocket.entity.Client;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ale on 2020/4/1
 */
@Component
@ServerEndpoint(value = "/ws/asset")
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


//    @PostConstruct
//    public void init(){
//        System.out.println("初始化");
//    }

    @OnOpen
    public void onOpen(Session session){
        System.out.println(session.getRequestURI());
        this.session = session;
        System.out.println("有新连接进入...");
    }

//    @OnError
//    public void onError() throws Throwable{
//        System.out.println("握手失败");
//    }


//    public void sendMessage(String message) throws IOException {
//        this.session.getBasicRemote().sendText(message);
//        // this.session.getAsyncRemote().sendText(message);
//    }


}
