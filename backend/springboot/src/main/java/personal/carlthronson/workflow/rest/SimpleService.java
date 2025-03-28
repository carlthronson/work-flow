package personal.carlthronson.workflow.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import personal.carlthronson.workflow.data.BaseObject;
import personal.carlthronson.workflow.jpa.repo.BaseRepository;

public abstract class SimpleService<ENTITY extends BaseObject> {

    public abstract BaseRepository<ENTITY> getSimpleRepository();

    public abstract JpaRepository<ENTITY, Long> getJpaRepository();

    public void delete(Long id) {
        getJpaRepository().deleteById(id);
    }

    public ENTITY save(ENTITY entity) {
        return getJpaRepository().save(entity);
    }

    public ENTITY getById(Long id) {
        return getJpaRepository().getReferenceById(id);
    }

    public ENTITY findById(Long id) {
        return getJpaRepository().findById(id).get();
    }

    public List<ENTITY> findAllById(Long id) {
        return getSimpleRepository().findAllById(id);
    }

    public List<ENTITY> findAll() {
        return getSimpleRepository().findAll();
    }

    public ENTITY findByName(String name) {
        ENTITY entity = getSimpleRepository().findByReference(name);
        return entity;
    }

    public List<ENTITY> findAllByName(String name) {
        return getSimpleRepository().findAllByReference(name);
    }

    public ENTITY findByLabel(String label) {
        return getSimpleRepository().findByDetails(label);
    }

    public List<ENTITY> findAllByLabel(String label) {
        return getSimpleRepository().findAllByDetails(label);
    }

    public Page<ENTITY> findAll(Optional<Integer> limit) {
        Pageable pageable = PageRequest.of(0, limit.isPresent() ? limit.get() : 3);
        return getJpaRepository().findAll(pageable);
    }
}
