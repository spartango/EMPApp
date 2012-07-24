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
            // Figure out what state we were in
                if(pipeline.getStatus() == models.Pipeline.SELECT_IMAGES)
                    return ok(imageSelect.render(pipeline));
                else if(pipeline.getStatus() == models.Pipeline.CONFIG_FILTERS)
                    return ok(filter.render(pipeline));
                else if(pipeline.getStatus() == models.Pipeline.CONFIG_GENERATION)
                    return ok(rotate.render(pipeline));
                else if(pipeline.getStatus() == models.Pipeline.CONFIG_CLASSIFIER)
                    return ok(classify.render(pipeline));
                else if(pipeline.getStatus() == models.Pipeline.START_RUN)
                    return ok(startRun.render(pipeline));
                else if(pipeline.getStatus() == models.Pipeline.RUNNING)
                    return ok(status.render(pipeline));
                else if(pipeline.getStatus() == models.Pipeline.COMPLETE)
                    return ok(results.render(pipeline));
                else if(pipeline.getStatus() == models.Pipeline.ERROR)
                    return ok(error.render(pipeline));
                else 
                    return notFound();
            // Render the right page for that state.
        } else {
            return badRequest();
        }

    }

    public static @Restrict(Application.USER_ROLE) Result selectImages(Long id) {
        final User user = Application.getLocalUser(session());
        // Get the pipeline
        models.Pipeline pipeline = models.Pipeline.findByIdWithOwner(id, user);
        if(pipeline != null && pipeline.getStatus() >= models.Pipeline.SELECT_IMAGES) {
            return ok(imageSelect.render(pipeline));
        } else {
            return badRequest();
        }
    }

    public static @Restrict(Application.USER_ROLE) Result pickParticles(Long id) {
        final User user = Application.getLocalUser(session());
        // Get the pipeline
        models.Pipeline pipeline = models.Pipeline.findByIdWithOwner(id, user);        
        if(pipeline != null && pipeline.getStatus() >= models.Pipeline.CONFIG_PICKER) {
            return ok(picker.render(pipeline));
        } else {
            return badRequest();
        }
    }

    public static @Restrict(Application.USER_ROLE) Result filter(Long id) {
        final User user = Application.getLocalUser(session());
        // Get the pipeline
        models.Pipeline pipeline = models.Pipeline.findByIdWithOwner(id, user);        
        if(pipeline != null && pipeline.getStatus() >= models.Pipeline.CONFIG_FILTERS) {
            return ok(filter.render(pipeline));
        } else {
            return badRequest();
        }
    }

    public static @Restrict(Application.USER_ROLE) Result rotate(Long id) {
        final User user = Application.getLocalUser(session());
        // Get the pipeline
        models.Pipeline pipeline = models.Pipeline.findByIdWithOwner(id, user);    
        if(pipeline != null && pipeline.getStatus() >= models.Pipeline.CONFIG_GENERATION) {
            return ok(rotate.render(pipeline));
        } else {
            return badRequest();
        }
    }

    public static @Restrict(Application.USER_ROLE) Result classify(Long id) {
        final User user = Application.getLocalUser(session());
        // Get the pipeline
        models.Pipeline pipeline = models.Pipeline.findByIdWithOwner(id, user);    
        if(pipeline != null && pipeline.getStatus() >= models.Pipeline.CONFIG_CLASSIFIER) {
            return ok(classify.render(pipeline));
        } else {
            return badRequest();
        }
    }

    public static @Restrict(Application.USER_ROLE) Result startRun(Long id) {
        final User user = Application.getLocalUser(session());
        // Get the pipeline
        models.Pipeline pipeline = models.Pipeline.findByIdWithOwner(id, user);    
        if(pipeline != null && pipeline.getStatus() >= models.Pipeline.START_RUN) {
            return ok(startRun.render(pipeline));
        } else {
            return badRequest();
        }
    }

    public static @Restrict(Application.USER_ROLE) Result status(Long id) {
        final User user = Application.getLocalUser(session());
        // Get the pipeline
        models.Pipeline pipeline = models.Pipeline.findByIdWithOwner(id, user);    
        if(pipeline != null && pipeline.getStatus() >= models.Pipeline.RUNNING) {
            return ok(status.render(pipeline));
        } else {
            return badRequest();
        }
    }

    public static @Restrict(Application.USER_ROLE) Result doSelectImages(Long id) {
        final User user = Application.getLocalUser(session());
        // Get the pipeline
        models.Pipeline pipeline = models.Pipeline.findByIdWithOwner(id, user);    
        if(pipeline != null && pipeline.getStatus() >= models.Pipeline.RUNNING) {
            return ok();
        } else {
            return badRequest();
        }
    }

}