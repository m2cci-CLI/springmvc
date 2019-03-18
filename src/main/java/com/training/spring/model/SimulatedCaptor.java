package com.training.spring.model;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;


@Entity
@DiscriminatorValue("SIMULATED")
public class SimulatedCaptor extends Captor{

    private Integer maxPowerInWatt;
    private Integer minPowerInWatt;

    public SimulatedCaptor(){
        super();
    }

    public SimulatedCaptor(String name, Site site, Integer minPowerInWatt, Integer maxPowerInWatt) {
        super(name, site, PowerSource.SIMULATED);
        this.minPowerInWatt = minPowerInWatt;
        this.maxPowerInWatt = maxPowerInWatt;
    }

    public Integer getMaxPowerInWatt() {
        return maxPowerInWatt;
    }

    public void setMaxPowerInWatt(Integer maxPowerInWatt) {
        this.maxPowerInWatt = maxPowerInWatt;
    }

    public Integer getMinPowerInWatt() {
        return minPowerInWatt;
    }

    public void setMinPowerInWatt(Integer minPowerInWatt) {
        this.minPowerInWatt = minPowerInWatt;
    }

    @AssertTrue(message = "minPowerInWatt should be less than maxPowerInWatt")
    public boolean isValid(){
        return this.minPowerInWatt <= this.maxPowerInWatt;
    }
}
