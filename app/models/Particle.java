package models;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.URL;

import play.db.ebean.Model;

public @Entity class Particle extends Model {

    /**
     * 
     */
    private static final long                 serialVersionUID = 7685366821808050763L;
    public @URL String                        s3Url;
    public @ManyToOne Pipeline                pipeline;
    public @Nullable @ManyToOne ParticleClass classification;

    public Particle(String s3Url, Pipeline pipeline) {
        this(s3Url, pipeline, null);
    }

    public Particle(String s3Url,
                    Pipeline pipeline,
                    ParticleClass classification) {
        super();
        this.s3Url = s3Url;
        this.pipeline = pipeline;
        this.classification = classification;
    }

}
