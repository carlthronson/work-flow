package personal.carlthronson.workflow.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import personal.carlthronson.workflow.data.core.AccountAuthorizationPrincipal;
import personal.carlthronson.workflow.data.entity.AccountEntity;
import personal.carlthronson.workflow.jpa.repo.AccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AccountEntity accountEntity = accountRepository.findByEmail(username);
    AccountAuthorizationPrincipal result = new AccountAuthorizationPrincipal();
    result.setUsername(username);
    result.setPassword(accountEntity.getPassword());
    result.setAuthorities(accountEntity.getRoles().stream().map(role -> {
      return new SimpleGrantedAuthority("ROLE_" + role.getReference());
    }).collect(Collectors.toList()));
    return result;
  }

}
