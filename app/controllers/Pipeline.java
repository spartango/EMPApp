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
            return ok(imageSelect.render(newPipeline));
        } else {
            return badRequest();
        }

    }

    public static @Restrict(Application.USER_ROLE) Result resume(Long id) {
        final User user = Application.getLocalUser(session());
        // Get the pipeline
        models.Pipeline pipeline = models.Pipeline.findByIdWithOwner(id, user);
        if(pipeline != null) {
            // Make a new pipeline
            // Send the user to the image upload screen
            return ok(imageSelect.render(pipeline));
        } else {
            return badRequest();
        }
        // Figure out what state we were in
        // Render the right page for that state.
    }

    public static @Restrict(Application.USER_ROLE) Result selectImages(Long id) {
        return ok();
    }

    public static @Restrict(Application.USER_ROLE) Result filter(Long id) {
        return ok();
    }

    public static @Restrict(Application.USER_ROLE) Result rotate(Long id) {
        return ok();
    }

    public static @Restrict(Application.USER_ROLE) Result classify(Long id) {
        return ok();
    }

    public static @Restrict(Application.USER_ROLE) Result startRun(Long id) {
        return ok();
    }



}