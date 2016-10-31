package com.drpweb.setmenu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ADMIN on 10/14/2016.
 */
public interface SetMenuRepository extends JpaRepository<SetMenu,Integer> {
    List<SetMenu> findBySetmenuNotIn(List<Integer> setCantEat);

}
