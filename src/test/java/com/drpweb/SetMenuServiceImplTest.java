package com.drpweb;

import com.drpweb.food_set_disease.FoodSetDisease;
import com.drpweb.food_set_disease.FoodSetDiseaseDao;
import com.drpweb.food_setmenu.FoodSetMenu;
import com.drpweb.food_setmenu.FoodSetMenuDao;
import com.drpweb.ingredient_on_food.IngredientOnFood;
import com.drpweb.ingredient_on_food.IngredientOnFoodDao;
import com.drpweb.ingredient_set_dislike.IngredientDislike;
import com.drpweb.ingredient_set_dislike.IngredientDislikeDao;
import com.drpweb.role.Role;
import com.drpweb.setmenu.SetMenu;
import com.drpweb.setmenu.SetMenuDao;
import com.drpweb.setmenu.SetMenuServiceImpl;
import com.drpweb.user.User;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ADMIN on 9/15/2016.
 */
public class SetMenuServiceImplTest {

    @Test
    public void getCantEatSetMenuByIngredient() throws Exception {


        User user = mock(User.class);
        Date dob = new Date("04/10/1995");
        Role role = new Role();
        role.setRoleName("member");

        Set<Role> roles = new HashSet<Role>();
        roles.add(role);

        when(user.getId()).thenReturn(1L);
        when(user.getUsername()).thenReturn("bbbbbb");
        when(user.getPassword()).thenReturn("123456");
        when(user.getEmail()).thenReturn("bo_zooza@hotmail.com");
        when(user.getName()).thenReturn("Wirunya");
        when(user.getLastName()).thenReturn("Pajcha");
        when(user.getGender()).thenReturn("female");
        when(user.getDob()).thenReturn(dob);
        when(user.getWeight()).thenReturn(48.0);
        when(user.getHeight()).thenReturn(163.0);
        when(user.getDuration()).thenReturn(5);
        when(user.getDiseaseId()).thenReturn((long) 7);

        IngredientDislikeDao ingredientDislikeDao = mock(IngredientDislikeDao.class);
        IngredientOnFoodDao ingredientOnFoodDao = mock(IngredientOnFoodDao.class);
        List<IngredientDislike> setDislike = ingredientDislikeDao.findByUserId(user.getId());
        List<IngredientOnFood> ingreOnFoodDislike = new ArrayList<>();
        for (IngredientDislike i: setDislike ) {
            for (IngredientOnFood j:ingredientOnFoodDao.findAll()) {
                if(i.getIngredientId().equals(j.getIngredientId())){

                    ingreOnFoodDislike.add(ingredientOnFoodDao.findOne(j.getId()));

                }
            }

        }

        List<Integer> setMenuDislike = new ArrayList<>();
        FoodSetMenuDao foodSetMenuDao = mock(FoodSetMenuDao.class);

        for (IngredientOnFood i : ingreOnFoodDislike) {
            for (FoodSetMenu f : foodSetMenuDao.findAll()) {
                if(i.getFoodIndex()==(f.getFoodId())){
                    setMenuDislike.add(f.getSetmenu());
                }
            }
        }

        List<Integer> setMenuNotUse = new ArrayList<>();
        setMenuNotUse.add(22);
        setMenuNotUse.add(18);
        setMenuNotUse.add(42);
        setMenuNotUse.add(12);

        for (int s : setMenuDislike
             ) {
            for (int i : setMenuNotUse
                 ) {
                assertEquals(s,i);
            }

        }




    }


    @Test
    public void getSetMenuByDisease() throws Exception {
        User user = mock(User.class);
        Date dob = new Date("04/10/1995");
        Role role = new Role();
        role.setRoleName("member");

        Set<Role> roles = new HashSet<Role>();
        roles.add(role);

        when(user.getId()).thenReturn(1L);
        when(user.getUsername()).thenReturn("bbbbbb");
        when(user.getPassword()).thenReturn("123456");
        when(user.getEmail()).thenReturn("bo_zooza@hotmail.com");
        when(user.getName()).thenReturn("Wirunya");
        when(user.getLastName()).thenReturn("Pajcha");
        when(user.getGender()).thenReturn("female");
        when(user.getDob()).thenReturn(dob);
        when(user.getWeight()).thenReturn(48.0);
        when(user.getHeight()).thenReturn(163.0);
        when(user.getDuration()).thenReturn(5);
        when(user.getDiseaseId()).thenReturn((long) 7);


        FoodSetDiseaseDao foodSetDiseaseDao = mock(FoodSetDiseaseDao.class);
        List<FoodSetDisease> foodSetDiseases = foodSetDiseaseDao.findByDisease(user.getDiseaseId());


        List<Integer> setMenuCantEatId = new ArrayList<>();
        List<SetMenu> setFromDisease;
        List<Integer> setCantEatFromIngre;
        List<SetMenu> setToEat = new ArrayList<>();
        List<Integer> setCantEatFromTwoCriteria = new ArrayList<>();
        List<SetMenu> setCanEatFromTwoCriteria;

        for (FoodSetDisease f : foodSetDiseases) {
            setMenuCantEatId.add(f.getSetmenu());
        }

        SetMenuDao setMenuDao = mock(SetMenuDao.class);
        IngredientDislikeDao ingredientDislikeDao = mock(IngredientDislikeDao.class);
        setFromDisease = setMenuDao.findBySetmenuNotIn(setMenuCantEatId);
        List<IngredientDislike> useridslike = ingredientDislikeDao.findByUserId(user.getId());

        SetMenuServiceImpl setMenuService = mock(SetMenuServiceImpl.class);
        setCantEatFromIngre = setMenuService.getCantEatSetMenuByIngredient(user);

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

        List<Integer> setMenuNotUse = new ArrayList<>();
        setMenuNotUse.add(22);
        setMenuNotUse.add(18);
        setMenuNotUse.add(42);
        setMenuNotUse.add(12);

        setCanEatFromTwoCriteria = setMenuDao.findBySetmenuNotIn(setCantEatFromTwoCriteria);
        for (SetMenu i : setCanEatFromTwoCriteria) {
            assertNotEquals(i.getSetmenu(),setMenuNotUse);
        }



    }

    @Test
    public void getSetMenu() throws Exception {
        User user = mock(User.class);
        Date dob = new Date("04/10/1995");
        Role role = new Role();
        role.setRoleName("member");

        Set<Role> roles = new HashSet<Role>();
        roles.add(role);

        when(user.getId()).thenReturn(1L);
        when(user.getUsername()).thenReturn("bowbow");
        when(user.getPassword()).thenReturn("123456");
        when(user.getEmail()).thenReturn("bo_zooza@hotmail.com");
        when(user.getName()).thenReturn("Wirunya");
        when(user.getLastName()).thenReturn("Pajcha");
        when(user.getGender()).thenReturn("female");
        when(user.getDob()).thenReturn(dob);
        when(user.getWeight()).thenReturn(55.0);
        when(user.getHeight()).thenReturn(165.0);
        when(user.getDuration()).thenReturn(5);
        when(user.getDiseaseId()).thenReturn(null);

        IngredientDislikeDao ingredientDislikeDao = mock(IngredientDislikeDao.class);
        List<IngredientDislike> useridslike = ingredientDislikeDao.findByUserId(user.getId());

        SetMenuServiceImpl setMenuService = mock(SetMenuServiceImpl.class);
        List<Integer> setMenuDislike = setMenuService.getCantEatSetMenuByIngredient(user);

        SetMenuDao setMenuDao = mock(SetMenuDao.class);
        List<SetMenu> setMenus = setMenuDao.findBySetmenuNotIn(setMenuDislike);

        List<Integer> setMenuNotUse = new ArrayList<>();
        setMenuNotUse.add(22);
        setMenuNotUse.add(18);
        setMenuNotUse.add(42);
        setMenuNotUse.add(12);

        for (SetMenu s : setMenus) {
            assertNotEquals(s.getSetmenu(),setMenuNotUse);
        }
    }

}