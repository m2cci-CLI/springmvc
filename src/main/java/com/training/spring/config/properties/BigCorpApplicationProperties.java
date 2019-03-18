package com.training.spring.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ConfigurationProperties(prefix = "bigcorp")
public class BigCorpApplicationProperties {

    private String name;
    private Integer version;
    private Set<String> emails;
    private String webSiteUrl;
    @NestedConfigurationProperty
    private BigCorpApplicationMeasureProperties measure;

    public BigCorpApplicationMeasureProperties getMeasure() {
        return measure;
    }

    public void setMeasure(BigCorpApplicationMeasureProperties measure) {
        this.measure = measure;
    }

    public BigCorpApplicationProperties() {
    }

    public String getName() {
        return name;
    }

    public Integer getVersion() {
        return version;
    }

    public Set<String> getEmails() {
        return emails;
    }

    public String getWebSiteUrl() {
        return webSiteUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    public void setWebSiteUrl(String webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
    }
}
