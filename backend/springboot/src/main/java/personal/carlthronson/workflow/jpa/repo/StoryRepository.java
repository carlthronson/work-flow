package personal.carlthronson.workflow.jpa.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import personal.carlthronson.workflow.data.entity.StoryEntity;
import personal.carlthronson.workflow.data.entity.TaskEntity;

@Repository
@Transactional
public interface StoryRepository extends JpaRepository<StoryEntity, Long>,
        BaseRepository<StoryEntity> {

    List<StoryEntity> findAllByTasksIn(List<TaskEntity> list);

    Page<StoryEntity> findAllByTasksIn(List<TaskEntity> content,
            Pageable pageable);
}
