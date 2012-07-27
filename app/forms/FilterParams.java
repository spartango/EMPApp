package forms;

import org.codehaus.jackson.map.ObjectMapper;
import play.data.validation.Constraints.Required;
import play.Logger;
import java.io.IOException;

public class FilterParams {
    public static final ObjectMapper mapper = new ObjectMapper();

    public @Required Integer maskSize;
    public @Required Integer lowPassFilter;
    public @Required Integer binning;

    public String toJSONString() {
        try {
            return mapper.writeValueAsString(this);
        } catch(IOException e) {
            Logger.error("Failed to serialize FilterParams "+this);
            return "";
        }
    }
}