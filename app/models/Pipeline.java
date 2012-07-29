package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.Lob;

import play.db.ebean.Model;

public @Entity class Pipeline extends Model {

    // Pipeline status information
    public static final long                                                                SELECT_IMAGES     = 0;
    public static final long                                                                CONFIG_PICKER     = 1;
    public static final long                                                                CONFIG_FILTERS    = 2;
    public static final long                                                                CONFIG_GENERATION = 3;
    public static final long                                                                CONFIG_CLASSIFIER = 4;
    public static final long                                                                START_RUN         = 5;
    public static final long                                                                RUNNING           = 6;
    public static final long                                                                COMPLETE          = 7;
    public static final long                                                                ERROR             = 8;
    /**
     * 
     */ 
    private static final long                                                                 serialVersionUID  = -7980942758060990464L;
    @Id public Long                                                                           id;
    public @ManyToOne Project                                                                 project;
    public Long                                                                               status;
    public Date                                                                               created;
    public @Lob String                                                                        pickerParams;
    public @Lob String                                                                        filterParams;
    public @Lob String                                                                        generationParams;
    public @Lob String                                                                        classifierParams;

    public @ManyToMany(mappedBy = "pipelines", cascade = CascadeType.ALL) Set<Image>          images;
    public @OneToMany(mappedBy = "pipeline", cascade = CascadeType.ALL)   List<Particle>      particles;
    public @OneToMany(mappedBy = "pipeline", cascade = CascadeType.ALL)   List<ParticleClass> particleClasses;

    public Pipeline(Project project) {
        super();
        this.project = project;
        this.status = SELECT_IMAGES;
        this.created = new Date();
        this.pickerParams = "{}";
        this.generationParams = "{}";
        this.filterParams = "{}";
        this.classifierParams = "{}";
        particleClasses = new ArrayList<>();
        particles = new ArrayList<>();
        images = new HashSet<>();
    }

    public static Model.Finder<Long, Pipeline> find = new Finder<Long, Pipeline>(Long.class,
                                                                                 Pipeline.class);

    public Long getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long newStatus) {
        status = newStatus;
    }

    public String getPickerParams() {
        return pickerParams;
    }

    public void setPickerParams(String newParams) {
        pickerParams = newParams;
    }

    public String getFilterParams() {
        return filterParams;
    }

    public void setFilterParams(String newParams) {
        filterParams = newParams;
    }

    public String getGenerationParams() {
        return generationParams;
    }

    public void setGenerationParams(String newParams) {
        generationParams = newParams;
    }

    public String getClassifierParams() {
        return classifierParams;
    }

    public void setClassifierParams(String newParams) {
        classifierParams = newParams;
    }


    public Project getProject() {
        return project;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void addImage(Image target) {
        //target.addPipeline(this);
        images.add(target);
    }

    public boolean containsImage(Image target) {
        return images.contains(target);
    }

    public void clearImages() {
        images.clear();
    }

    public String getStatusString() {
            if(status == SELECT_IMAGES)
                return "Selecting Images";
            else if(status == CONFIG_PICKER)
                return "Particle Picking";
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
        if (found != null && Project.hasAccess(owner, found.getProject())) {
            return found;
        } else
            return null;
    }
}
