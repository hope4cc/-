package com.paopao.controller;


import com.paopao.async.RunnerQueue;
import com.paopao.exception.GlobalException;
import com.paopao.logger.SysLogger;
import com.paopao.model.modelView.MessageResponse;
import com.paopao.model.modelView.RequestModel.MessageRequest;
import com.paopao.model.pojo.Follow;
import com.paopao.model.pojo.Message;
import com.paopao.model.pojo.Notice;
import com.paopao.service.IFollowService;
import com.paopao.service.IMessageService;
import com.paopao.service.INoticeService;
import com.paopao.util.DateUtil;
import com.paopao.util.MyHttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/message")
@ResponseBody
public class MessageController {

    @Autowired
    private IMessageService messageService;
    @Autowired
    private IFollowService followService;
    @Autowired
    private INoticeService noticeService;
    @Autowired
    RunnerQueue runnerQueue;
    @Autowired
    ApplicationContext applicationContext;

    /**
     * 新增微博
     */
    @RequestMapping(value = "/newMessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse newMessage(@RequestBody Message message) throws GlobalException {
        MyHttpStatus status = MyHttpStatus.OK;
        //保存微博
        messageService.insert(message);
        //获取messageCreator的关注用户
        List<Follow> followers = followService.getFollowers(message.getCreator());
        //判断该用户是否有粉丝
        if (followers != null) {
            //不为空 遍历粉丝列表
            for (Follow follow : followers) {
                //为每一位粉丝推送消息
                noticeService.produceNotice(follow.getFollowerId(), Notice.NoticeType.NEW_MESSAGE, message.getId(), 0);
            }
        }
        //响应返回
        MessageResponse result = new MessageResponse(status);
        return result;
    }

    /**
     * 新增转推伊文
     */
    @RequestMapping(value = "/newReTweetMessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse newReTweetMessage(@RequestBody Message message) throws GlobalException {
        MyHttpStatus status = MyHttpStatus.OK;

        messageService.retweet(message, message.getCurUserId());

        MessageResponse result = new MessageResponse(status);
        return result;
    }

    /**
     * 取消转推伊文
     */
    @RequestMapping(value = "/cancelReTweetMessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse cancelReTweetMessage(@RequestBody Message message) throws GlobalException {
        MyHttpStatus status = MyHttpStatus.OK;

        messageService.cancelRetweet(message.getCurUserId(), message.getRetweetMessageId());

        MessageResponse result = new MessageResponse(status);
        return result;
    }

    /**
     * 获取我的微博
     */
    @RequestMapping(value = "/getMyMessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse getMyMessage(@RequestBody Message message) {
        MyHttpStatus status = MyHttpStatus.OK;

        List<Message> list = messageService.getMyMessage(message.getCurUserId());

        MessageResponse result = new MessageResponse(status, list, DateUtil.getTimeString(new Date()));
        return result;
    }

    /**
     * 获取某用户的伊文
     */
    @RequestMapping(value = "/getUserMessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse getUserMessage(@RequestBody Message message) {
        MyHttpStatus status = MyHttpStatus.OK;

        List<Message> list = messageService.getUserMessage(message.getCurUserId(), message.getCreator());

        MessageResponse result = new MessageResponse(status, list, DateUtil.getTimeString(new Date()));
        return result;
    }

    /**
     * 获取我关注的(若没有任何关注或者关注内容返回空则启动“随便看看”服务)
     */
    @RequestMapping(value = "/getFriendMessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse getFriendMessage(@RequestBody MessageRequest messageRequest) throws GlobalException {
        MyHttpStatus status = MyHttpStatus.OK;
        List<Message> list = null;
        int curUserId = messageRequest.getMessage().getId();
        List<Follow> follows = followService.getFollowingList(curUserId);
        if (follows != null && follows.size() > 0) {
            list = messageService.getFollowingMessage(curUserId, messageRequest.getPager());
        } else {
            SysLogger.info("无关注内容,自动执行随便看看服务");
            list = messageService.getMessageRandom(curUserId, messageRequest.getPager());
        }
        MessageResponse result = new MessageResponse(status, list, DateUtil.getTimeString(new Date()));
        return result;
    }


    @RequestMapping(value = "/getRandomMessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse getRandomMessage(@RequestBody MessageRequest messageRequest) throws GlobalException {
        MyHttpStatus status = MyHttpStatus.OK;
        List<Message> list = null;
        int curUserId = messageRequest.getMessage().getId();
            SysLogger.info("随便看看服务");
            list = messageService.getMessageRandom(curUserId, messageRequest.getPager());
        MessageResponse result = new MessageResponse(status, list, DateUtil.getTimeString(new Date()));
        return result;
    }


    /**
     * 微博详情
     */
    @RequestMapping(value = "/messageDetail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse messageDetail(@RequestBody Message message) {
        MyHttpStatus status = MyHttpStatus.OK;
        //获取微博id和用户id
        Message hit = messageService.getMessageByIdAndCurUserId(message.getId(), message.getCurUserId());
        int viewNums = messageService.getMessageViewNumsByRedis(message.getId(), message.getCurUserId());
        if (hit == null) {
            //判断是否为空 为空提示404错误
            return new MessageResponse(MyHttpStatus.MESSAGE_404);
        }
        //不为空将html转换 响应返回
        hit.setViewsNums(String.valueOf(viewNums));
        MessageResponse result = new MessageResponse(status, hit);
        return result;
    }
}
