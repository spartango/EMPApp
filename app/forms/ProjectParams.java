package forms;

import play.data.validation.Constraints.Required;

public class ProjectParams {
    public @Required String name;
    public String           description;
}