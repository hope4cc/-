package com.paopao.service;


import com.paopao.exception.FirstLoginException;
import com.paopao.exception.GlobalException;
import com.paopao.model.pojo.Crew;

public interface ICrewService extends BaseService<Crew> {
    boolean auth(Crew crew);

    String tokenResolve(Crew crew, boolean doForce) throws FirstLoginException;

    boolean isCrewAlreadyExist(String crewName, String phoneNum, String mail);

    int register(Crew crew);

    Crew getByPhone(String phone) throws GlobalException;

    Crew getByMail(String mail) throws GlobalException;

    Crew getByCrewName(String crewName) throws GlobalException;

    Crew getById(int id) throws GlobalException;

    void updateAvatar(String crewName,String fileName) throws GlobalException;
}
