package com.paopao.model.modelView.RequestModel;


import com.paopao.model.pojo.Message;
import com.paopao.model.pojo.Pager;

public class MessageRequest {
    private Message message;
    private Pager pager;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}
