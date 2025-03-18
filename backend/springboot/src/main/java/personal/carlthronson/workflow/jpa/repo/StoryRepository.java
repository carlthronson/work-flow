package personal.carlthronson.workflow.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import personal.carlthronson.workflow.data.entity.StoryEntity;

@Repository
@Transactional
public interface StoryRepository extends BaseRepository<StoryEntity> {
}
