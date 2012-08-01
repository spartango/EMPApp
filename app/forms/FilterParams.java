package forms;

import play.data.validation.Constraints.Required;

public class FilterParams extends PipelineParams {
    public @Required Integer maskSize;
    public @Required Integer lowPassFilter;
    public @Required Integer binning;
}