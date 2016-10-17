package com.drpweb.setmenu;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ADMIN on 10/14/2016.
 */
public interface SetMenuService {
    SetMenu getSetMenu() throws SQLException;
    SetMenu getSetMenuByDisease(String diseaseName) throws SQLException;
    SetMenu toSetMenu(List<SetMenu> setMenu);
}
