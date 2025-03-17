package personal.carlthronson.workflow.gql.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import personal.carlthronson.workflow.gql.svc.SignupService;

@RestController
@EnableWebMvc
@Transactional
public class Signup {

  @Autowired
  private SignupService signupService;

  @MutationMapping(name = "signup")
  public Long signup(
      @Argument(name = "email") String email,
      @Argument(name = "password") String password) {
    return signupService.signup(email, password);
  }

}
