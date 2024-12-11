package clush.todo.clushtodo.controller;

import clush.todo.clushtodo.dto.IdRes;
import clush.todo.clushtodo.dto.Schedule;
import clush.todo.clushtodo.dto.ScheduleDTO;

import clush.todo.clushtodo.entity.User;
import clush.todo.clushtodo.error.CustomException;
import clush.todo.clushtodo.service.CalendarService;
import clush.todo.clushtodo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;


@RestController
@RequestMapping(name = "/schedule")
public class CalendarController {
    @Autowired
    CalendarService calSvc;

    @Autowired
    UserService userSvc;

    @PostMapping
    public ResponseEntity<?> addSchedule(@RequestBody ScheduleDTO.AddReq addReq) throws CustomException {
        User user = userSvc.validate(addReq.getUserId());
        return ResponseEntity.ok(new IdRes(calSvc.addSchedule(user,addReq.getSchedule())));
    }

    @GetMapping("/month")
    public ResponseEntity<?> getSchedulesMonth(@RequestParam(name = "date") LocalDate date,
                                          @RequestParam(name = "userId") String userId) throws CustomException {
        userSvc.validate(userId);
        return ResponseEntity.ok(calSvc.getSchedules(date,userId));
    }
    @GetMapping("/day")
    public ResponseEntity<?> getSchedulesDay(@RequestParam(name = "date") LocalDate date,
                                               @RequestParam(name = "userId") String userId) throws CustomException {
        userSvc.validate(userId);
        return ResponseEntity.ok(calSvc.getOneDay(date,userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object>  editSchedule(@PathVariable(name = "id") String cid, Schedule newSchedule) throws CustomException {
        UUID cId = UUID.fromString(cid);
        return ResponseEntity.ok(new IdRes(calSvc.editSchedule(cId,newSchedule)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object>  deleteSchedule(@PathVariable(name = "id") String cid) throws CustomException {
        UUID cId = UUID.fromString(cid);
        calSvc.deleteSchedule(cId);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
