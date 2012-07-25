package models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.URL;

import play.db.ebean.Model;

public @Entity class Image extends Model {

    /**
     * 
     */
    private static final long serialVersionUID = 6727022222330879650L;
    @Id public Long                   id;
    public @URL String                url;
    public String                     name;
    public @ManyToOne Project         project;
    public @ManyToMany List<Pipeline> pipelines;
    public Date                       uploaded;

    public Image(String name, String url, Project project) {
        super();
        this.name = name;
        this.url = url;
        this.project = project;
        this.uploaded = new Date();
        pipelines = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Date getUploaded() { 
        return uploaded;
    }

    public Project getProject() { 
        return project;
    }

    public Long getId() {
        return id;
    }

    public void addPipeline(Pipeline target) {
        pipelines.add(target);
    }

    public static Model.Finder<Long, Image> find = new Finder<Long, Image>(Long.class,
                                                                           Image.class);


    public static Image findById(Long id) {
        return find.byId(id);
    }

    public static Image findByIdWithOwner(Long id, User owner) {
        Image found = findById(id);
        if (found != null && Project.hasAccess(owner, found.getProject())) {
            return found;
        } else
            return null;
    }
}
