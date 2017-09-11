package com.drpweb.setmenu;

import com.drpweb.daily_meal.DailyMealService;
import com.drpweb.diet_plan.DietPlanDao;
import com.drpweb.diet_plan.DietPlanService;
import com.drpweb.food.Food;
import com.drpweb.food.FoodDao;
import com.drpweb.food_setmenu.FoodSetMenu;
import com.drpweb.food_setmenu.FoodSetMenuDao;
import com.drpweb.ingredient.IngredientDao;
import com.drpweb.ingredient_set_dislike.IngredientDislikeDao;
import com.drpweb.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 11/13/2016.
 */
@CrossOrigin
@RestController
public class SetMenuController {
    @Autowired
    IngredientDao ingredientDao;
    @Autowired
    UserService userService;
    @Autowired
    IngredientDislikeDao ingredientDisDao;
    @Autowired
    DietPlanService dietPlanService;
    @Autowired
    DietPlanDao dietPlanDao;
    @Autowired
    DailyMealService dailyMealService;
    @Autowired
    SetMenuDao setMenuDao;
    @Autowired
    FoodSetMenuDao foodSetMenuDao;
    @Autowired
    FoodDao foodDao;

    public int ID = 43;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/getSetMenuToManage", method = RequestMethod.GET)
    public List<String[]> getSetMenuToManage() throws SQLException {
        List<SetMenu> all = setMenuDao.findAll();

        List<String[]> setMenus = new ArrayList<>();

        String[] setMenu;
        List<FoodSetMenu> foodSetMenu;
        for (SetMenu s : all) {
            setMenu = new String[4];
            foodSetMenu = foodSetMenuDao.findBySetmenu(s.getSetmenu());
            setMenu[0] = "" + s.getSetmenu();
            int i = 1;
            for (FoodSetMenu foodSet : foodSetMenu) {
                setMenu[i] = (foodDao.findOne(foodSet.getFoodId()).getFoodNameEng());
                i++;
            }
            setMenus.add(setMenu);

        }
        return setMenus;

    }

    @RequestMapping(value = "/createSetMenu", method = RequestMethod.GET)
    public SetMenu createIngredient(@RequestParam("bfast") int bfast,
                                       @RequestParam("lunch") int lunch,
                                       @RequestParam("dinner") int dinner
    ) throws SQLException {
        List<SetMenu> setmenus = setMenuDao.findAll();
        int id = setmenus.get(setmenus.size() - 1).getSetmenu();
        System.out.println("Setmenu id !!!!!!!!!!!!!!!!!!" + id);

        Food breakfast = foodDao.findOne(bfast);
        Food lunchh = foodDao.findOne(lunch);
        Food dinnerr = foodDao.findOne(dinner);

        SetMenu setMenu = new SetMenu();
        setMenu.setSetmenu(id + 1);
        setMenu.setTotal_cal(breakfast.getKal()+lunchh.getKal()+dinnerr.getKal());
        setMenu.setTotal_fat(breakfast.getFat()+lunchh.getFat()+dinnerr.getFat());
        setMenu.setTotal_carboh(breakfast.getCarboh()+lunchh.getCarboh()+dinnerr.getCarboh());
        setMenu.setTotal_protein(breakfast.getProtein()+lunchh.getProtein()+dinnerr.getProtein());

        SetMenu s = setMenuDao.create(setMenu);
        FoodSetMenu f = new FoodSetMenu();
        f.setFoodId(breakfast.getFoodId());
        f.setSetmenu(s.getSetmenu());
        f.setFoodIndex(1);
        FoodSetMenu fAdded1 = foodSetMenuDao.create(f);

        FoodSetMenu g = new FoodSetMenu();
        g.setFoodId(lunchh.getFoodId());
        g.setSetmenu(s.getSetmenu());
        g.setFoodIndex(2);
        FoodSetMenu fAdded2 = foodSetMenuDao.create(g);

        FoodSetMenu h = new FoodSetMenu();
        h.setFoodId(dinnerr.getFoodId());
        h.setSetmenu(s.getSetmenu());
        h.setFoodIndex(3);
        FoodSetMenu fAdded3 = foodSetMenuDao.create(h);

        return s;
    }

    @RequestMapping(value = "/updateSetMenu", method = RequestMethod.GET)
    public SetMenu updateSetMenu(
            @RequestParam("id") int id,
            @RequestParam("bfast") int bfast,
            @RequestParam("lunch") int lunch,
            @RequestParam("dinner") int dinner) throws SQLException {

        Food breakfast = foodDao.findOne(bfast);
        Food lunchh = foodDao.findOne(lunch);
        Food dinnerr = foodDao.findOne(dinner);

        SetMenu setMenu = setMenuDao.findOne(id);
        setMenu.setSetmenu(id);
        setMenu.setTotal_cal(breakfast.getKal()+lunchh.getKal()+dinnerr.getKal());
        setMenu.setTotal_fat(breakfast.getFat()+lunchh.getFat()+dinnerr.getFat());
        setMenu.setTotal_carboh(breakfast.getCarboh()+lunchh.getCarboh()+dinnerr.getCarboh());
        setMenu.setTotal_protein(breakfast.getProtein()+lunchh.getProtein()+dinnerr.getProtein());



        List<FoodSetMenu> foodSetMenus = foodSetMenuDao.findBySetmenu(setMenu.getSetmenu());
        for (FoodSetMenu f : foodSetMenus
             ) {
            foodSetMenuDao.delete(f);
        }

        SetMenu s = setMenuDao.update(setMenu);

        FoodSetMenu f = new FoodSetMenu();
        f.setFoodId(breakfast.getFoodId());
        f.setSetmenu(s.getSetmenu());
        f.setFoodIndex(1);
        FoodSetMenu fAdded1 = foodSetMenuDao.create(f);

        FoodSetMenu g = new FoodSetMenu();
        g.setFoodId(lunchh.getFoodId());
        g.setSetmenu(s.getSetmenu());
        g.setFoodIndex(2);
        FoodSetMenu fAdded2 = foodSetMenuDao.create(g);

        FoodSetMenu h = new FoodSetMenu();
        h.setFoodId(dinnerr.getFoodId());
        h.setSetmenu(s.getSetmenu());
        h.setFoodIndex(3);
        FoodSetMenu fAdded3 = foodSetMenuDao.create(h);
        return s;
    }

    @RequestMapping(value = "/deleteSetMenu", method = RequestMethod.GET)
    public boolean deleteSetMenu(@RequestParam("id") int id) throws SQLException {
        SetMenu s = setMenuDao.findOne(id);
        List<FoodSetMenu> foodSetMenus = foodSetMenuDao.findBySetmenu(id);
        for (FoodSetMenu f : foodSetMenus
             ) {
            foodSetMenuDao.delete(f);
        }
        setMenuDao.delete(s);
        return true;
    }

}
