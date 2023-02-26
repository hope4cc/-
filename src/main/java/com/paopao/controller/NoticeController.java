package com.paopao.controller;


import com.paopao.async.RunnerQueue;
import com.paopao.exception.GlobalException;
import com.paopao.model.modelView.NoticeResponse;
import com.paopao.model.pojo.Notice;
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
@RequestMapping("/notice")
@ResponseBody
public class NoticeController {

    @Autowired
    private INoticeService noticeService;
    @Autowired
    RunnerQueue runnerQueue;
    @Autowired
    ApplicationContext applicationContext;

    /**
     * 获取当前用户的未读消息
     */
    @RequestMapping(value = "/getNewNotice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public NoticeResponse getNewNotice(@RequestBody Notice notice) {
        MyHttpStatus status = MyHttpStatus.OK;
        //用户已读 调用业务 增加通知数
        List<Notice> notices = noticeService.getCurUserNewNotice(notice.getNotifierId());

        NoticeResponse result = new NoticeResponse(status,notices);
        return result;
    }

    /**
     * 更新为已读
     */
    @RequestMapping(value = "/readNotice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public NoticeResponse readNotice(@RequestBody Notice notice) throws GlobalException {
        MyHttpStatus status = MyHttpStatus.OK;
        //用户已读 调用业务 删除通知数
        noticeService.readNotice(notice.getId());

        NoticeResponse result = new NoticeResponse(status);
        return result;
    }
}
