package com.drpweb.caloriesInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ADMIN on 8/27/2016.
 */
@Repository
public class CalInfoDaoImpl implements CalInfoDao{
    @Autowired
    CalInfoRepository calInfoRepository;

    @Override
    public CalInfo create(CalInfo calInfo) {
        return calInfoRepository.save(calInfo);
    }

    @Override
    public CalInfo update(CalInfo calInfo) {
        return null;
    }

    @Override
    public void delete(CalInfo food) {

    }

    @Override
    public CalInfo findOne(Long id) {
        return null;
    }

    @Override
    public List<CalInfo> findAll() {
        return null;
    }
}
