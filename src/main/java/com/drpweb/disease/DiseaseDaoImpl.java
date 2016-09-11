package com.drpweb.disease;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ADMIN on 8/27/2016.
 */
@Repository
public class DiseaseDaoImpl implements DiseaseDao {
    @Autowired
    DiseaseRepository diseaseRepository;

    @Override
    public Disease create(Disease disease) {
        return diseaseRepository.save(disease);
    }

    @Override
    public Disease update(Disease disease) {
        return diseaseRepository.save(disease);
    }

    @Override
    public void delete(Disease disease) {
        diseaseRepository.delete(disease);
    }

    @Override
    public Disease findOne(int id) {
        return diseaseRepository.findOne(id);
    }

    @Override
    public List<Disease> findAll() {
        return diseaseRepository.findAll();
    }
}
