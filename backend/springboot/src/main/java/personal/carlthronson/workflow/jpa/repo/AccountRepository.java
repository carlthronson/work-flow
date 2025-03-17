package personal.carlthronson.workflow.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import personal.carlthronson.workflow.data.entity.AccountEntity;

@Repository
@Transactional
public interface AccountRepository extends BaseRepository<AccountEntity> {

  AccountEntity findByEmail(String email);
}
