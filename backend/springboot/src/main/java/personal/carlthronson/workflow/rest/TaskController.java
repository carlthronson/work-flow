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
import personal.carlthronson.workflow.data.entity.StatusEntity;
import personal.carlthronson.workflow.data.entity.StoryEntity;
import personal.carlthronson.workflow.data.entity.TaskEntity;
import personal.carlthronson.workflow.jpa.repo.StatusRepository;
import personal.carlthronson.workflow.jpa.repo.TaskRepository;

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
    private TaskRepository service;

    Logger logger = Logger.getLogger(TaskController.class.getName());

    @Autowired
    private StatusRepository statusService;

    @RequestMapping(path = "/task/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(name = "id") Long id) {
        service.deleteById(id);
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

    @RequestMapping(path = "/task", method = RequestMethod.POST)
    public Task save(@RequestBody Task task) {
        logger.info("Request body: " + task);
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(task.getId());
        taskEntity.setReference(task.getReference());
        taskEntity.setDetails(task.getDetails());
        return service.save(taskEntity);
    }

    @RequestMapping(path = "/task/getbyid/{id}", method = RequestMethod.GET)
    public Task getById(@PathVariable("id") Long id) {
        logger.info("Path variable: " + id);
        Optional<TaskEntity> taskEntity = service.findById(id);
        return taskEntity.get();
    }

    @RequestMapping(path = "/task/findbyid/{id}", method = RequestMethod.GET)
    public Task findById(@PathVariable("id") Long id) {
        logger.info("Path variable: " + id);
        return service.findById(id).get();
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
        return service.findByReference(name);
    }

    @RequestMapping(path = "/task/findallbyname/{name}", method = RequestMethod.GET)
    public List<Task> findAllByName(@PathVariable("name") String name) {
        logger.info("Path variable: " + name);
        return service.findAllByReference(name).stream().map(entity -> {
          return entity;
        }).collect(Collectors.toList());
    }

    @RequestMapping(path = "/task/findbylabel/{label}", method = RequestMethod.GET)
    public Task findByLabel(@PathVariable("label") String label) {
        logger.info("Path variable: " + label);
        return service.findByDetails(label);
    }

    @RequestMapping(path = "/task/findallbylabel/{label}", method = RequestMethod.GET)
    public List<TaskEntity> findAllByLabel(
            @PathVariable("label") String label) {
        logger.info("Path variable: " + label);
        return service.findAllByDetails(label).stream().map(entity -> {
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
