package personal.carlthronson.workflow.data.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import personal.carlthronson.workflow.data.core.Status;

@Entity(name = "status")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class StatusEntity extends Status {

  /**
   * Every Status needs a Phase
   * But a Phase does not need a Status
   */
  @ManyToOne
  /**
   * The Phase is created first
   * And then the Status is created and refers to the Phase
   * Meaning Status is the owner of the relationship
   * And the status table contains the phase_id column
   */
  @JoinColumn(name = "phase_id", nullable = true, unique = false)
  /**
   * For Json
   * Every Status should include the Phase
   */
  @JsonManagedReference(value = "status-phase")
  @Getter
  @Setter
  private PhaseEntity phase;
}
