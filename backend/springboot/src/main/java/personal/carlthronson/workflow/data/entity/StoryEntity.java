package personal.carlthronson.workflow.data.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import personal.carlthronson.workflow.data.core.Story;

@Entity(name = "story")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class StoryEntity extends Story {

  /**
   * Every Story needs a Phase
   * But a Phase does not need a Story
   */
  @ManyToOne
  /**
   * The Phase is created first
   * And then the Story is created and refers to the Phase
   * Meaning Story is the owner of the relationship
   * And the story table contains the phase_id column
   */
  @JoinColumn(name = "phase_id", nullable = true, unique = false)
  /**
   * For Json
   * Every Story should include the Phase
   */
  @JsonManagedReference(value = "story-phase")
  @Getter
  @Setter
  private PhaseEntity phase;
}
