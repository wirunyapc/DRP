package com.drpweb.caloriesInfo;

import com.drpweb.user.User;

/**
 * Created by ADMIN on 8/27/2016.
 */
public interface CalInfoService {
     CalInfo getCalInfo(Long id);
    double calBudget(User user);
}
