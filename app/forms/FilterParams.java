package forms;

import org.codehaus.jackson.map.ObjectMapper;
import play.data.validation.Constraints.Required;
import play.Logger;
import java.io.IOException;

public class FilterParams extends PipelineParams{
    public @Required Integer maskSize;
    public @Required Integer lowPassFilter;
    public @Required Integer binning;
}