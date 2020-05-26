package wkai.test.game.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wkai.test.game.entity.ChatRecord;
import wkai.test.game.service.ChatRecordService;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/chat/{userId}")
public class WebSocket {
    /**
     * 在线人数
     */
    public static int onlineNumber = 0;
    /**
     * 以userId为key，WebSocket为对象保存起来
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<String, Session>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ChatRecordService chatRecordService;
    /**
     * 会话
     */
    private Session session;
    /**
     * 用户名称
     */
    private String userId;

    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        onlineNumber++;
        logger.info("现在来连接的客户id：" + session.getId() + "用户Id：" + userId);
        this.userId = userId;
        this.session = session;
        logger.info("有新连接加入！ 当前在线人数" + onlineNumber);
        clients.put(userId, session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("服务端发生了错误" + error.getMessage());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        onlineNumber--;
        clients.remove(userId);
        logger.info("有连接关闭！ 当前在线人数" + onlineNumber);
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            logger.info("来自客户端消息：" + message + "客户端的id是：" + session.getId());
            ChatRecord cr = JSON.parseObject(message, ChatRecord.class);

            sendMessageTo(message, cr.getToUserId());
            if ("N".equals(cr.getMsgType())) {
                int b = this.chatRecordService.chatBeenRead(cr.getMsgId());

            } else {
                int a = this.chatRecordService.saveChatRecord(cr);
            }
        } catch (Exception e) {
            logger.info("发生了错误了");
        }

    }

    public void sendMessageTo(String message, String TouserId) throws IOException {
        Session session = clients.get(TouserId);
        session.getAsyncRemote().sendText(message);
    }

}