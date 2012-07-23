package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

public @Entity class Pipeline extends Model {

    // Pipeline status information
    public static final long                                                                SELECT_IMAGES     = 0;
    public static final long                                                                CONFIG_FILTERS    = 1;
    public static final long                                                                CONFIG_GENERATION = 2;
    public static final long                                                                CONFIG_CLASSIFIER = 3;
    public static final long                                                                START_RUN         = 4;
    public static final long                                                                RUNNING           = 5;
    public static final long                                                                COMPLETE          = 6;
    public static final long                                                                ERROR             = 7;

    /**
     * 
     */
    private static final long                                                               serialVersionUID  = -7980942758060990464L;
    @Id public Long                                                                         id;
    public @ManyToOne Project                                                               project;
    public Long                                                                             status;
    public Date                                                                             created;
    public @OneToMany(mappedBy = "pipeline", cascade = CascadeType.ALL) List<Particle>      particles;
    public @OneToMany(mappedBy = "pipeline", cascade = CascadeType.ALL) List<ParticleClass> particleClasses;

    public Pipeline(Project project) {
        super();
        this.project = project;
        this.status = SELECT_IMAGES;
        this.created = new Date();
        particleClasses = new ArrayList<>();
        particles = new ArrayList<>();
    }

    public static Model.Finder<Long, Pipeline> find = new Finder<Long, Pipeline>(Long.class,
                                                                                 Pipeline.class);

    public Date getCreated() {
        return created;
    }

    public String getStatusString() {
            if(status == SELECT_IMAGES)
                return "Selecting Images";
            else if(status == CONFIG_FILTERS)
                return "Configuring Filters";
            else if(status == CONFIG_GENERATION)
                return "Configuring Generation";
            else if(status == CONFIG_CLASSIFIER)
                return "Configuring Classifier";
            else if(status == START_RUN)
                return "Ready to Run";
            else if(status == RUNNING)
                return "Running";
            else if(status == COMPLETE)
                return "Complete" ;
            else if(status == ERROR)
                return "Error";
            else
                return "Unknown";
    }

    public static Pipeline findById(Long id) {
        return find.byId(id);
    }

    public static Pipeline findByIdWithOwner(Long id, User owner) {
        Pipeline found = findById(id);
        if (found != null && Project.hasAccess(owner, found.project)) {
            return found;
        } else
            return null;
    }
}
