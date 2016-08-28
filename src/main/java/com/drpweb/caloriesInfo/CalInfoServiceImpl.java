package com.drpweb.caloriesInfo;

import com.drpweb.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Created by ADMIN on 8/27/2016.
 */
@Service
public class CalInfoServiceImpl implements CalInfoService{
    @Autowired
    CalInfoDao calInfoDao;

    @Override
    public CalInfo getCalInfo(Long id) {

        return calInfoDao.findOne(id);
    }
    @Override
    public double calBudget(User user){
        double budget;
        LocalDate today = LocalDate.now();

        //LocalDate birthday = LocalDate.of(user.getDob());

       // Period p = Period.between(birthday, today);
        if(user.getGender()=="female"){
          // budget = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * age(y) - 161;
        }
        return 0.0;
    }


}
