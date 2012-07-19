package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

public @Entity class Pipeline extends Model {

    /**
     * 
     */
    private static final long                                                               serialVersionUID = -7980942758060990464L;
    @Id public Long                                                                         id;
    public @ManyToOne Project                                                               project;
    public @OneToMany(mappedBy = "pipeline", cascade = CascadeType.ALL) List<Particle>      particles;
    public @OneToMany(mappedBy = "pipeline", cascade = CascadeType.ALL) List<ParticleClass> particleClasses;

    public Pipeline(Project project) {
        super();
        this.project = project;
        particleClasses = new ArrayList<>();
        particles = new ArrayList<>();
    }

    public static Model.Finder<Long, Pipeline> find = new Finder<Long, Pipeline>(Long.class,
                                                                                 Pipeline.class);
}
