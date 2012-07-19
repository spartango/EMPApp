package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

public @Entity class Project extends Model {

    /**
     * 
     */
    private static final long                                                         serialVersionUID = -5522690761426776041L;
    @Id public Long                                                                   id;
    public String                                                                     name;
    public @Lob String                                                                description;
    public @ManyToOne User                                                            owner;
    public Date                                                                       created;
    public @OneToMany(mappedBy = "project", cascade = CascadeType.ALL) List<Image>    images;
    public @OneToMany(mappedBy = "project", cascade = CascadeType.ALL) List<Pipeline> pipelines;

    public Project(String name, String description, User owner) {
        super();
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.created = new Date();
        this.images = new ArrayList<>();
        this.pipelines = new ArrayList<>();

    }

    public static Model.Finder<Long, Project> find = new Finder<Long, Project>(Long.class,
                                                                               Project.class);

}
