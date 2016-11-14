package com.drpweb.ingredient;

import com.drpweb.ingredient_set_dislike.IngredientDislike;
import com.drpweb.ingredient_set_dislike.IngredientDislikeDao;
import com.drpweb.user.User;
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
public class IngredientController {
    @Autowired
    IngredientDao ingredientDao;
    @Autowired
    UserService userService;
    @Autowired
    IngredientDislikeDao ingredientDisDao;

    @CrossOrigin(origins = "http://localhost:3000")
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

    @RequestMapping(value = "/selectIngredient",method = RequestMethod.GET)
    public Boolean selectIngredient(@RequestParam("ingredient")String [] ingredients,@RequestParam("name")String username) throws SQLException {
        IngredientDislike ingredientDislike;
        Ingredient ingredient;

        User user = userService.findByUserName(username);
        for (String ingre : ingredients) {
            ingredient = ingredientDao.findByIngredientName(ingre);

            ingredientDislike = new IngredientDislike();
            ingredientDislike.setUserId(user.getId());
            ingredientDislike.setIngredientId(ingredient.getId());

            ingredientDisDao.create(ingredientDislike);
        }


        return true;
    }
    @RequestMapping(value = "/deselectIngredient",method = RequestMethod.GET)
    public Boolean deselectIngredient(@RequestParam("ingredient")String [] ingredients,@RequestParam("name")String username) throws SQLException {
        User user = userService.findByUserName(username);
        List<IngredientDislike> userDisliked = ingredientDisDao.findByUserId(user.getId());
        List<Ingredient> ingredientToDeSelect = new ArrayList<>();

        for (String in : ingredients) {
            ingredientToDeSelect.add(ingredientDao.findByIngredientName(in));
        }

        for (Ingredient i : ingredientToDeSelect) {
            for (IngredientDislike d : userDisliked) {
                if(d.getIngredientId().equals(i.getId())) {
                    ingredientDisDao.delete(d);
                    //ingredientToDelete.add(ingredientDisDao.findByIngredientId(i.getId()));
                }
            }

        }





        return true;
    }
}