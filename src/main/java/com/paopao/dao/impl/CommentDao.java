package com.paopao.dao.impl;


import com.paopao.dao.ICommentDao;
import com.paopao.mapper.CommentMapper;
import com.paopao.model.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CommentDao extends AbstractDao implements ICommentDao {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void inserts(List<Comment> items) {
        commentMapper.inserts(items);
    }

    @Override
    public void delete(int id) {
        commentMapper.delete(id);
    }

    @Override
    public void deletes(List<Integer> ids) {
        commentMapper.deletes(ids);
    }

    @Override
    public void update(Comment item) {
        commentMapper.update(item);
    }

    @Override
    public void updates(List<Comment> items) {
        commentMapper.updates(items);
    }

    @Override
    public List<Comment> selects(Map<String, Object> condition) {
        return commentMapper.selects(condition);
    }

}
