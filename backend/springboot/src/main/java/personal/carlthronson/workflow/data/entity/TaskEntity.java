package personal.carlthronson.workflow.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import personal.carlthronson.workflow.data.core.Task;

@Entity(name = "task")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TaskEntity extends Task {

  /**
   * Every Task needs a Status
   * But a Status does not need a Task
   */
  @ManyToOne
  /**
   * The Status is created first
   * And then the Task is created and refers to the Status
   * Meaning Task is the owner of the relationship
   * And the task table contains the status_id column
   */
  @JoinColumn(name = "status_id", nullable = true, unique = false)
  /**
   * For Json
   * Every Task should include the Status
   */
  @JsonManagedReference(value = "task-status")
  @Getter
  @Setter
  private StatusEntity status;

  /**
   * Every Task needs a Story
   * And every Story needs a Task
   */
  @ManyToOne
  /**
   * The Story is created first
   * And then the Task is created and refers to the Story
   * Meaning Task is the owner of the relationship
   * And the task table contains the story_id column
   */
  @JoinColumn(name = "story_id", nullable = true, unique = false)
  /**
   * For Json
   * The Task should not include the Story
   */
  @JsonBackReference(value = "story-task")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Getter
  @Setter
  private StoryEntity story;
}
