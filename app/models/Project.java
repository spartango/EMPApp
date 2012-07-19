package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

public @Entity class Project extends Model {

    /**
     * 
     */
    private static final long serialVersionUID = -5522690761426776041L;

    public String             name;
    public @Lob String        description;
    public @ManyToOne User    owner;
    public Date               created;

    public Project(String name, String description, User owner) {
        super();
        this.name = name;
        this.description = description;
        this.owner = owner;
        created = new Date();
    }

}
