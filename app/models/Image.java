package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.URL;

import play.db.ebean.Model;

public @Entity class Image extends Model {

    /**
     * 
     */
    private static final long serialVersionUID = 6727022222330879650L;
    @Id public Long           id;
    public @URL String        url;
    public String             name;
    public @ManyToOne Project project;
    public Date               uploaded;

    public Image(String name, String url, Project project) {
        super();
        this.name = name;
        this.url = url;
        this.project = project;
        this.uploaded = new Date();
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

    public static Model.Finder<Long, Image> find = new Finder<Long, Image>(Long.class,
                                                                           Image.class);
}
