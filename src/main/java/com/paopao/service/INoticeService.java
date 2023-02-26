package com.paopao.service;


import com.paopao.exception.GlobalException;
import com.paopao.model.pojo.Notice;


import java.util.List;

public interface INoticeService extends BaseService<Notice> {
    void produceNotice(int crewId,int noticeType, int messageId, int followerId) throws GlobalException;
    List<Notice> getCurUserNewNotice(int crewId);
    void readNotice(int id) throws GlobalException;
    Notice getById(int id) throws GlobalException;

}
