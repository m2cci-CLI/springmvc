package com.training.spring.service;

import com.training.spring.config.Monitored;
import com.training.spring.model.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Service("siteService")
@Component
public class SiteServiceImpl implements SiteService {

    private CaptorService captorService;

    @Autowired
    private ResourceLoader resourceLoader;

    private final static Logger logger = LoggerFactory.getLogger(SiteService.class);

    public SiteServiceImpl (){

    }

    @Autowired
    public SiteServiceImpl(CaptorService captorService, ResourceLoader resourceLoader) {
        logger.debug("Init SiteServiceImpl :" + this);
        this.captorService = captorService;
        this.resourceLoader = resourceLoader;
    }

    @Monitored
    @Override
    public Site findById(String siteId) {
        logger.debug("Appel de findById :" + this);
        if (siteId == null) {
            return null;
        }

        Site site = new Site("Florange");
        site.setId(siteId);
        site.setCaptors(captorService.findBySite(siteId));
        return site;
    }
}
