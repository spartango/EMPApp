package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

public @Entity class ParticleClass extends Model {

    /**
     * 
     */
    private static final long                                                                serialVersionUID = 7101243556881813483L;
    @Id public Long                                                                          id;
    public @ManyToOne Pipeline                                                               pipeline;
    public @OneToOne Particle                                                                average;
    public @OneToMany(mappedBy = "classification", cascade = CascadeType.ALL) List<Particle> particles;

    public ParticleClass(Pipeline pipeline, Particle average) {
        super();
        this.pipeline = pipeline;
        this.average = average;
        particles = new ArrayList<>();
    }

    public static Model.Finder<Long, ParticleClass> find = new Finder<Long, ParticleClass>(Long.class,
                                                                                           ParticleClass.class);
}
