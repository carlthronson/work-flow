package personal.carlthronson.workflow.data.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import personal.carlthronson.workflow.data.core.Phase;

@Entity(name = "phase")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PhaseEntity extends Phase {

  /**
   * A Phase can haver zero or more Statuses
   * And a Status must have exactly one Phase
   * 
   * The Phase is created first
   * And then the Status is created and refers to the Phase
   * Meaning Status is the owner of the relationship
   * And the status table contains the phase_id column
   */
  @OneToMany(mappedBy = "phase")
  /**
   * For Json
   * The Phase should not include the Statuses
   */
  @JsonBackReference(value = "status-phase")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Getter
  @Setter
  private List<StatusEntity> statuses;
}
