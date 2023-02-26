package com.paopao.dao.impl;

import com.paopao.dao.IFollowDao;
import com.paopao.mapper.FollowMapper;
import com.paopao.model.pojo.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FollowDao extends AbstractDao implements IFollowDao {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private FollowMapper followMapper;

    @Override
    public void inserts(List<Follow> items) {
        followMapper.inserts(items);
    }

    @Override
    public void delete(int id) {
        followMapper.delete(id);
    }

    @Override
    public void deletes(List<Integer> ids) {
        followMapper.deletes(ids);
    }

    @Override
    public void update(Follow item) {
        followMapper.update(item);
    }

    @Override
    public void updates(List<Follow> items) {
        followMapper.updates(items);
    }

    @Override
    public List<Follow> selects(Map<String, Object> condition) {
        return followMapper.selects(condition);
    }
}
