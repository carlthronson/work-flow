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

import personal.carlthronson.workflow.data.core.Status;
import personal.carlthronson.workflow.data.entity.StatusEntity;
import personal.carlthronson.workflow.jpa.repo.StatusRepository;

@RestController
@EnableWebMvc
@Transactional
@ControllerAdvice
public class StatusController {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        ex.printStackTrace(writer);
        return "Any error comes here: " + stringWriter.toString();
    }

    @Autowired
    private StatusRepository statusRepository;

    Logger logger = Logger.getLogger(StatusController.class.getName());

    @RequestMapping(path = "/status/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(name = "id") Long id) {
        statusRepository.deleteById(id);
    }

//    @RequestMapping(path = "/status/update", method = RequestMethod.PUT)
//    public Map<String, Object> update(@RequestBody StatusUpdate status) {
//        logger.info("Request body status: " + status.getStatusId() + " status: " + status.getStatusId());
//        Optional<StatusEntity> optional = service.update(status.getStatusId(), status.getStatusId());
//        Map<String, Object> map = new HashMap<>();
//        map.put("status", status);
//        map.put("result", optional.isPresent());
//        return map;
//    }

    @RequestMapping(path = "/status", method = RequestMethod.POST)
    public Status save(@RequestBody Status status) {
        logger.info("Request body: " + status);
        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setId(status.getId());
        statusEntity.setReference(status.getReference());
        statusEntity.setDetails(status.getDetails());
        return statusRepository.save(statusEntity);
    }

    @RequestMapping(path = "/status/getbyid/{id}", method = RequestMethod.GET)
    public Status getById(@PathVariable("id") Long id) {
        logger.info("Path variable: " + id);
        Optional<StatusEntity> statusEntity = statusRepository.findById(id);
        return statusEntity.get();
    }

    @RequestMapping(path = "/status/findbyid/{id}", method = RequestMethod.GET)
    public Status findById(@PathVariable("id") Long id) {
        logger.info("Path variable: " + id);
        return statusRepository.findById(id).get();
    }

    @RequestMapping(path = "/status/findallbyid/{id}", method = RequestMethod.GET)
    public List<Status> findAllById(@PathVariable("id") Long id) {
        logger.info("Path variable: " + id);
        return statusRepository.findAllById(id).stream().map(entity -> {
          return entity;
        }).collect(Collectors.toList());
      }

    @RequestMapping(path = "/status/findbyname/{name}", method = RequestMethod.GET)
    public Status findByName(@PathVariable("name") String name) {
        logger.info("Path variable: " + name);
        return statusRepository.findByReference(name);
    }

    @RequestMapping(path = "/status/findallbyname/{name}", method = RequestMethod.GET)
    public List<Status> findAllByName(@PathVariable("name") String name) {
        logger.info("Path variable: " + name);
        return statusRepository.findAllByReference(name).stream().map(entity -> {
          return entity;
        }).collect(Collectors.toList());
    }

    @RequestMapping(path = "/status/findbylabel/{label}", method = RequestMethod.GET)
    public Status findByLabel(@PathVariable("label") String label) {
        logger.info("Path variable: " + label);
        return statusRepository.findByDetails(label);
    }

    @RequestMapping(path = "/status/findallbylabel/{label}", method = RequestMethod.GET)
    public List<StatusEntity> findAllByLabel(
            @PathVariable("label") String label) {
        logger.info("Path variable: " + label);
        return statusRepository.findAllByDetails(label).stream().map(entity -> {
          return entity;
        }).collect(Collectors.toList());
    }

    @RequestMapping(path = "/status/findall", method = RequestMethod.GET)
    public List<Status> findAll(
            @RequestParam("limit") Optional<Integer> limit,
            Principal principal) {
        logger.info("Request param: " + limit);
        return statusRepository.findAll().stream().map(entity -> {
          return entity;
        }).collect(Collectors.toList());
    }

    @RequestMapping(path = "/status/count", method = RequestMethod.GET)
    public Long count() {
        return statusRepository.count();
    }
}
