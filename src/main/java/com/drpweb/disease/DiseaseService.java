package com.drpweb.disease;

/**
 * Created by ADMIN on 8/27/2016.
 */
public interface DiseaseService {
     Disease getDiseaseById(Long id);
     Disease findByDiseaseName(String diseaseName);

}
