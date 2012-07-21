package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import be.objectify.deadbolt.actions.Restrict;
import views.html.project.*;

public class Project extends Controller {

    public static @Restrict(Application.USER_ROLE) Result overview() {
        final User user = Application.getLocalUser(session());
        return ok(overview.render(user.getProjects()));
    }

    public static @Restrict(Application.USER_ROLE) Result doCreate() {
        final User user = Application.getLocalUser(session());
        return ok();
    }

    public static @Restrict(Application.USER_ROLE) Result create() {
        final User user = Application.getLocalUser(session());
        return ok(create.render());
    }

    public static @Restrict(Application.USER_ROLE) Result describe(Long id) {
        final User user = Application.getLocalUser(session());
        return ok();
    }

    public static @Restrict(Application.USER_ROLE) Result pipelines(Long id) {
        final User user = Application.getLocalUser(session());
        return ok();
    }

    public static @Restrict(Application.USER_ROLE) Result images(Long id) {
        final User user = Application.getLocalUser(session());
        return ok();
    }
}