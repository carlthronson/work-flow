package personal.carlthronson.workflow.jpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import personal.carlthronson.workflow.data.BaseObject;

@NoRepositoryBean
public interface BaseRepository<ENTITY extends BaseObject> extends JpaRepository<ENTITY, Long>, CrudRepository<ENTITY, Long> {

  @Override
  @Modifying
  @Query("UPDATE #{#entityName} e SET e.deletedAt = CURRENT_TIMESTAMP WHERE e.id = :id")
  void deleteById(@Param("id") Long id);

  List<ENTITY> findAllById(Long id);

  boolean existsByReference(String reference);

  ENTITY findByReference(String reference);

  List<ENTITY> findAllByReference(String reference);

  boolean existsByDetails(String details);

  ENTITY findByDetails(String details);

  List<ENTITY> findAllByDetails(String details);

  @Override
  @Query("SELECT e FROM #{#entityName} e WHERE e.deletedAt IS NULL")
  List<ENTITY> findAll();
}
