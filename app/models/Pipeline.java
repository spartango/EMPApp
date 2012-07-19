package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

public @Entity class Pipeline extends Model {

    /**
     * 
     */
    private static final long serialVersionUID = -7980942758060990464L;
    public @ManyToOne Project project;

}
