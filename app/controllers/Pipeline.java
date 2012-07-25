package controllers;

import models.User;
import models.Project;
import models.Image;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.pipeline.*;
import be.objectify.deadbolt.actions.Restrict;
import play.Logger;
import java.util.Map;
import java.util.List;

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
        if(pipeline == null) {
            return badRequest();
        }
        // Figure out what state we were in
        if(pipeline.getStatus() == models.Pipeline.SELECT_IMAGES)
            return ok(imageSelect.render(pipeline));
        else if(pipeline.getStatus() == models.Pipeline.CONFIG_PICKER)
            return ok(picker.render(pipeline));
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
        Map<String, String[]> formData = request().body().asFormUrlEncoded();
        // Get the user
        final User user = Application.getLocalUser(session());
        // Get the pipeline
        models.Pipeline pipeline = models.Pipeline.findByIdWithOwner(id, user); 

        if(pipeline == null) {
            return badRequest();
        }

        if(formData == null || !formData.containsKey("images") || formData.get("images").length == 0) {
            return badRequest(imageSelect.render(pipeline));
        } 
        for(String imageString : formData.get("images")){
            try {
                Long imageId = Long.parseLong(imageString);
                Image image = Image.findByIdWithOwner(imageId, user);
                if(image != null) {
                    Logger.warn("Found image "+image);
                    pipeline.addImage(image);
                } else {
                    Logger.error("No imageId for "+imageString);
                    return badRequest(imageSelect.render(pipeline));
                }
            } catch (NumberFormatException e) {
                Logger.error("No imageId for "+imageString);
                return badRequest(imageSelect.render(pipeline));
            }
        }

        // OK to proceed to next step
        // pipeline.setStatus(models.Pipeline.CONFIG_PICKER);
        // return ok(picker.render(pipeline));
        return ok(pipeline.getImages().toString());
    }

}