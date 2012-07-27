package forms;

import org.codehaus.jackson.map.ObjectMapper;
import play.data.validation.Constraints.Required;
import play.Logger;
import java.io.IOException;

public class GenerationParams extends PipelineParams {
    public @Required Double rotationAngle;
    public Integer shiftDistance = 0;
    public Integer shiftIncrement = 1;
}