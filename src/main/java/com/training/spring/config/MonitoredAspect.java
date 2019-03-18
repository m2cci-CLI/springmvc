package com.training.spring.config;

import com.training.spring.service.SiteService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MonitoredAspect {

    private final static Logger logger = LoggerFactory.getLogger(MonitoredAspect.class);

    @Before("@annotation(Monitored)")
    public void logServiceBeforeCall(JoinPoint jp) {
        logger.info("Appel finder " + jp.getSignature());
    }

    @AfterReturning(pointcut = "@annotation(Monitored)", returning = "site")
    public void logServiceAfterCall(JoinPoint jp, Object site) {
        if (site == null) {
            logger.info("Finder " + jp.getTarget() + "returns null");
        } else {
            logger.info("Finder " + jp.getTarget() + "returns " + site.toString());
        }
    }
}
