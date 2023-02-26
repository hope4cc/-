package com.paopao.service;


import com.paopao.exception.GlobalException;
import com.paopao.model.pojo.Like;

public interface ILikeService extends BaseService<Like> {
    Like selectByLikerIdAndMessageId(int likerId,int messageId) throws GlobalException;
}
