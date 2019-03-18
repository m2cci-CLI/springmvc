package com.training.spring.service;

import com.training.spring.model.Captor;
import com.training.spring.model.PowerSource;

import java.util.Set;

public interface CaptorService {
    Set<Captor> findBySite(String siteId);
}
