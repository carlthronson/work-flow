package personal.carlthronson.workflow.gql.resolvers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import personal.carlthronson.workflow.data.entity.PhaseEntity;
import personal.carlthronson.workflow.data.entity.StatusEntity;
import personal.carlthronson.workflow.data.entity.StoryEntity;
import personal.carlthronson.workflow.data.entity.TaskEntity;
import personal.carlthronson.workflow.jpa.repo.PhaseRepository;
import personal.carlthronson.workflow.jpa.repo.StatusRepository;
import personal.carlthronson.workflow.jpa.repo.StoryRepository;
import personal.carlthronson.workflow.jpa.repo.TaskRepository;
import personal.carlthronson.workflow.rest.StoryService;

@RestController
@EnableWebMvc
@Transactional
public class Story {

  Logger logger = Logger.getLogger(StoryService.class.getName());

  @Autowired
  private StoryRepository storyRepository;

  @Autowired
  private PhaseRepository phaseRepository;

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private StatusRepository statusRepository;

  @MutationMapping(name = "createStory")
  public Long createStory(
      @Argument(name = "reference") String reference,
      @Argument(name = "details") String details) {
    StoryEntity entity = new StoryEntity();
    entity.setReference(reference);
    entity.setDetails(details);
    PhaseEntity phaseEntity = phaseRepository.findByReference("NEW");
    entity.setPhase(phaseEntity);
    return storyRepository.save(entity).getId();
  }

  @MutationMapping(name = "createTask")
  public Long createTask(
      @Argument(name = "reference") String reference,
      @Argument(name = "details") String details,
      @Argument(name = "story") String story) {
    TaskEntity entity = new TaskEntity();
    entity.setReference(reference);
    entity.setDetails(details);
    StatusEntity statusEntity = statusRepository.findByReference("NEW");
    entity.setStatus(statusEntity);
    StoryEntity storyEntity = storyRepository.findByReference(story);
    entity.setStory(storyEntity);
    return taskRepository.save(entity).getId();
  }
}
