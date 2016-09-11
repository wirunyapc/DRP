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
    public Disease getDiseaseById(int id) {

        return diseaseDao.findOne(id);
    }



}
