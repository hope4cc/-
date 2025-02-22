package com.paopao.service.impl;

import com.paopao.cache.ValueIonaRedisCache;
import com.paopao.dao.IMessageDao;
import com.paopao.exception.GlobalException;
import com.paopao.logger.SysLogger;
import com.paopao.model.pojo.Message;
import com.paopao.model.pojo.Pager;
import com.paopao.service.IMessageService;
import com.paopao.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
public class MessageService implements IMessageService {

    @Autowired
    private IMessageDao messageDao;
    @Autowired
    private ValueIonaRedisCache redisCache;

    @Override
    public void inserts(List<Message> items) {
        messageDao.inserts(items);
    }

    @Override
    public void delete(int id) {
        messageDao.delete(id);
    }

    @Override
    public void deletes(List<Integer> ids) {
        messageDao.deletes(ids);
    }

    @Override
    public void update(Message item) throws GlobalException {
        messageDao.update(item);
    }

    @Override
    public void updates(List<Message> items) {
        messageDao.updates(items);
    }

    @Override
    public List<Message> selects(Map<String, Object> condition) {
        return messageDao.selects(condition);
    }

    @Override
    public void insert(Message message) {
        if (StringUtils.isEmpty(message.getCreateTime())) {
            message.setCreateTime(DateUtil.getTimeString(new Date()));
        }
        List<Message> list = new ArrayList<>();
        list.add(message);
        messageDao.inserts(list);
    }

    @Override
    public List<Message> getMessageByCreator(int creator) {
        Message message = new Message();
        message.setCreator(creator);
        Map<String,Object> condition = new HashMap<>();
        condition.put("item",message);
        return messageDao.selects(condition);
    }

    @Override
    public List<Message> getMyMessage(int curLoginUserId) {
        Message message = new Message();
        message.setCreator(curLoginUserId);
        message.setCurUserId(curLoginUserId);
        Map<String,Object> condition = new HashMap<>();
        condition.put("item",message);
        condition.put("OrRetweetorIdIsMy",curLoginUserId);//转推id不是我的排除掉,不展示在我的伊文中
        return messageDao.selects(condition);
    }

    @Override
    public List<Message> getUserMessage(int curLoginUserId, int curUserCardId) {
        Message message = new Message();
        message.setCreator(curUserCardId);
        message.setCurUserId(curLoginUserId);
        Map<String,Object> condition = new HashMap<>();
        condition.put("item",message);
        condition.put("OrRetweetorIdIsMy",curUserCardId);
        return messageDao.selects(condition);
    }

    @Override
    public List<Message> getMessageRandom(int curUserId, Pager pager)  {
        Message message = new Message();
        message.setCurUserId(curUserId);
        Map<String,Object> condition = new HashMap<>();
        condition.put("item",message);
        return messageDao.ramselects(condition,pager);
    }

    @Override
    public List<Message> getFollowingMessage(int curLoginUserId, Pager pager) {
        return messageDao.getFollowingMessage(curLoginUserId,pager);
    }

    @Override
    public Message getByRetweetorIdAndRetweetMessageId(int retweetorId, int retweetMessageId) throws GlobalException {
        Message message = new Message();
        message.setRetweetorId(retweetorId);
        message.setRetweetMessageId(retweetMessageId);
        Map<String,Object> condition = new HashMap<>();
        condition.put("item",message);
        List<Message> messages =  messageDao.selects(condition);
        if(messages == null || messages.size() <= 0){
            return null;
        }else if(messages.size() > 1){
            SysLogger.info("检测到数据库数据关系异常,retweetorId+retweetMessageId组合不唯一,retweetorId:" +retweetorId+ ",retweetMessageId" + retweetMessageId);
            throw new GlobalException();
        }else{
            return messages.get(0);
        }
    }

    @Override
    public void retweet(Message oldMessage, int retweetorId) {
        Message newRetweetMessage = new Message();
        BeanUtils.copyProperties(oldMessage,newRetweetMessage,"id");
        newRetweetMessage.setRetweetorId(retweetorId);
        newRetweetMessage.setRetweetMessageId(oldMessage.getId());
        newRetweetMessage.setRetweetTime(DateUtil.getTimeString(new Date()));
        newRetweetMessage.setCreateTime(newRetweetMessage.getRetweetTime());
        List<Message> list = new ArrayList<>();
        list.add(newRetweetMessage);
        messageDao.inserts(list);
    }

    @Override
    public void cancelRetweet(int retweetorId, int retweetMessageId) throws GlobalException {
        Message message = getByRetweetorIdAndRetweetMessageId(retweetorId,retweetMessageId);
        messageDao.delete(message.getId());
    }

    @Override
    public Message getMessageByIdAndCurUserId(int id, int curUserId) {
        return messageDao.getMessageByIdAndCurUserId(id,curUserId);
    }

    @Override
    public int getMessageViewNumsByRedis(int messageId, int curUserId) {

        String isCurUserViewsThisMessageKey = "user:" + curUserId + ":already:views:" + messageId;
        String viewNumsKey = "message:" + messageId + ":viewNums";

        Boolean isCurUserViewsThisMessage = (Boolean) redisCache.get(isCurUserViewsThisMessageKey);

        if(isCurUserViewsThisMessage != null && isCurUserViewsThisMessage){
            SysLogger.info("当前用户已经阅读过此博文,故计数器不+1");
        }else{
            redisCache.set(isCurUserViewsThisMessageKey,true);
            redisCache.incr(viewNumsKey);
        }

        return redisCache.getNums(viewNumsKey);
    }
}
