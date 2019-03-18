package com.training.spring.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("FIXED")
public class FixedCaptor extends Captor{

    private Integer defaultPowerInWatt;

    public FixedCaptor(){
        super();
    }

    public FixedCaptor(String name, Site site, Integer defaultPowerInWatt) {
        super(name, site, PowerSource.FIXED);
        this.defaultPowerInWatt = defaultPowerInWatt;
    }

    public Integer getDefaultPowerInWatt() {
        return defaultPowerInWatt;
    }

    public void setDefaultPowerInWatt(Integer defaultPowerInWatt) {
        this.defaultPowerInWatt = defaultPowerInWatt;
    }
}
