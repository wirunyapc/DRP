package com.drpweb.setmenu;

import com.drpweb.diet_plan.DietPlanDao;
import com.drpweb.disease.DiseaseDao;
import com.drpweb.food_set_disease.FoodSetDisease;
import com.drpweb.food_set_disease.FoodSetDiseaseDao;
import com.drpweb.food_setmenu.FoodSetMenu;
import com.drpweb.food_setmenu.FoodSetMenuDao;
import com.drpweb.ingredient_on_food.IngredientOnFood;
import com.drpweb.ingredient_on_food.IngredientOnFoodDao;
import com.drpweb.ingredient_set_dislike.IngredientDislike;
import com.drpweb.ingredient_set_dislike.IngredientDislikeDao;
import com.drpweb.user.User;
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
    @Autowired
    FoodSetDiseaseDao foodSetDiseaseDao;
    @Autowired
    DiseaseDao diseaseDao;
    @Autowired
    FoodSetMenuDao foodSetMenuDao;
    @Autowired
    DietPlanDao dietPlanDao;
    @Autowired
    IngredientOnFoodDao ingreOnFoodDao;
    @Autowired
    IngredientDislikeDao ingredislikeDao;

/*    @Override
    public List<SetMenu> getAllSetMenus() throws SQLException {
        return setMenuDao.findAll();
    }*/

    @Override
    public List<Integer> getCantEatSetMenuByIngredient(User user) throws SQLException {
        List<IngredientDislike> setDislike = ingredislikeDao.findByUserId(user.getId());
        List<IngredientOnFood> ingreOnFoodDislike = new ArrayList<>();
        for (IngredientDislike i: setDislike ) {
            for (IngredientOnFood j:ingreOnFoodDao.findAll()) {
                if(i.getIngredientId().equals(j.getIngredientId())){
                    System.out.println("Ingredient dislike "+ j.getIngredientId()+"On food "+j.getFoodIndex());
                    ingreOnFoodDislike.add(ingreOnFoodDao.findOne(j.getId()));

                }
            }

        }

        List<Integer> setMenuDislike = new ArrayList<>();

        for (IngredientOnFood i : ingreOnFoodDislike) {
            for (FoodSetMenu f : foodSetMenuDao.findAll()) {
                if(i.getFoodIndex()==(f.getFoodId())){
                    System.out.println("Set menu dislike" + f.getSetmenu());
                    setMenuDislike.add(f.getSetmenu());
                }
            }
        }

        return setMenuDislike;
    }

    @Override
    public List<SetMenu> getSetMenu(User user) throws SQLException {
        List<IngredientDislike> useridslike = ingredislikeDao.findByUserId(user.getId());
        if(useridslike.isEmpty()){
            return setMenuDao.findAll();
        }

        List<Integer> setMenuDislike = getCantEatSetMenuByIngredient(user);

        List<SetMenu> setMenus = setMenuDao.findBySetmenuNotIn(setMenuDislike);
        for (SetMenu s : setMenus) {
            System.out.println("Set menu to use " + s.getSetmenu());
        }

        return setMenus;
    }

    @Override
    public  List<SetMenu> getSetMenuByDisease(User user) throws SQLException {
       // SetMenu setMenu = new SetMenu();

        List<FoodSetDisease> foodSetDiseases = foodSetDiseaseDao.findByDisease(user.getDiseaseId());


        List<Integer> setMenuCantEatId = new ArrayList<>();
        List<SetMenu> setFromDisease;
        List<Integer> setCantEatFromIngre;
        List<SetMenu> setToEat = new ArrayList<>();
        List<Integer> setCantEatFromTwoCriteria = new ArrayList<>();
        List<SetMenu> setCanEatFromTwoCriteria;

//        if(!foodSetDiseases.isEmpty()) {
            for (FoodSetDisease f : foodSetDiseases) {
                setMenuCantEatId.add(f.getSetmenu());
            }

            setFromDisease = setMenuDao.findBySetmenuNotIn(setMenuCantEatId);
            List<IngredientDislike> useridslike = ingredislikeDao.findByUserId(user.getId());
            if(useridslike.isEmpty()){
                System.out.println("Patient dislike is empty");
                return  setFromDisease;
            }

            setCantEatFromIngre = getCantEatSetMenuByIngredient(user);

            for (int s : setMenuCantEatId) {
                System.out.println("Set can not eat from disease" + s);
                setCantEatFromTwoCriteria.add(s);
            }
            for (int a : setCantEatFromIngre) {
                System.out.println("Set can not eat from ingredient" + a);
                setCantEatFromTwoCriteria.add(a);
            }

            for (int i : setCantEatFromTwoCriteria) {
                System.out.println("Set can not eat from two criteria" + i);
            }

            setCanEatFromTwoCriteria = setMenuDao.findBySetmenuNotIn(setCantEatFromTwoCriteria);
            for (SetMenu i : setCanEatFromTwoCriteria) {
                System.out.println("Set can eat from two criteria" + i.getSetmenu());
            }
/*            for (int  s : setCantEatFromIngre ) {
                for ( SetMenu e: setFromDisease) {
                    if(s!=e.getSetmenu()) {
                        System.out.println("Set can eat from disease and ingre" + e.getSetmenu());
                        setToEat.add(e);
                    }else {
                        System.out.println("Set can not eat from disease and ingre" + e.getSetmenu());
                        setCantEatFromTwoCriteria.add(e);
                    }
                }
            }*/


//        }else{
//            return getSetMenu(user);
//        }


    return setCanEatFromTwoCriteria;

    }

    @Override
    public SetMenu toSetMenu(List<SetMenu> setMenus) {
        SetMenu setMenu = new SetMenu();
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
                arr_setMenu_id[idx] = s.getSetmenu();
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
