package com.drpweb.disease;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ADMIN on 8/27/2016.
 */
public interface DiseaseRepository extends JpaRepository<Disease,Integer> {
    Disease findByDiseaseName(String diseaseName);
}
