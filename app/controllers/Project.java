package controllers;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
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
        return ok();
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
        return ok();
    }
}