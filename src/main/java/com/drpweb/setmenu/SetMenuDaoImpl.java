package com.drpweb.setmenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ADMIN on 10/14/2016.
 */
@Repository
public class SetMenuDaoImpl implements SetMenuDao{
    @Autowired
    SetMenuRepository  setMenuRepository;

    @Override
    public SetMenu create(SetMenu setMenu) {
        return setMenuRepository.save(setMenu);
    }

    @Override
    public SetMenu update(SetMenu setMenu) {
        return setMenuRepository.save(setMenu);
    }

    @Override
    public void delete(SetMenu setMenu) {
    setMenuRepository.delete(setMenu);
    }

    @Override
    public SetMenu findOne(int id) {
        return setMenuRepository.findOne(id);
    }

    @Override
    public List<SetMenu> findAll() {
        return setMenuRepository.findAll();
    }
}
