package com.drpweb.setmenu;

import java.util.List;

/**
 * Created by ADMIN on 10/14/2016.
 */
public interface SetMenuDao {

    SetMenu create(SetMenu setMenu);
    SetMenu update (SetMenu setMenu);
    void delete(SetMenu setMenu);
    SetMenu findOne(int id);
    List<SetMenu> findAll ();
}
