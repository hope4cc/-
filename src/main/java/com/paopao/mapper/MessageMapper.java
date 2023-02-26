package com.paopao.mapper;


import com.paopao.model.pojo.Message;
import com.paopao.model.pojo.Pager;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    List<Message> getFollowingMessage(int id, Pager pager);
    Message getMessageByIdAndCurUserId(int messageId,int curUserId);


    List<Message> ramselects(Map<String, Object> condition);
}
