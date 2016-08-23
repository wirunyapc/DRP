package com.drpweb.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Asus on 7/8/2559.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;

    @Override
    public Role create(Role role) {
        return roleDao.create(role);
    }

    @Override
    public Role update(Role role) {
        return roleDao.update(role);
    }

    @Override
    public void delete(Long id) {
        roleDao.delete(roleDao.findOne(id));
    }

    @Override
    public Role findOne(Long id) {
        return roleDao.findOne(id);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByRoleName(name);
    }
}
