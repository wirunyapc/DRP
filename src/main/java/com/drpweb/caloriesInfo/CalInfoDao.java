package com.drpweb.caloriesInfo;

import java.util.List;

/**
 * Created by ADMIN on 8/27/2016.
 */
public interface CalInfoDao {
    CalInfo create(CalInfo calInfo);
    CalInfo update (CalInfo calInfo);
    void delete(CalInfo food);
    CalInfo findOne(Long id);
    List<CalInfo> findAll ();

}
