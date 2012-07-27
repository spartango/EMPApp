package forms;

import org.codehaus.jackson.map.ObjectMapper;
import play.data.validation.Constraints.Required;
import play.Logger;
import java.io.IOException;

public class PickerParams {
    public static final ObjectMapper mapper = new ObjectMapper();

    public @Required Integer particleSize;
    public @Required Integer particleEpsilon;
    public Integer boxSize = 0;
    public @Required Integer firstFilter = 45;
    public @Required Integer secondFilter = 71;

    public void validateBoxSize() {
        if(boxSize == null || boxSize == 0) {
            boxSize = 2*(particleSize + particleEpsilon);
        }
    }

    public String toJSONString() {
        try {
            return mapper.writeValueAsString(this);
        } catch(IOException e) {
            Logger.error("Failed to serialize PickerParams "+this);
            return "";
        }
    }
}