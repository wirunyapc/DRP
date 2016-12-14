package com.drpweb.setmenu;

import com.drpweb.user.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ADMIN on 10/14/2016.
 */
public interface SetMenuService {
    List<SetMenu> getSetMenu(User user) throws SQLException;
    List<SetMenu> getSetMenuByDisease(User user) throws SQLException;
    SetMenu toSetMenu(List<SetMenu> setMenu);
    List<Integer> getCantEatSetMenuByIngredient(User user) throws SQLException;
   // List<SetMenu> getAllSetMenus() throws SQLException;
}
