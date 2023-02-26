package com.paopao.controller;

import com.paopao.async.RunnerQueue;
import com.paopao.exception.GlobalException;
import com.paopao.model.modelView.LikeResponse;
import com.paopao.model.pojo.Like;
import com.paopao.model.pojo.Notice;
import com.paopao.service.ILikeService;
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

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/like")
@ResponseBody
public class LikeController {

    @Autowired
    private ILikeService likeService;
    @Autowired
    private INoticeService noticeService;
    @Autowired
    RunnerQueue runnerQueue;
    @Autowired
    ApplicationContext applicationContext;

    /**
     * 喜欢
     */
    @RequestMapping(value = "/doLike", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LikeResponse doLike(@RequestBody Like like) throws GlobalException {
        MyHttpStatus status = MyHttpStatus.OK;

        List<Like> likes = new ArrayList<>();
        likes.add(like);
        likeService.inserts(likes);

        LikeResponse result = new LikeResponse(status);

        noticeService.produceNotice(like.getMessageCreator() , Notice.NoticeType.LIKE,like.getMessageId(), 0);

        return result;
    }

    /**
     * 取消喜欢
     */
    @RequestMapping(value = "/cancelLike", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LikeResponse cancelLike(@RequestBody Like like) throws GlobalException {
        MyHttpStatus status = MyHttpStatus.OK;

        Like hitLike = likeService.selectByLikerIdAndMessageId(like.getLikerId(),like.getMessageId());
        likeService.delete(hitLike.getId());

        LikeResponse result = new LikeResponse(status);
        return result;
    }

}
