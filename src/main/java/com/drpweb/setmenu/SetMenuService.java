package com.drpweb.setmenu;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ADMIN on 10/14/2016.
 */
public interface SetMenuService {
    List<SetMenu> getSetMenu() throws SQLException;
    List<SetMenu> getSetMenuByDisease(Long diseaseId) throws SQLException;
    SetMenu toSetMenu(List<SetMenu> setMenu);
}
