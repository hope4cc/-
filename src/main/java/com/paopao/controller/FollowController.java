package com.paopao.controller;

import com.paopao.async.RunnerQueue;
import com.paopao.exception.GlobalException;
import com.paopao.model.modelView.FollowResponse;
import com.paopao.model.pojo.Crew;
import com.paopao.model.pojo.Follow;
import com.paopao.model.pojo.Notice;
import com.paopao.service.ICrewService;
import com.paopao.service.IFollowService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/follow")
@ResponseBody
public class FollowController {

    @Autowired
    private IFollowService followService;
    @Autowired
    private INoticeService noticeService;
    @Autowired
    private ICrewService crewService;
    @Autowired
    RunnerQueue runnerQueue;
    @Autowired
    ApplicationContext applicationContext;

    /**
     * 查询是否关注
     */
    @RequestMapping(value = "/isFollowing", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public FollowResponse isFollowing(@RequestBody Follow follow) throws GlobalException {
        MyHttpStatus status = MyHttpStatus.OK;
        Follow followData = followService.getByFollowIdAndFollowingId(follow.getFollowerId(), follow.getFollowingId());
        FollowResponse result = new FollowResponse(status, followData);
        return result;
    }

    /**
     * 添加关注
     */
    @RequestMapping(value = "/addFollow", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public FollowResponse addFollow(@RequestBody Follow follow) throws GlobalException {
        MyHttpStatus status = MyHttpStatus.OK;
        //调用业务层查询关注人 判断是否存在，获取该用户信息
        Crew follower = crewService.getById(follow.getFollowerId());
        //调用业务层查询 被关注人 判断是否存在，获取该用户信息
        Crew following = crewService.getById(follow.getFollowingId());
        List<Follow> follows = new ArrayList<>();
        follow.setFollowerName(follower.getCrewName());
        follow.setFollowingName(following.getCrewName());
        follow.setFollowTime(DateUtil.getTimeString(new Date()));
        //填充关注人实体
        follows.add(follow);
        //新增关注与被关注人 关联关系
        followService.inserts(follows);
        //通知用户
        noticeService.produceNotice(following.getId(), Notice.NoticeType.FOLLOW,0, follower.getId());

        FollowResponse result = new FollowResponse(status);
        return result;
    }

    /**
     * 取消关注
     */
    @RequestMapping(value = "/cancelFollow", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public FollowResponse cancelFollow(@RequestBody Follow follow) throws GlobalException {
        MyHttpStatus status = MyHttpStatus.OK;

        Follow curFollow = followService.getByFollowIdAndFollowingId(follow.getFollowerId(),follow.getFollowingId());
        followService.delete(curFollow.getId());

        FollowResponse result = new FollowResponse(status);
        return result;
    }


}
