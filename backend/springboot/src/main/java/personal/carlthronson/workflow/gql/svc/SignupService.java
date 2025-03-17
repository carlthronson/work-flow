package personal.carlthronson.workflow.gql.svc;

import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import personal.carlthronson.workflow.data.entity.AccountEntity;
import personal.carlthronson.workflow.data.entity.RoleEntity;
import personal.carlthronson.workflow.jpa.repo.AccountRepository;
import personal.carlthronson.workflow.jpa.repo.RoleRepository;
import personal.carlthronson.workflow.security.AuthorizationService;

@Service
@Transactional
public class SignupService {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private AuthorizationService authorizationService;

  static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  static {
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  }

  private AccountEntity createNewAccount(String email, String password, String role) {
    logger.info("This email has never been used");
    AccountEntity newAccountEntity = new AccountEntity();
    newAccountEntity.setEmail(email);
    if (password != null) {
      newAccountEntity.setPassword(passwordEncoder.encode(password));
    }
    newAccountEntity.setEnabled(true);
    RoleEntity roleEntity = roleRepository.findByReference(role);
    newAccountEntity.setRoles(Set.of(roleEntity));
    AccountEntity savedAccountEntity = accountRepository.save(newAccountEntity);
    return savedAccountEntity;
  }

  public Long signup(String email, String password) {
    logger.info(String.format("signup: email [%s] password [%s]", email, password));
    AccountEntity existingAccountEntity = accountRepository.findByEmail(email);
    if (existingAccountEntity == null) {
      AccountEntity savedAccountEntity = createNewAccount(email, password, "GUEST");
      authorizationService.login(email, password);
      return savedAccountEntity.getId();
    } else {
      throw new IllegalArgumentException("Email is unavailable");
    }
  }


}
