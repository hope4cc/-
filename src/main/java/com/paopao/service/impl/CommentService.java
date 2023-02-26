package com.paopao.service.impl;


import com.paopao.cache.GlobalCache;
import com.paopao.dao.ICommentDao;
import com.paopao.model.pojo.Comment;
import com.paopao.service.ICommentService;
import com.paopao.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CommentService implements ICommentService {

    @Autowired
    private ICommentDao commentDao;
    @Autowired
    private GlobalCache globalCache;


    @Override
    public void inserts(List<Comment> items) {
        commentDao.inserts(items);
    }

    @Override
    public void delete(int id) {
        commentDao.delete(id);
    }

    @Override
    public void deletes(List<Integer> ids) {
        commentDao.deletes(ids);
    }

    @Override
    public void update(Comment newComment){
        commentDao.update(newComment);
    }

    @Override
    public void updates(List<Comment> items) {
        commentDao.updates(items);
    }

    @Override
    public List<Comment> selects(Map<String, Object> condition) {
        return commentDao.selects(condition);
    }

    @Override
    public void newComment(Comment comment) {
        comment.setCreateTime(DateUtil.getTimeString(new Date()));

        List<Comment> list = new ArrayList<>();
        list.add(comment);
        commentDao.inserts(list);
    }

    @Override
    public List<Comment> getMessageComment(int messageId) {
        Comment comment = new Comment();
        comment.setMessageId(messageId);
        Map<String,Object> condition = new HashMap<>();
        condition.put("item", comment);
        return commentDao.selects(condition);
    }
}
