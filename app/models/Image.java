package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.URL;

import play.db.ebean.Model;

public @Entity class Image extends Model {

    /**
     * 
     */
    private static final long serialVersionUID = 6727022222330879650L;

    public @URL String        s3Url;
    public @ManyToOne Project project;
    public Date               uploaded;

    public Image(String s3Url, Project project) {
        super();
        this.s3Url = s3Url;
        this.project = project;
        this.uploaded = new Date();
    }

}
