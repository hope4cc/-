package com.paopao.dao.impl;

import com.paopao.dao.ICrewDao;
import com.paopao.exception.GlobalException;
import com.paopao.mapper.CrewMapper;
import com.paopao.model.pojo.Crew;
import com.paopao.model.pojo.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CrewDao extends AbstractDao implements ICrewDao {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private CrewMapper crewMapper;

    @Override
    public void inserts(List<Crew> items) {
        crewMapper.inserts(items);
    }

    @Override
    public void delete(int id) {
        crewMapper.delete(id);
    }

    @Override
    public void deletes(List<Integer> ids) {
        crewMapper.deletes(ids);
    }

    @Override
    public void update(Crew item) {
        crewMapper.update(item);
    }

    @Override
    public void updates(List<Crew> items) {
        crewMapper.updates(items);
    }

    @Override
    public List<Crew> selects(Map<String, Object> condition) {
        return crewMapper.selects(condition);
    }

    @Override
    public List<Crew> selects(Map<String, Object> condition, Pager pager) throws GlobalException {
        setPager(condition,pager);
        return crewMapper.selects(condition);
    }
}
