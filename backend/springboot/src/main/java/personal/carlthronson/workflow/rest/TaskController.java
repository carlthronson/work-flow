package personal.carlthronson.workflow.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

//import personal.carlthronson.dl.be.dto.TaskUpdate;
//import personal.carlthronson.dl.be.entity.JobEntity;
//import personal.carlthronson.dl.be.svc.StatusService;
//import personal.carlthronson.dl.be.svc.TaskService;
import personal.carlthronson.workflow.data.core.Task;
import personal.carlthronson.workflow.data.core.TaskUpdate;
import personal.carlthronson.workflow.data.entity.TaskEntity;
import personal.carlthronson.workflow.jpa.repo.StatusRepository;

@RestController
@EnableWebMvc
@Transactional
@ControllerAdvice
public class TaskController {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        ex.printStackTrace(writer);
        return "Any error comes here: " + stringWriter.toString();
    }

    @Autowired
    private TaskService service;

    Logger logger = Logger.getLogger(TaskController.class.getName());

    @Autowired
    private StatusRepository statusRepository;

    @RequestMapping(path = "/task/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(name = "id") Long id) {
        service.delete(id);
    }

//    @RequestMapping(path = "/task/update", method = RequestMethod.PUT)
//    public Map<String, Object> update(@RequestBody TaskUpdate task) {
//        logger.info("Request body task: " + task.getTaskId() + " status: " + task.getStatusId());
//        Optional<TaskEntity> optional = service.update(task.getTaskId(), task.getStatusId());
//        Map<String, Object> map = new HashMap<>();
//        map.put("task", task);
//        map.put("result", optional.isPresent());
//        return map;
//    }

    @RequestMapping(path = "/task/update", method = RequestMethod.PUT)
    public Map<String, Object> update(@RequestBody TaskUpdate task) {
        logger.info("Request body task: " + task.getTaskId() + " status: " + task.getStatusId());
        Optional<TaskEntity> optional = service.update(task.getTaskId(), task.getStatusId());
        Map<String, Object> map = new HashMap<>();
        map.put("task", task);
        map.put("result", optional.isPresent());
        return map;
    }

    @RequestMapping(path = "/task/getbyid/{id}", method = RequestMethod.GET)
    public Task getById(@PathVariable("id") Long id) {
        logger.info("Path variable: " + id);
        TaskEntity taskEntity = service.findById(id);
        return taskEntity;
    }

    @RequestMapping(path = "/task/findbyid/{id}", method = RequestMethod.GET)
    public Task findById(@PathVariable("id") Long id) {
        logger.info("Path variable: " + id);
        return service.findById(id);
    }

    @RequestMapping(path = "/task/findallbyid/{id}", method = RequestMethod.GET)
    public List<Task> findAllById(@PathVariable("id") Long id) {
        logger.info("Path variable: " + id);
        return service.findAllById(id).stream().map(entity -> {
          return entity;
        }).collect(Collectors.toList());
      }

    @RequestMapping(path = "/task/findbyname/{name}", method = RequestMethod.GET)
    public Task findByName(@PathVariable("name") String name) {
        logger.info("Path variable: " + name);
        return service.findByName(name);
    }

    @RequestMapping(path = "/task/findallbyname/{name}", method = RequestMethod.GET)
    public List<Task> findAllByName(@PathVariable("name") String name) {
        logger.info("Path variable: " + name);
        return service.findAllByName(name).stream().map(entity -> {
          return entity;
        }).collect(Collectors.toList());
    }

    @RequestMapping(path = "/task/findbylabel/{label}", method = RequestMethod.GET)
    public Task findByLabel(@PathVariable("label") String label) {
        logger.info("Path variable: " + label);
        return service.findByLabel(label);
    }

    @RequestMapping(path = "/task/findallbylabel/{label}", method = RequestMethod.GET)
    public List<TaskEntity> findAllByLabel(
            @PathVariable("label") String label) {
        logger.info("Path variable: " + label);
        return service.findAllByLabel(label).stream().map(entity -> {
          return entity;
        }).collect(Collectors.toList());
    }

    @RequestMapping(path = "/task/findall", method = RequestMethod.GET)
    public List<Task> findAll(
            @RequestParam("limit") Optional<Integer> limit,
            Principal principal) {
        logger.info("Request param: " + limit);
        return service.findAll().stream().map(entity -> {
          return entity;
        }).collect(Collectors.toList());
    }

    @RequestMapping(path = "/task/count", method = RequestMethod.GET)
    public Long count() {
        return service.count();
    }
}
