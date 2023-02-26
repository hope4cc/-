package com.paopao.service;




import com.paopao.model.pojo.WebsocketServerMessage;

import java.util.List;

public interface IWebSocektService {
    void sendMsg(WebsocketServerMessage message);

    void sendMsgToUser(String UserId,WebsocketServerMessage message);

    void sendMsgToUsers(List<String> users, WebsocketServerMessage message);
}
