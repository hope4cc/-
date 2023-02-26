package com.paopao.async.asyncRunner;


import com.paopao.config.ContantsContext;
import com.paopao.model.pojo.Notice;
import com.paopao.model.pojo.WebsocketServerMessage;
import com.paopao.service.INoticeService;
import com.paopao.service.IWebSocektService;
import com.paopao.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 专门用于生产新通知的Runner
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProduceNoticeRunner extends BaseRunner{

    @Autowired
    private INoticeService noticeService;
    @Autowired
    private IWebSocektService webSocektService;


    private int crewId;
    private String content;
    private String url;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ProduceNoticeRunner(int crewId, String content, String url) {
        this.crewId = crewId;
        this.content = content;
        this.url = url;
    }



    @Override
    public void run() {
        //数据持久
        List<Notice> list = new ArrayList<>();
        Notice notice = new Notice();
        notice.setNotifierId(crewId);
        notice.setContent(content);
        notice.setCreateTime(DateUtil.getTimeString(new Date()));
        notice.setUrl(url);
        notice.setIsRead(ContantsContext.I_FALSE);
        list.add(notice);
        noticeService.inserts(list);

        //WebSocket通知前端刷新数据
        webSocektService.sendMsgToUser(String.valueOf(crewId),new WebsocketServerMessage(ContantsContext.TRUE));


        runnerQueue.runnerComplete();
    }
}
