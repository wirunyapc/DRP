package com.drpweb.disease;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ADMIN on 8/27/2016.
 */
@Service
public class DiseaseServiceImpl implements DiseaseService {
    @Autowired
    DiseaseDao diseaseDao;

    @Override
    public Disease getDiseaseById(Long id) {

        return diseaseDao.findOne(id);
    }

    @Override
    public Disease findByDiseaseName(String diseaseName) {
        return diseaseDao.findByDiseaseName(diseaseName);
    }


}
