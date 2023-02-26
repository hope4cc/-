package com.paopao.controller;


import com.paopao.async.RunnerQueue;
import com.paopao.exception.GlobalException;
import com.paopao.model.modelView.CommentResponse;
import com.paopao.model.pojo.Comment;
import com.paopao.model.pojo.Notice;
import com.paopao.service.ICommentService;
import com.paopao.service.INoticeService;
import com.paopao.util.MyHttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/comment")
@ResponseBody
public class CommentController {

    @Autowired
    private ICommentService commentService;
    @Autowired
    private INoticeService noticeService;
    @Autowired
    RunnerQueue runnerQueue;
    @Autowired
    ApplicationContext applicationContext;

    /**
     * 添加回复
     * @param comment 回复数据
     */
    @RequestMapping(value = "/newComment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommentResponse newComment(@RequestBody Comment comment) throws GlobalException {
        MyHttpStatus status = MyHttpStatus.OK;
        //调用评论业务
        commentService.newComment(comment);
        //评论后通知消息 该微博的用户
        noticeService.produceNotice(comment.getMessageCreator(), Notice.NoticeType.COMMENT,comment.getMessageId(),0);
        return new CommentResponse(status);
    }

    /**
     * 根据MessageId获取回复list
     */
    @RequestMapping(value = "/commentList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommentResponse commentList(@RequestBody Comment comment){
        MyHttpStatus status = MyHttpStatus.OK;
        List<Comment> comments = commentService.getMessageComment(comment.getMessageId());
        return new CommentResponse(status,comments);
    }

}
