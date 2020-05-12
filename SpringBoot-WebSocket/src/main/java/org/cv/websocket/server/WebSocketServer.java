package org.cv.websocket.server;

import org.cv.websocket.entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ale on 2020/4/1
 */
@Component
@ServerEndpoint(value = "/ws/asset")
public class WebSocketServer {

    static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

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
    private  static Session session;

    /**
     * 服务端名称
     */
    private static final String SYS_NICK_NAME = "SYSADMIN";


    @OnOpen
    public static void onOpen(Session s) throws Throwable {
        logger.info("有新连接进入...");

        session = s;


        onConnectCount++;
        // 连接成功之后要进行数据返回 也就是说前端弹幕既然要渲染那么后端就要返回数据给前端初始化
        // 数据从哪里读取,1 数据库,2 Redis
        sendMessage("服务端发送消息");


         // 当前连接人数

    }

    /**
     * 连接错误的
     * @param throwable
     */
    @OnError
    public void onError(Throwable throwable){
        logger.info("异常.."+throwable);
    }

    /**
     * 接收前端发送的消息
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message,Session session){
        logger.info(message);

    }


    /**
     * 主动向客户端发送消息
     * @param message
     * @throws Throwable
     */
    public static void sendMessage(String message) throws Throwable{
        session.getBasicRemote().sendText(message);
    }

    /**
     * 关连接关闭
     */
    @OnClose
    
    public void onClose(){
        onConnectCount--;
        logger.info("有连接退出,当前在线人数："+onConnectCount);
    }


}
