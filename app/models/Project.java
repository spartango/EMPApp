package models;

import java.util.Collection;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

public @Entity class Project extends Model {

    public @Id Long                      id;

    public String                        name;
    public @OneToMany User               owner;
    public @ManyToMany Collection<Image> images = new Vector<>();

    public Project(String name, User owner) {
        super();
        this.name = name;
        this.owner = owner;
    }

    public static Model.Finder<Long, Project> find = new Model.Finder<Long, Project>(Long.class,
                                                                                     Project.class);

}
