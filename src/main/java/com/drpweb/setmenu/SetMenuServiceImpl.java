package com.drpweb.setmenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 10/14/2016.
 */
@Service
public class SetMenuServiceImpl implements SetMenuService {
    @Autowired
    SetMenuDao setMenuDao;

    @Override
    public SetMenu getSetMenu() throws SQLException {

        SetMenu setMenu = new SetMenu();
        List<SetMenu> setMenus = setMenuDao.findAll();
        System.out.println("SetMenulistttt" + setMenus);
        if(setMenus!=null) {
            int size = setMenus.size();
            int[] arr_setMenu_id = new int[size];
            int[] arr_total_cal = new int[size];
            int[] arr_total_fat = new int[size];
            int[] arr_total_carboh = new int[size];
            int[] arr_total_protein = new int[size];

            ArrayList<int[]> total_cals = new ArrayList();
            ArrayList<int[]> total_fats = new ArrayList();
            ArrayList<int[]> total_carbohs = new ArrayList();
            ArrayList<int[]> total_proteins = new ArrayList();
            //ArrayList<String> names = new ArrayList();

            int idx = 0;
            for (SetMenu s : setMenus) {
                arr_setMenu_id[idx] = s.getSetMenu_id();
                arr_total_cal[idx] = s.getTotal_cal();
                arr_total_fat[idx] = s.getTotal_fat();
                arr_total_carboh[idx] = s.getTotal_carboh();
                arr_total_protein[idx] = s.getTotal_protein();

                total_cals.add(new int[]{arr_setMenu_id[idx], arr_total_cal[idx]});
                total_fats.add(new int[]{arr_setMenu_id[idx], arr_total_fat[idx]});
                total_carbohs.add(new int[]{arr_setMenu_id[idx], arr_total_carboh[idx]});
                total_proteins.add(new int[]{arr_setMenu_id[idx], arr_total_protein[idx]});
               // names.add(f.getFoodName());

                idx++;
//            food.getKals().add(f.getArr_kal());
            }

            setMenu.setArr_setMenu_id(arr_setMenu_id);
            setMenu.setArr_total_cal(arr_total_cal);
            setMenu.setArr_total_fat(arr_total_fat);
            setMenu.setArr_total_carboh(arr_total_carboh);
            setMenu.setArr_total_protein(arr_total_protein);
            setMenu.setTotal_cals(total_cals);
            setMenu.setTotal_fats(total_fats);
            setMenu.setTotal_carbohs(total_carbohs);
            setMenu.setTotal_proteins(total_proteins);
           // setMenu.setNames(names);

           // System.out.println(" setNamesssssss "+ food.getNames());
        }
        return setMenu;
    }
}
