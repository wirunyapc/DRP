package com.drpweb.food;

import com.drpweb.category.Category;
import com.drpweb.category.CategoryDao;
import com.drpweb.daily_meal.DailyMeal;
import com.drpweb.daily_meal.DailyMealDao;
import com.drpweb.diet_plan.DietPlan;
import com.drpweb.diet_plan.DietPlanService;
import com.drpweb.food_setmenu.FoodSetMenuDao;
import com.drpweb.ingredient.Ingredient;
import com.drpweb.ingredient.IngredientDao;
import com.drpweb.ingredient_on_food.IngredientOnFood;
import com.drpweb.ingredient_on_food.IngredientOnFoodDao;
import com.drpweb.setmenu.SetMenuDao;
import com.drpweb.user.User;
import com.drpweb.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 10/13/2016.
 */
@CrossOrigin
@RestController
public class FoodController {
    @Autowired
    FoodDao foodDao;
    @Autowired
    DietPlanService dietPlanService;
    @Autowired
    DailyMealDao dailyMealdao;
    @Autowired
    SetMenuDao setMenuDao;
    @Autowired
    FoodSetMenuDao foodSetMenuDao;
    @Autowired
    UserDao userDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    IngredientOnFoodDao ingredientOnFoodDao;
    @Autowired
    IngredientDao ingredientDao;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/getTotalDietCal",method = RequestMethod.GET)
    public int DietCal(@RequestParam("date")String date,@RequestParam("name")String name) throws SQLException {
        System.out.println("date to cal total diet "+date);
        int totalDietCal = 0;

        User user = userDao.findByUsername(name);
        DietPlan userDietPlan = dietPlanService.findByUserId(user.getId());
        List<DailyMeal> dailymeals = dailyMealdao.findByDietPlanId(userDietPlan.getDietPlanId());



        for (DailyMeal daily : dailymeals) {
            if(daily.getDate().toString().equals(date+" 00:00:00.0")){
                System.out.println("Daily get date " + daily.getDate().toString()+"Date from front "+ date+" 00:00:00.0");
//                List<FoodSetMenu> foodSetMenus = foodSetMenuDao.findBySetmenu(daily.getSetMenu_id());
//
//                for (FoodSetMenu f: foodSetMenus) {
//                    totalDietCal+=foodDao.findOne(f.getFoodId()).getKal();
//                }
                totalDietCal+=setMenuDao.findOne(daily.getSetMenu_id()).getTotal_cal();
            }
        }



        System.out.println("Total cal" + totalDietCal);
        return  totalDietCal;

    }

    @RequestMapping(value = "/getFoodToManage",method = RequestMethod.GET)
    public List<Food> getFoodToManage() throws SQLException {

        return foodDao.findAll();
    }
    @RequestMapping(value = "/getAllCategory",method = RequestMethod.GET)
    public List<Category> getAllCategory() throws SQLException {

        return categoryDao.findAll();
    }
    @RequestMapping(value = "/getSelectedCategory",method = RequestMethod.GET)
    public String getSelectedCategory(@RequestParam("name")String name) throws SQLException {
        Food food = foodDao.findByFoodNameEng(name);
        System.out.println("Food for category "+food.getFoodNameEng()+" "+food.getCategoriesId());
        Category category = categoryDao.findOne(food.getCategoriesId());
        return "["+"\""+category.getCategoryName()+"\""+"]";
    }
    @RequestMapping(value = "/getSelectedIngreByFood",method = RequestMethod.GET)
    public List<String> getSelectedIngreByFood(@RequestParam("id")int id) throws SQLException {
        List<IngredientOnFood> ingredientOnFoods = ingredientOnFoodDao.findByFoodIndex(id);
        List<Ingredient> ingredients = new ArrayList<>();

        for (IngredientOnFood in : ingredientOnFoods
             ) {
            ingredients.add(ingredientDao.findById(in.getIngredientId()));
        }


        List<String> ingreName = new ArrayList<>();
        for (Ingredient i : ingredients) {
            String ingre = "";
            ingre += i.getIngredientName();
            ingreName.add(ingre);
        }
        return ingreName;
    }

    @RequestMapping(value = "/createFoodWithIngre",method = RequestMethod.GET)
    public Food createFoodWithIngre(@RequestParam("name")String name,
                                            @RequestParam("cal")int cal,
                                            @RequestParam("fat")int fat,
                                            @RequestParam("carboh")int carboh,
                                            @RequestParam("prote")int protein,
                                            @RequestParam("category")int category,
                                            @RequestParam("amount")int amount,
                                            @RequestParam("unit")String unit,
                                            @RequestParam("ingredients")String[] ingredientsName) throws SQLException {
        Food f = new Food();
        f.setFoodNameEng(name);
        f.setKal(cal);
        f.setFat(fat);
        f.setCarboh(carboh);
        f.setProtein(protein);
        f.setCategoriesId(category);
        f.setAmount(amount);
        f.setUnit(unit);


        Food food = foodDao.create(f);

        findIngredientFromName(food, ingredientsName);

        return food;
    }
    @RequestMapping(value = "/createFood",method = RequestMethod.GET)
    public Food createFood(@RequestParam("name")String name,
                                 @RequestParam("cal")int cal,
                                 @RequestParam("fat")int fat,
                                 @RequestParam("carboh")int carboh,
                                 @RequestParam("prote")int protein,
                                 @RequestParam("category")int category,
                                 @RequestParam("amount")int amount,
                                 @RequestParam("unit")String unit ) throws SQLException {
        Food f = new Food();
        f.setFoodNameEng(name);
        f.setKal(cal);
        f.setFat(fat);
        f.setCarboh(carboh);
        f.setProtein(protein);
        f.setCategoriesId(category);
        f.setAmount(amount);
        f.setUnit(unit);

        return foodDao.create(f);
    }

    public void findIngredientFromName(Food f, String[] ingredients){
        ArrayList<Ingredient> ingreFound = new ArrayList<>();

        for (String in : ingredients ) {
            ingreFound.add(ingredientDao.findByIngredientName(in));
        }

        for (Ingredient ingre : ingreFound) {
            IngredientOnFood i = new IngredientOnFood();
            i.setIngredientId(ingre.getId());
            i.setFoodIndex(f.getFoodId());
            IngredientOnFood added = ingredientOnFoodDao.create(i);
        }

    }

    @RequestMapping(value = "/updateFoodWithIngredient",method = RequestMethod.GET)
    public Food updateFoodWithIngredient(@RequestParam("id")int id,
                                         @RequestParam("name")String name,
                                         @RequestParam("cal")int cal,
                                         @RequestParam("fat")int fat,
                                         @RequestParam("carboh")int carboh,
                                         @RequestParam("prote")int protein,
                                         @RequestParam("category")String category,
                                         @RequestParam("amount")int amount,
                                         @RequestParam("unit")String unit,
                                         @RequestParam("ingredients")String[] ingredientsName) throws SQLException {

        Food f = foodDao.findOne(id);
        f.setFoodNameEng(name);
        f.setKal(cal);
        f.setFat(fat);
        f.setCarboh(carboh);
        f.setProtein(protein);
        f.setCategoriesId(categoryDao.findByCategoryName(category).getCategoryId());
        f.setAmount(amount);
        f.setUnit(unit);


        Food food = foodDao.update(f);


        List<IngredientOnFood> ingreOnFood = ingredientOnFoodDao.findByFoodIndex(id);
        for (IngredientOnFood in: ingreOnFood) {
            ingredientOnFoodDao.delete(in);
        }
        findIngredientFromName(food, ingredientsName);


        return food;
    }
    @RequestMapping(value = "/updateFood",method = RequestMethod.GET)
    public Food updateFood(@RequestParam("id")int id,
                           @RequestParam("name")String name,
                           @RequestParam("cal")int cal,
                           @RequestParam("fat")int fat,
                           @RequestParam("carboh")int carboh,
                           @RequestParam("prote")int protein,
                           @RequestParam("category")String category,
                           @RequestParam("amount")int amount,
                           @RequestParam("unit")String unit ) throws SQLException {

        Food f = foodDao.findOne(id);
        f.setFoodNameEng(name);
        f.setKal(cal);
        f.setFat(fat);
        f.setCarboh(carboh);
        f.setProtein(protein);
        f.setCategoriesId(categoryDao.findByCategoryName(category).getCategoryId());
        f.setAmount(amount);
        f.setUnit(unit);

        List<IngredientOnFood> ingreOnFood = ingredientOnFoodDao.findByFoodIndex(id);
        for (IngredientOnFood in: ingreOnFood) {
            ingredientOnFoodDao.delete(in);
        }
        return foodDao.update(f);
    }

    @RequestMapping(value = "/deleteFood",method = RequestMethod.GET)
    public boolean deleteFood(@RequestParam("id")int id) throws SQLException {
        Food food = foodDao.findOne(id);
        List<IngredientOnFood> ingredientOnFoods = ingredientOnFoodDao.findByFoodIndex(id);
        for (IngredientOnFood i : ingredientOnFoods
             ) {
            ingredientOnFoodDao.delete(i);
        }
        foodDao.delete(food);
        return true;
    }
}
