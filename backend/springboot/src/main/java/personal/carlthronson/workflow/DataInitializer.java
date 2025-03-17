package personal.carlthronson.workflow;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import personal.carlthronson.workflow.data.entity.AccountEntity;
import personal.carlthronson.workflow.data.entity.RoleEntity;
import personal.carlthronson.workflow.jpa.repo.AccountRepository;
import personal.carlthronson.workflow.jpa.repo.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private AccountRepository accountRepository;

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
  }
}
