package com.paopao.dao.impl;


import com.paopao.dao.INoticeDao;
import com.paopao.mapper.NoticeMapper;
import com.paopao.model.pojo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class NoticeDao extends AbstractDao implements INoticeDao {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public void inserts(List<Notice> items) {
        noticeMapper.inserts(items);
    }

    @Override
    public void delete(int id) {
        noticeMapper.delete(id);
    }

    @Override
    public void deletes(List<Integer> ids) {
        noticeMapper.deletes(ids);
    }

    @Override
    public void update(Notice item) {
        noticeMapper.update(item);
    }

    @Override
    public void updates(List<Notice> items) {
        noticeMapper.updates(items);
    }

    @Override
    public List<Notice> selects(Map<String, Object> condition) {
        return noticeMapper.selects(condition);
    }
}
