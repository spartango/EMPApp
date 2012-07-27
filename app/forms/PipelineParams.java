package forms;

import org.codehaus.jackson.map.ObjectMapper;
import play.Logger;
import java.io.IOException;

public abstract class PipelineParams {
    public static final ObjectMapper mapper = new ObjectMapper();

    public String toJSONString() {
        try {
            return mapper.writeValueAsString(this);
        } catch(IOException e) {
            Logger.error("Failed to serialize GenerationParams "+this);
            return "";
        }
    }
}