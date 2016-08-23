package com.drpweb.role;

import java.util.List;

/**
 * Created by Asus on 7/8/2559.
 */
public interface RoleService {
    Role create(Role role);
    Role update (Role role);
    void delete(Long id);
    Role findOne(Long id);
    List<Role> findAll ();
    Role findByName(String name);
}
