package com.drpweb;

import choco.kernel.solver.Solver;
import com.drpweb.daily_meal.DailyMeal;
import com.drpweb.daily_meal.DailyMealDao;
import com.drpweb.diet_plan.DietPlan;
import com.drpweb.diet_plan.DietPlanService;
import com.drpweb.diet_plan.DietPlanServiceImpl;
import com.drpweb.disease.Disease;
import com.drpweb.disease.DiseaseDao;
import com.drpweb.setmenu.SetMenu;
import com.drpweb.user.User;
import com.drpweb.util.DailyDiet;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ADMIN on 9/15/2016.
 */
public class DietPlanServiceImplTest {

    @Test
    public void createPlan() throws Exception {

        User user = mock(User.class);

        Date dob = new Date("04/10/1995");
        when(user.getId()).thenReturn(1L);
        when(user.getUsername()).thenReturn("Wirunya");
        when(user.getPassword()).thenReturn("123456");
        when(user.getEmail()).thenReturn("bo_zooza@hotmail.com");
        when(user.getName()).thenReturn("Wirunya");
        when(user.getLastName()).thenReturn("Pajcha");
        when(user.getGender()).thenReturn("female");
        when(user.getDob()).thenReturn(dob);
        when(user.getWeight()).thenReturn(48.0);
        when(user.getHeight()).thenReturn(163.0);
        when(user.getDuration()).thenReturn(7);

        SetMenu setMenu = mock(SetMenu.class);
        int[] setMenuId = new int[2];
        int[] totalCal = new int[2];
        int[] totalFat = new int[2];
        int[] totalCarboh = new int[2];
        int[] totalProtein = new int[2];

        ArrayList<int[]> totalCals = new ArrayList<>();
        ArrayList<int[]> totalFats = new ArrayList<>();
        ArrayList<int[]> totalCarbohs = new ArrayList<>();
        ArrayList<int[]> totalProteins = new ArrayList<>();


        when(setMenu.getArr_setMenu_id()).thenReturn(setMenuId);
        when(setMenu.getArr_total_cal()).thenReturn(totalCal);
        when(setMenu.getArr_total_fat()).thenReturn(totalFat);
        when(setMenu.getArr_total_carboh()).thenReturn(totalCarboh);
        when(setMenu.getArr_total_protein()).thenReturn(totalProtein);

        when(setMenu.getTotal_cals()).thenReturn(totalCals);
        when(setMenu.getTotal_fats()).thenReturn(totalFats);
        when(setMenu.getTotal_carbohs()).thenReturn(totalCarbohs);
        when(setMenu.getTotal_proteins()).thenReturn(totalProteins);


        DailyDiet dailyDiet = mock(DailyDiet.class);
        Solver solver = mock(Solver.class);
        when(dailyDiet.solve(2,3,
                setMenu.getArr_setMenu_id(), setMenu.getArr_total_cal(),setMenu.getArr_total_fat(), setMenu.getArr_total_carboh(), setMenu.getArr_total_protein(),
                setMenu.getTotal_cals(), setMenu.getTotal_fats(), setMenu.getTotal_carbohs(), setMenu.getTotal_proteins(),0,0,0,0,1233,"member")).thenReturn(solver);
        String result = "[[["+"id_0_0:1,"+"kal_0_0:380,"+"fat_0_0:18,"+"pro_0_0:20,"+"carbo_0_0:45"+"],"+"["+"id_0_1:2,"+"kal_0_1:590,"+"fat_0_1:30,"+"pro_0_1:10,"+"carbo_0_1:20"+"],"+"["+"id_0_2:3,"+"kal_0_2:650,"+"fat_0_2:10,"+"pro_0_2:20,"+"carbo_0_2:70"+"]]]";
        when(dailyDiet.toJson()).thenReturn(result);

        assertEquals(result, dailyDiet.toJson());

    }

    @Test
    public void createPatientPlan() throws Exception {

        User user = mock(User.class);

        Date dob = new Date("08/05/1997");
        when(user.getId()).thenReturn(1L);
        when(user.getUsername()).thenReturn("krissn");
        when(user.getPassword()).thenReturn("222222");
        when(user.getEmail()).thenReturn("kris@k.com");
        when(user.getName()).thenReturn("Kritika");
        when(user.getLastName()).thenReturn("Soni");
        when(user.getGender()).thenReturn("female");
        when(user.getDob()).thenReturn(dob);
        when(user.getWeight()).thenReturn(45.0);
        when(user.getHeight()).thenReturn(180.0);
        when(user.getDuration()).thenReturn(1);
        when(user.getDiseaseId()).thenReturn(1L);

        SetMenu setMenu = mock(SetMenu.class);
        int[] setMenuId = new int[2];
        int[] totalCal = new int[2];
        int[] totalFat = new int[2];
        int[] totalCarboh = new int[2];
        int[] totalProtein = new int[2];

        ArrayList<int[]> totalCals = new ArrayList<>();
        ArrayList<int[]> totalFats = new ArrayList<>();
        ArrayList<int[]> totalCarbohs = new ArrayList<>();
        ArrayList<int[]> totalProteins = new ArrayList<>();


        when(setMenu.getArr_setMenu_id()).thenReturn(setMenuId);
        when(setMenu.getArr_total_cal()).thenReturn(totalCal);
        when(setMenu.getArr_total_fat()).thenReturn(totalFat);
        when(setMenu.getArr_total_carboh()).thenReturn(totalCarboh);
        when(setMenu.getArr_total_protein()).thenReturn(totalProtein);

        when(setMenu.getTotal_cals()).thenReturn(totalCals);
        when(setMenu.getTotal_fats()).thenReturn(totalFats);
        when(setMenu.getTotal_carbohs()).thenReturn(totalCarbohs);
        when(setMenu.getTotal_proteins()).thenReturn(totalProteins);


        Disease userDisease = mock(Disease.class);
        when(userDisease.getId()).thenReturn(1L);
        when(userDisease.getDiseaseName()).thenReturn("obesity");
        when(userDisease.getKcal()).thenReturn((double) 2000);
        when(userDisease.getFat()).thenReturn((double) 60);
        when(userDisease.getCarboh()).thenReturn((double) 165);
        when(userDisease.getProt()).thenReturn((double) 33);

        DiseaseDao diseaseDao = mock(DiseaseDao.class);
        when(diseaseDao.findOne(user.getDiseaseId())).thenReturn(userDisease);

        DailyDiet dailyDiet = mock(DailyDiet.class);
        Solver solver = mock(Solver.class);
        when(dailyDiet.solve(2,3,
                setMenu.getArr_setMenu_id(), setMenu.getArr_total_cal(),setMenu.getArr_total_fat(), setMenu.getArr_total_carboh(), setMenu.getArr_total_protein(),
                setMenu.getTotal_cals(), setMenu.getTotal_fats(), setMenu.getTotal_carbohs(), setMenu.getTotal_proteins(),(int)userDisease.getKcal(),(int)userDisease.getFat(),(int)userDisease.getProt(),(int)userDisease.getCarboh(),1319,"patient")).thenReturn(solver);

        String result = "[[["+"id_0_0:1,"+"kal_0_0:380,"+"fat_0_0:18,"+"pro_0_0:20,"+"carbo_0_0:45"+"],"+"["+"id_0_1:2,"+"kal_0_1:590,"+"fat_0_1:30,"+"pro_0_1:10,"+"carbo_0_1:20"+"],"+"["+"id_0_2:3,"+"kal_0_2:650,"+"fat_0_2:10,"+"pro_0_2:20,"+"carbo_0_2:70"+"]]]";
        when(dailyDiet.toJson()).thenReturn(result);

        assertEquals("[[["+"id_0_0:4,"+"kal_0_0:65,"+"fat_0_0:11,"+"pro_0_0:17,"+"carbo_0_0:12"+"],"+
                "["+"id_0_1:5,"+"kal_0_1:380,"+"fat_0_1:18,"+"pro_0_1:20,"+"carbo_0_1:45"+"],"+"["+
                "id_0_2:6,"+"kal_0_2:520,"+"fat_0_2:21,"+"pro_0_2:2,"+"carbo_0_2:41"+"]]]", dailyDiet.toJson());
    }
    @Test
    public void parseDate() throws ParseException {
        Date date = mock(Date.class);
        when(date.toString()).thenReturn("Wed Sep 28 00:00:00 ICT 2016");

        DietPlanService dietPlanService = mock(DietPlanService.class);
        when(dietPlanService.parseDate("2016/09/28")).thenReturn(date);

        assertEquals(dietPlanService.parseDate("2016/09/28").toString(),date.toString());


    }

    @Test
    public void savePlan(){


        User user = mock(User.class);
        Date dob = new Date("04/10/1995");
        when(user.getId()).thenReturn(1L);
        when(user.getUsername()).thenReturn("Wirunya");
        when(user.getPassword()).thenReturn("123456");
        when(user.getEmail()).thenReturn("bo_zooza@hotmail.com");
        when(user.getName()).thenReturn("Wirunya");
        when(user.getLastName()).thenReturn("Pajcha");
        when(user.getGender()).thenReturn("female");
        when(user.getDob()).thenReturn(dob);
        when(user.getWeight()).thenReturn(48.0);
        when(user.getHeight()).thenReturn(163.0);
        when(user.getDuration()).thenReturn(7);
        when(user.getDiseaseId()).thenReturn(null);

        //
        DietPlan dietPlan = mock(DietPlan.class);
        when(dietPlan.getDietPlanId()).thenReturn(1L);
        when(dietPlan.getUserId()).thenReturn(1L);

        DietPlanService dietPlanService = mock(DietPlanService.class);
        when(dietPlanService.findByUserId(user.getId())).thenReturn(dietPlan);

        DailyMeal dailyMeal = mock(DailyMeal.class);
        when(dailyMeal.getDietPlanId()).thenReturn(1L);

        DailyMealDao dailyMealDao = mock(DailyMealDao.class);
        assertNotNull(dailyMealDao.findByDietPlanId(dietPlan.getDietPlanId()));


    }

    @Test
    public void getBmr() throws SQLException {
        DietPlanServiceImpl dietPlanServiceImpl = new DietPlanServiceImpl();
        assertEquals(dietPlanServiceImpl.getBmr(48,163,21,"female"),1232);


    }


}