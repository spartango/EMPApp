package controllers;

import models.User;
import models.Project;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.pipeline.*;
import be.objectify.deadbolt.actions.Restrict;

public class Pipeline extends Controller {
    public static @Restrict(Application.USER_ROLE) Result create(Long projectId) {
        final User user = Application.getLocalUser(session());
        // Get the project
        models.Project project = models.Project.findByIdWithOwner(projectId, user);
        if(project !=null) {
            // Make a new pipeline
            models.Pipeline newPipeline = new models.Pipeline(project);
            newPipeline.save();
            // Send the user to the image upload screen
            return ok(imageSelect.render());
        } else {
            return badRequest();
        }

    }

    public static @Restrict(Application.USER_ROLE) Result resume(Long id) {
        final User user = Application.getLocalUser(session());
        return ok();
    }
}