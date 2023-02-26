package com.paopao.dao.impl;


import com.paopao.config.ContantsContext;
import com.paopao.exception.GlobalException;
import com.paopao.model.pojo.Pager;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public abstract class AbstractDao{
    private String msg = "添加分页器失败,condition为空";

    public void setPager(Map<String, Object> condition) throws GlobalException {
        if(condition == null){
            throw new GlobalException(msg);
        }
        condition.put("pager",new Pager(1, ContantsContext.PAGER_SIZE));
    }

    public void setPager(Map<String, Object> condition, Pager pager) throws GlobalException {
        if(condition == null){
            throw new GlobalException(msg);
        }
        condition.put("pager",pager);
    }
}
