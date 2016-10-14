package com.drpweb.disease;

import java.util.List;

/**
 * Created by ADMIN on 8/27/2016.
 */
public interface DiseaseDao {
//    Disease create(Disease disease);
//    Disease update (Disease disease);
//    void delete(Disease food);
    Disease findOne(Long id);
    List<Disease> findAll ();
    Disease findByDiseaseName(String diseaseName);

}
