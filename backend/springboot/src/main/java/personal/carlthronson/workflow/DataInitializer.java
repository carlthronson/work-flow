package personal.carlthronson.workflow;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import personal.carlthronson.workflow.data.BaseObject;
import personal.carlthronson.workflow.data.entity.AccountEntity;
import personal.carlthronson.workflow.data.entity.PhaseEntity;
import personal.carlthronson.workflow.data.entity.RoleEntity;
import personal.carlthronson.workflow.data.entity.StatusEntity;
import personal.carlthronson.workflow.jpa.repo.AccountRepository;
import personal.carlthronson.workflow.jpa.repo.BaseRepository;
import personal.carlthronson.workflow.jpa.repo.PhaseRepository;
import personal.carlthronson.workflow.jpa.repo.RoleRepository;
import personal.carlthronson.workflow.jpa.repo.StatusRepository;

@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private PhaseRepository phaseRepository;

  @Autowired
  private StatusRepository statusRepository;

  @Override
  public void run(String... args) throws Exception {
    if (roleRepository.count() == 0) {
      RoleEntity admin = new RoleEntity();
      admin.setReference("ADMIN");
      admin.setDetails("Admin");
      roleRepository.save(admin);
      RoleEntity dev = new RoleEntity();
      dev.setReference("DEV");
      dev.setDetails("Dev");
      roleRepository.save(dev);
      RoleEntity qa = new RoleEntity();
      qa.setReference("QA");
      qa.setDetails("Qa");
      roleRepository.save(qa);
      RoleEntity guest = new RoleEntity();
      qa.setReference("GUEST");
      qa.setDetails("GUEST");
      roleRepository.save(guest);
      AccountEntity carl = new AccountEntity();
      carl.setEmail("carlthronson@gmail.com");
      carl.setRoles(Set.of(admin, dev));
      carl.setEnabled(true);
      accountRepository.save(carl);
    }
    if (phaseRepository.count() == 0) {
      PhaseEntity newPhase = new PhaseEntity();
      createEntity("NEW", "New", phaseRepository, newPhase);
      PhaseEntity devPhase = new PhaseEntity();
      createEntity("DEV", "Development", phaseRepository, devPhase);
      PhaseEntity qaPhase = new PhaseEntity();
      createEntity("QA", "Testing", phaseRepository, qaPhase);
      StatusEntity newStatus = new StatusEntity();
      newStatus.setPhase(newPhase);
      createEntity("NEW", "New", statusRepository, newStatus);
      StatusEntity inProgressStatus = new StatusEntity();
      inProgressStatus.setPhase(devPhase);
      createEntity("IN-PROGRESS", "In Progress", statusRepository, inProgressStatus);
      StatusEntity testingStatus = new StatusEntity();
      testingStatus.setPhase(qaPhase);
      createEntity("TESTING", "Testing", statusRepository, testingStatus);
    }
  }

  private <T extends BaseObject> void createEntity(String reference, String details, BaseRepository<T> repo, T entity) {
    entity.setReference(reference);
    entity.setDetails(details);
    repo.save(entity);
  }
}
