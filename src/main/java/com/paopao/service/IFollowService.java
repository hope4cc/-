package com.paopao.service;


import com.paopao.exception.GlobalException;
import com.paopao.model.pojo.Follow;

import java.util.List;

public interface IFollowService extends BaseService<Follow> {
    List<Follow> getFollowers(int crewId);
    List<Follow> getFollowingList(int crewId);
    Follow getByFollowIdAndFollowingId(int followId,int followingId) throws GlobalException;
}
