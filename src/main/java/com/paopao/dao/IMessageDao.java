package com.paopao.dao;



import com.paopao.model.pojo.Message;
import com.paopao.model.pojo.Pager;

import java.util.List;
import java.util.Map;

public interface IMessageDao extends BaseDao<Message> {
    List<Message> getFollowingMessage(int id, Pager pager);
    Message getMessageByIdAndCurUserId(int id,int curUserId);


    List<Message> ramselects(Map<String, Object> condition, Pager pager);
}
