package com.paopao.dao;


import com.paopao.exception.GlobalException;
import com.paopao.model.pojo.Pager;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {
    void inserts(List<T> items);
    void delete(int id);
    void deletes(List<Integer> ids);
    void update(T item);
    void updates(List<T> items);
    List<T> selects(Map<String, Object> condition);
    default List<T> selects(Map<String, Object> condition, Pager pager) throws GlobalException {
        throw new GlobalException("当前操作不支持");
    }
}
