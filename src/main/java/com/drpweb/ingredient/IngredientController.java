package com.drpweb.ingredient;

import com.drpweb.daily_meal.DailyMealService;
import com.drpweb.diet_plan.DietPlan;
import com.drpweb.diet_plan.DietPlanDao;
import com.drpweb.diet_plan.DietPlanService;
import com.drpweb.ingredient_set_dislike.IngredientDislike;
import com.drpweb.ingredient_set_dislike.IngredientDislikeDao;
import com.drpweb.role.Role;
import com.drpweb.user.User;
import com.drpweb.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by ADMIN on 11/13/2016.
 */
@CrossOrigin(origins = "*")
@RestController
public class IngredientController {
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

    //@CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/getIngredientsToSelect",method = RequestMethod.GET)
    public List<Ingredient> getIngredients(@RequestParam("name")String username) throws SQLException {
        User user = userService.findByUserName(username);
        List<IngredientDislike> ingredientDislikes = ingredientDisDao.findByUserId(user.getId());
        List<Long> idTogetIngredients = new ArrayList<>();

        for (IngredientDislike id : ingredientDislikes) {
            idTogetIngredients.add(id.getIngredientId());
        }


        List<Ingredient> ingredients;
        if(ingredientDislikes.size()!=0) {
            System.out.println("Disliked ingredients");
            ingredients = ingredientDao.findByIdNotIn(idTogetIngredients);

            for (Ingredient i : ingredients) {
                System.out.println("dislike ingredient" + i.getIngredientName());
            }
        }else {
            System.out.println("No dislike ingredients");
            ingredients = ingredientDao.findAll();
        }
        System.out.println("Number of ingredient to select" + ingredients);
        return ingredients;
    }

    @RequestMapping(value = "/createIngredient",method = RequestMethod.GET)
    public Ingredient createIngredient(@RequestParam("name")String name,
                           @RequestParam("category")int category) throws SQLException {

        Ingredient i = new Ingredient();
        i.setIngredientName(name);
        i.setCategoriesId(category);

        return ingredientDao.create(i);
    }

    @RequestMapping(value = "/updateIngredient",method = RequestMethod.GET)
    public Ingredient updateIngredient(
                                 @RequestParam("id")Long id,
                                 @RequestParam("name")String name,
                                 @RequestParam("category")int category) throws SQLException {

        Ingredient i = ingredientDao.findOne(id);
        i.setIngredientName(name);
        i.setCategoriesId(category);

        return ingredientDao.update(i);
    }

    @RequestMapping(value = "/deleteIngredient",method = RequestMethod.GET)
    public boolean deleteIngredient(@RequestParam("id")Long id) throws SQLException {
        Ingredient infredient = ingredientDao.findOne(id);
       ingredientDao.delete(infredient);
        return true;
    }
    @RequestMapping(value = "/getAllIngredient",method = RequestMethod.GET)
    public List<Ingredient> getAllIngredient() throws SQLException {

        return ingredientDao.findAll();
    }

    @RequestMapping(value = "/updatePlanWithIngredient",method = RequestMethod.GET)
    public boolean updatePlanWithIngredient(@RequestParam("name")String username, @RequestParam("ingredients")String[] ingredientsName) throws SQLException {
        User user = userService.findByUserName(username);
        List<IngredientDislike> ingreDis = ingredientDisDao.findByUserId(user.getId());
        if(ingreDis!=null) {
            for (IngredientDislike id : ingreDis
                    ) {
                ingredientDisDao.delete(id);
            }
        }
        List<Ingredient> ingredientToSet = new ArrayList<>();
        for (String i : ingredientsName
             ) {
            ingredientToSet.add(ingredientDao.findByIngredientName(i));
        }
        for (Ingredient in : ingredientToSet
             ) {
            IngredientDislike ingre = new IngredientDislike();
            ingre.setUserId(user.getId());
            ingre.setIngredientId(in.getId());
            IngredientDislike added = ingredientDisDao.create(ingre);
        }

        DietPlan dietPlan = dietPlanService.findByUserId(user.getId());

        LocalDate today = LocalDate.now();
        Date startDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dietPlan.setStartDate(startDate);

        LocalDate end = today.plusDays(user.getDuration() - 1);
        Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dietPlan.setEndDate(endDate);
        dietPlanDao.update(dietPlan);

        dailyMealService.delete(dietPlan.getDietPlanId());
        Set<Role> roles = user.getRoles();
        for (Iterator<Role> it = roles.iterator(); it.hasNext(); ) {
            Role role = it.next();
            if (role.getRoleName().equals("member")) {
                dietPlanService.createPlan(user.getUsername());
            }
            else{
                dietPlanService.createPatientPlan(user.getUsername());
            }

        }
        return true;
    }

    @RequestMapping(value = "/updatePlanWithOutIngredient",method = RequestMethod.GET)
    public boolean updatePlanWithIngredient(@RequestParam("name")String username) throws SQLException {
        User user = userService.findByUserName(username);
        List<IngredientDislike> ingreDis = ingredientDisDao.findByUserId(user.getId());
        if(ingreDis!=null) {
            for (IngredientDislike id : ingreDis
                    ) {
                ingredientDisDao.delete(id);
            }
        }

        DietPlan dietPlan = dietPlanService.findByUserId(user.getId());

        LocalDate today = LocalDate.now();
        Date startDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dietPlan.setStartDate(startDate);

        LocalDate end = today.plusDays(user.getDuration() - 1);
        Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dietPlan.setEndDate(endDate);
        dietPlanDao.update(dietPlan);

        dailyMealService.delete(dietPlan.getDietPlanId());
        Set<Role> roles = user.getRoles();
        for (Iterator<Role> it = roles.iterator(); it.hasNext(); ) {
            Role role = it.next();
            if (role.getRoleName().equals("member")) {
                dietPlanService.createPlan(user.getUsername());
            }
            else{
                dietPlanService.createPatientPlan(user.getUsername());
            }

        }
        return true;
    }

    //Define unwanted ingredient
    @RequestMapping(value = "/getSelectedIngredients",method = RequestMethod.GET)
    public List<String> getSelectedIngredients(@RequestParam("name")String username) throws SQLException {
        User user = userService.findByUserName(username);
        List<IngredientDislike> ingredientDislikes = ingredientDisDao.findByUserId(user.getId());
        List<String> ingredients = new ArrayList<>();
        for (IngredientDislike ingreDisk : ingredientDislikes) {
            ingredients.add(ingredientDao.findOne(ingreDisk.getIngredientId()).getIngredientName());
        }
        return ingredients;
    }


}
