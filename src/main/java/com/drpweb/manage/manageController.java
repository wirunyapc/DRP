package com.drpweb.manage;

import com.drpweb.food.FoodDao;
import com.drpweb.food_setmenu.FoodSetMenuDao;
import com.drpweb.setmenu.SetMenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ADMIN on 12/9/2016.
 */
@CrossOrigin
@RestController
public class manageController {
    @Autowired
    SetMenuDao setMenuDao;
    @Autowired
    FoodSetMenuDao foodSetMenuDao;
    @Autowired
    FoodDao foodDao;

/*    @RequestMapping(value = "/getAllSetMenu",method = RequestMethod.GET)
    public List<String> getAllSetMenu() throws SQLException {
        List<SetMenu> setMenus = setMenuDao.findAll();
        List<String> setMenuId = new ArrayList<>();

        return setMenu;
    }*/
}
