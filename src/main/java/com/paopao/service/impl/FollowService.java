package com.paopao.service.impl;

import com.paopao.cache.GlobalCache;
import com.paopao.dao.IFollowDao;
import com.paopao.exception.GlobalException;
import com.paopao.logger.SysLogger;
import com.paopao.model.pojo.Follow;
import com.paopao.service.IFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FollowService implements IFollowService {

    @Autowired
    private IFollowDao followDao;
    @Autowired
    private GlobalCache globalCache;


    @Override
    public void inserts(List<Follow> items) {
        followDao.inserts(items);
    }

    @Override
    public void delete(int id) {
        followDao.delete(id);
    }

    @Override
    public void deletes(List<Integer> ids) {
        followDao.deletes(ids);
    }

    @Override
    public void update(Follow item) throws GlobalException {
        followDao.update(item);
    }

    @Override
    public void updates(List<Follow> items) {
        followDao.updates(items);
    }

    @Override
    public List<Follow> selects(Map<String, Object> condition) {
        return followDao.selects(condition);
    }

    @Override
    public Follow getByFollowIdAndFollowingId(int followId, int followingId) throws GlobalException {
        Follow follow = new Follow();
        follow.setFollowerId(followId);
        follow.setFollowingId(followingId);
        Map<String,Object> condition = new HashMap<>();
        condition.put("item",follow);
        List<Follow> follows =  followDao.selects(condition);
        if(follows == null || follows.size() <= 0){
            return null;
        }else if(follows.size() > 1){
            SysLogger.info("检测到数据库数据关系异常,followId+followingId组合不唯一,followId:" +followId+ ",followingId" + followingId);
            throw new GlobalException();
        }else{
            return follows.get(0);
        }
    }

    @Override
    public List<Follow> getFollowers(int crewId) {
        Follow follow = new Follow();
        follow.setFollowingId(crewId);
        Map<String,Object> condition = new HashMap<>();
        condition.put("item",follow);
        List<Follow> follows =  followDao.selects(condition);
        if(follows == null || follows.size() <= 0){
            return null;
        }else{
            return follows;
        }
    }

    @Override
    public List<Follow> getFollowingList(int crewId) {
        Follow follow = new Follow();
        follow.setFollowerId(crewId);
        Map<String,Object> condition = new HashMap<>();
        condition.put("item",follow);
        List<Follow> follows =  followDao.selects(condition);
        if(follows == null || follows.size() <= 0){
            return null;
        }else{
            return follows;
        }
    }
}
