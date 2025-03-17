package personal.carlthronson.workflow.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import personal.carlthronson.workflow.data.entity.ResetPasswordTokenEntity;

@Repository
@Transactional
public interface ResetPasswordTokenRepository
    extends BaseRepository<ResetPasswordTokenEntity> {
}
