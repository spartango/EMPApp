package controllers;

import models.User;
import models.Image;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.BodyParser;
import play.libs.Json;
import play.Logger;
import org.codehaus.jackson.JsonNode;
import views.html.project.*;
import be.objectify.deadbolt.actions.Restrict;
import forms.ProjectParams;

public class Project extends Controller {

    final static Form<ProjectParams> projectForm = form(ProjectParams.class);

    public static @Restrict(Application.USER_ROLE) Result overview() {
        final User user = Application.getLocalUser(session());
        return ok(overview.render(user.getProjects()));
    }

    public static @Restrict(Application.USER_ROLE) Result doCreate() {
        final User user = Application.getLocalUser(session());
        Form<ProjectParams> filledForm = projectForm.bindFromRequest();

        if (filledForm.hasErrors()) {
            return badRequest(create.render(filledForm));
        } else {
            ProjectParams created = filledForm.get();
            // Add a new project from the params
            models.Project newProject = new models.Project(created.getName(),
                                                           created.getDescription(),
                                                           user);
            newProject.save();
            return ok(pipelines.render(newProject));
        }
    }

    public static @Restrict(Application.USER_ROLE) Result create() {
        return ok(create.render(projectForm));
    }

    public static @Restrict(Application.USER_ROLE) Result describe(Long id) {
        final User user = Application.getLocalUser(session());
        return badRequest();
    }

    public static @Restrict(Application.USER_ROLE) Result pipelines(Long id) {
        final User user = Application.getLocalUser(session());
        models.Project thisProject = models.Project.findByIdWithOwner(id, user);
        if(thisProject == null) {
            return notFound();
        } else {
            return ok(pipelines.render(thisProject));
        }
    }

    public static @Restrict(Application.USER_ROLE) Result images(Long id) {
        final User user = Application.getLocalUser(session());
        return badRequest();
    }

    //@BodyParser.Of(BodyParser.Json.class)
    public static @Restrict(Application.USER_ROLE) Result uploadImage(Long id) {
        JsonNode json = request().body().asJson();
        if(json == null) {
            return badRequest("Expecting Json data");
        }

        String name = json.findPath("filename").getTextValue();
        String url  = json.findPath("url").getTextValue();
        if(name == null || url == null) {
            return badRequest("Missing fields");
        }

        final User user = Application.getLocalUser(session());
        models.Project thisProject = models.Project.findByIdWithOwner(id, user);
        if(thisProject == null) {
            return notFound();
        } else {
            // Build an image from the incoming form. 
            models.Image newImage = new models.Image(name, url, thisProject);
            newImage.save();
            return ok(newImage.getId().toString());
        }
    }
}