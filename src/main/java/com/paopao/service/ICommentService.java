package com.paopao.service;



import com.paopao.model.pojo.Comment;

import java.util.List;

public interface ICommentService extends BaseService<Comment> {
    void newComment(Comment comment);
    List<Comment> getMessageComment(int messageId);
}
