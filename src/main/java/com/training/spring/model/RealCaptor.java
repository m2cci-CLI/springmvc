package com.training.spring.model;

import javax.persistence.Entity;

import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("REAL")
public class RealCaptor extends Captor {
    @Deprecated
    public RealCaptor() {
        super();
// used only by serializer and deserializer
    }
    public RealCaptor(String name, Site site) {
        super(name, site, PowerSource.REAL);
    }
}
