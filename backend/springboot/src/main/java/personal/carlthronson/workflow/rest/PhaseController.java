package personal.carlthronson.workflow.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import personal.carlthronson.workflow.data.core.Phase;
import personal.carlthronson.workflow.data.entity.PhaseEntity;
import personal.carlthronson.workflow.jpa.repo.PhaseRepository;

@RestController
@EnableWebMvc
@Transactional
@ControllerAdvice
public class PhaseController {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        ex.printStackTrace(writer);
        return "Any error comes here: " + stringWriter.toString();
    }

    @Autowired
    private PhaseRepository phaseRepository;

    Logger logger = Logger.getLogger(PhaseController.class.getName());

    @RequestMapping(path = "/phase/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(name = "id") Long id) {
        phaseRepository.deleteById(id);
    }

//    @RequestMapping(path = "/phase/update", method = RequestMethod.PUT)
//    public Map<String, Object> update(@RequestBody PhaseUpdate phase) {
//        logger.info("Request body phase: " + phase.getPhaseId() + " phase: " + phase.getPhaseId());
//        Optional<PhaseEntity> optional = service.update(phase.getPhaseId(), phase.getPhaseId());
//        Map<String, Object> map = new HashMap<>();
//        map.put("phase", phase);
//        map.put("result", optional.isPresent());
//        return map;
//    }

    @RequestMapping(path = "/phase", method = RequestMethod.POST)
    public Phase save(@RequestBody Phase phase) {
        logger.info("Request body: " + phase);
        PhaseEntity phaseEntity = new PhaseEntity();
        phaseEntity.setId(phase.getId());
        phaseEntity.setReference(phase.getReference());
        phaseEntity.setDetails(phase.getDetails());
        return phaseRepository.save(phaseEntity);
    }

    @RequestMapping(path = "/phase/getbyid/{id}", method = RequestMethod.GET)
    public Phase getById(@PathVariable("id") Long id) {
        logger.info("Path variable: " + id);
        Optional<PhaseEntity> phaseEntity = phaseRepository.findById(id);
        return phaseEntity.get();
    }

    @RequestMapping(path = "/phase/findbyid/{id}", method = RequestMethod.GET)
    public Phase findById(@PathVariable("id") Long id) {
        logger.info("Path variable: " + id);
        return phaseRepository.findById(id).get();
    }

    @RequestMapping(path = "/phase/findallbyid/{id}", method = RequestMethod.GET)
    public List<Phase> findAllById(@PathVariable("id") Long id) {
        logger.info("Path variable: " + id);
        return phaseRepository.findAllById(id).stream().map(entity -> {
          return entity;
        }).collect(Collectors.toList());
      }

    @RequestMapping(path = "/phase/findbyname/{name}", method = RequestMethod.GET)
    public Phase findByName(@PathVariable("name") String name) {
        logger.info("Path variable: " + name);
        return phaseRepository.findByReference(name);
    }

    @RequestMapping(path = "/phase/findallbyname/{name}", method = RequestMethod.GET)
    public List<Phase> findAllByName(@PathVariable("name") String name) {
        logger.info("Path variable: " + name);
        return phaseRepository.findAllByReference(name).stream().map(entity -> {
          return entity;
        }).collect(Collectors.toList());
    }

    @RequestMapping(path = "/phase/findbylabel/{label}", method = RequestMethod.GET)
    public Phase findByLabel(@PathVariable("label") String label) {
        logger.info("Path variable: " + label);
        return phaseRepository.findByDetails(label);
    }

    @RequestMapping(path = "/phase/findallbylabel/{label}", method = RequestMethod.GET)
    public List<PhaseEntity> findAllByLabel(
            @PathVariable("label") String label) {
        logger.info("Path variable: " + label);
        return phaseRepository.findAllByDetails(label).stream().map(entity -> {
          return entity;
        }).collect(Collectors.toList());
    }

    @RequestMapping(path = "/phase/findall", method = RequestMethod.GET)
    public List<Phase> findAll(
            @RequestParam("limit") Optional<Integer> limit,
            Principal principal) {
        logger.info("Request param: " + limit);
        return phaseRepository.findAll().stream().map(entity -> {
          return entity;
        }).collect(Collectors.toList());
    }

    @RequestMapping(path = "/phase/count", method = RequestMethod.GET)
    public Long count() {
        return phaseRepository.count();
    }
}
