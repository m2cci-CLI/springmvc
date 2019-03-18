package com.training.spring.service;

import com.training.spring.model.Captor;
import com.training.spring.repository.CaptorDao;
import com.training.spring.service.measure.MeasureService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service("captorService")
public class CaptorServiceImpl implements CaptorService{

    private MeasureService fixedMeasureService;
    private MeasureService simulatedMeasureService;
    private MeasureService realMeasureService;
    private CaptorDao captorDao;

    public CaptorServiceImpl(MeasureService fixedMeasureService, MeasureService simulatedMeasureService, MeasureService realMeasureService, CaptorDao captorDao) {
        this.fixedMeasureService = fixedMeasureService;
        this.simulatedMeasureService = simulatedMeasureService;
        this.realMeasureService = realMeasureService;
        this.captorDao = captorDao;
    }

    @Override
    public Set<Captor> findBySite(String siteId) {
        return captorDao.findBySiteId(siteId).stream().collect(Collectors.toSet());
    }
}
