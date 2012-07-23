package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
//import views.html.pipeline.*;
import be.objectify.deadbolt.actions.Restrict;

public class Pipeline extends Controller {
    public static @Restrict(Application.USER_ROLE) Result create(Long id) {
        final User user = Application.getLocalUser(session());
        return ok();
    }

    public static @Restrict(Application.USER_ROLE) Result resume(Long id) {
        final User user = Application.getLocalUser(session());
        return ok();
    }
}