package personal.carlthronson.workflow.data.core;

import lombok.Getter;
import lombok.Setter;

public class TaskUpdate {

    @Getter
    @Setter
    private Long taskId;
    
    @Getter
    @Setter
    private Long statusId;
}
