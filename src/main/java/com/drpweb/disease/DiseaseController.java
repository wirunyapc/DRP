package com.drpweb.disease;

import com.drpweb.diet_plan.DietPlanController;
import com.drpweb.food.Food;
import com.drpweb.food.FoodDao;
import com.drpweb.food_set_disease.FoodSetDisease;
import com.drpweb.food_set_disease.FoodSetDiseaseDao;
import com.drpweb.food_setmenu.FoodSetMenu;
import com.drpweb.food_setmenu.FoodSetMenuDao;
import com.drpweb.setmenu.SetMenu;
import com.drpweb.setmenu.SetMenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12/18/2016.
 */@CrossOrigin
@RestController
public class DiseaseController {
    @Autowired
    DiseaseDao diseaseDao;
    @Autowired
    SetMenuDao setMenuDao;
    @Autowired
    FoodSetMenuDao foodSetMenuDao;
    @Autowired
    FoodDao foodDao;
    @Autowired
    FoodSetDiseaseDao foodSetDiseasedao;
    @Autowired
    DietPlanController dietPlanController;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/createDiseaseWithSetMenu",method = RequestMethod.GET)
    public Disease createDiseaseWithSetMenu(@RequestParam("name")String name,
                                 @RequestParam("cal")int cal,
                                 @RequestParam("fat")int fat,
                                 @RequestParam("carboh")int carboh,
                                 @RequestParam("prot")int protein,
                                 @RequestParam("setMenu")String[] setMenuName) throws SQLException {
            Disease d = new Disease();
            d.setDiseaseName(name);
            d.setKcal(cal);
            d.setFat(fat);
            d.setCarboh(carboh);
            d.setProt(protein);
            Disease disease = diseaseDao.create(d);

        findSetMenuFromName(disease, setMenuName);

        return disease;
    }
    @RequestMapping(value = "/createDisease",method = RequestMethod.GET)
    public Disease createDisease(@RequestParam("name")String name,
                                 @RequestParam("cal")int cal,
                                 @RequestParam("fat")int fat,
                                 @RequestParam("carboh")int carboh,
                                 @RequestParam("prot")int protein) throws SQLException {
        Disease d = new Disease();
        d.setDiseaseName(name);
        d.setKcal(cal);
        d.setFat(fat);
        d.setCarboh(carboh);
        d.setProt(protein);

        return diseaseDao.create(d);
    }

    public void findSetMenuFromName(Disease d, String[] setMenuName){
        ArrayList<SetMenu> setFound = new ArrayList<>();

        for (String setMenu : setMenuName) {
            System.out.println("setMenu before split " + setMenu);
            String[] parts = setMenu.split("\\|");
            String part1 = parts[0];
            String part2 = parts[1];
            String part3 = parts[2];
            System.out.println("splited into parts"+part1+" "+part2+" "+part3);
            Food breakfast = foodDao.findByFoodNameEng(part1);
            Food lunch = foodDao.findByFoodNameEng(part2);
            Food dinner = foodDao.findByFoodNameEng(part3);
            System.out.print("Set menu splited " + breakfast.getFoodNameEng()+ " "+lunch.getFoodNameEng() + " "+dinner.getFoodNameEng());


            for (SetMenu s : setMenuDao.findAll()) {
                List<FoodSetMenu> foodSetMenus = foodSetMenuDao.findBySetmenu(s.getSetmenu());
                int foundCount=0;
                for (FoodSetMenu f : foodSetMenus) {
                    if (f.getFoodIndex() == 1 && f.getFoodId() == breakfast.getFoodId()) {
                        System.out.println("Found breakfast!");
                        foundCount++;
                    }else if(f.getFoodIndex() == 2 && f.getFoodId() == lunch.getFoodId()){
                        System.out.println("Found lunch!");
                        foundCount++;
                    }else if(f.getFoodIndex() == 3 && f.getFoodId() == dinner.getFoodId()){
                        System.out.println("Found dinner!");
                        foundCount++;
                    }else{
                        System.out.print("Error!");
                    }

                }
                if (foundCount==3){
                    System.out.println("Set found" + s.getSetmenu());
                    setFound.add(s);
                }


            }

        }
        for (SetMenu setMenu : setFound) {
            FoodSetDisease f = new FoodSetDisease();
            f.setDisease(d.getId());
            f.setSetmenu(setMenu.getSetmenu());
            FoodSetDisease added = foodSetDiseasedao.create(f);
        }

    }

    @RequestMapping(value = "/updateDiseaseWithSetMenu",method = RequestMethod.GET)
    public Disease updateDiseaseWithSetMenu(@RequestParam("id")Long id,
                                            @RequestParam("name")String name,
                                            @RequestParam("cal")int cal,
                                            @RequestParam("fat")int fat,
                                            @RequestParam("carboh")int carboh,
                                            @RequestParam("prot")int protein,
                                            @RequestParam("setMenu")String[] setMenuName) throws SQLException {
        Disease d = diseaseDao.findOne(id);
        d.setDiseaseName(name);
        d.setKcal(cal);
        d.setFat(fat);
        d.setCarboh(carboh);
        d.setProt(protein);
        Disease disease = diseaseDao.update(d);


        List<FoodSetDisease> foodSetDisease = foodSetDiseasedao.findByDisease(disease.getId());
        for (FoodSetDisease fs: foodSetDisease) {
            foodSetDiseasedao.delete(fs);
        }
        findSetMenuFromName(disease, setMenuName);


        return disease;
    }
    @RequestMapping(value = "/updateDisease",method = RequestMethod.GET)
    public Disease updateDisease(@RequestParam("id")Long id,
                                 @RequestParam("name")String name,
                                 @RequestParam("cal")int cal,
                                 @RequestParam("fat")int fat,
                                 @RequestParam("carboh")int carboh,
                                 @RequestParam("prot")int protein) throws SQLException {
        Disease d = diseaseDao.findOne(id);
        d.setDiseaseName(name);
        d.setKcal(cal);
        d.setFat(fat);
        d.setCarboh(carboh);
        d.setProt(protein);
        Disease disease = diseaseDao.update(d);

        List<FoodSetDisease> foodSetDisease = foodSetDiseasedao.findByDisease(disease.getId());
        for (FoodSetDisease fs: foodSetDisease) {
            foodSetDiseasedao.delete(fs);
        }

        return disease;
    }
    @RequestMapping(value = "/getSelectedSetMenuByDisease",method = RequestMethod.GET)
    public List<String> getSelectedSetMenuByDisease(@RequestParam("name")String name) throws SQLException {
        Disease disease = diseaseDao.findByDiseaseName(name);
        List<FoodSetDisease> foodSetDisease = foodSetDiseasedao.findByDisease(disease.getId());
        List<SetMenu> setMenu = new ArrayList<>();
        for (FoodSetDisease f : foodSetDisease
             ) {
            setMenu.add(setMenuDao.findOne(f.getSetmenu()));
        }
        List<String> setName = new ArrayList<>();
        List<FoodSetMenu> foodSetMenu;
        for (SetMenu s: setMenu) {
            foodSetMenu = foodSetMenuDao.findBySetmenu(s.getSetmenu());
            String foodName = "";
           //   value.add(""+s.getSetmenu());
//            value.add(""+s.getTotal_cal());
            for (FoodSetMenu foodSet: foodSetMenu) {
                foodName += (foodDao.findOne(foodSet.getFoodId()).getFoodNameEng());
                if(foodSet.getFoodIndex()!=3){
                    foodName += "|";
                }
            }
            setName.add(foodName);

        }
            return setName;

    }
    @RequestMapping(value = "/deleteDisease",method = RequestMethod.GET)
    public boolean deleteDisease(@RequestParam("name")String name) throws SQLException {
        Disease disease = diseaseDao.findByDiseaseName(name);
        List<FoodSetDisease> foodSetDiseases = foodSetDiseasedao.findByDisease(disease.getId());
        for (FoodSetDisease f : foodSetDiseases
             ) {
            foodSetDiseasedao.delete(f);
        }
        diseaseDao.delete(disease);
        return true;
    }
}
