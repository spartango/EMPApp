package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

public @Entity class ParticleClass extends Model {

    /**
     * 
     */
    private static final long  serialVersionUID = 7101243556881813483L;
    public @ManyToOne Pipeline pipeline;
    public @OneToOne Particle  average;

    public ParticleClass(Pipeline pipeline, Particle average) {
        super();
        this.pipeline = pipeline;
        this.average = average;
    }

}
