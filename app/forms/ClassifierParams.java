package forms;

import org.codehaus.jackson.map.ObjectMapper;
import play.data.validation.Constraints.Required;
import play.Logger;
import java.io.IOException;

public class ClassifierParams extends PipelineParams {
    public @Required Integer classCount;
    public Integer           principalComponents = 12;
    public Double            classAccuracy       = 0.001;
}