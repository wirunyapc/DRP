package com.drpweb.role;

import java.util.List;

/**
 * Created by Asus on 7/8/2559.
 */
public interface RoleDao {
    Role create(Role role);
    Role update (Role role);
    void delete(Role role);
    Role findOne(Long id);
    List<Role> findAll ();
    Role findByRoleName(String RoleName);
}
