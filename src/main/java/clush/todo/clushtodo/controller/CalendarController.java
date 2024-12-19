package clush.todo.clushtodo.controller;

import clush.todo.clushtodo.dto.IdRes;
import clush.todo.clushtodo.dto.ScheduleReq;

import clush.todo.clushtodo.entity.User;
import clush.todo.clushtodo.error.CustomException;
import clush.todo.clushtodo.service.CalendarService;
import clush.todo.clushtodo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;


@RestController
@RequestMapping("/schedule")
public class CalendarController {
    @Autowired
    CalendarService calSvc;

    @Autowired
    UserService userSvc;

    @PostMapping
    public ResponseEntity<?> addSchedule(@RequestBody ScheduleReq addReq) throws CustomException {
        userSvc.validate(addReq.getUserId());
        return ResponseEntity.ok(new IdRes(calSvc.addSchedule(addReq.getUserId(), addReq.getSchedule())));
    }

    @GetMapping("/month")
    public ResponseEntity<?> getSchedulesMonth(@RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                          @RequestParam(name = "userId") String userId) throws CustomException {
        userSvc.validate(userId);
        return ResponseEntity.ok(calSvc.getSchedules(date,userId));
    }
    @GetMapping("/day")
    public ResponseEntity<?> getSchedulesDay(@RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                               @RequestParam(name = "userId") String userId) throws CustomException {
        userSvc.validate(userId);
        return ResponseEntity.ok(calSvc.getOneDay(date,userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>  editSchedule(@PathVariable(name = "id") String cid, @RequestBody ScheduleReq newSchedule) throws CustomException {
        userSvc.validate(newSchedule.getUserId());
        Long cId = Long.parseLong(cid);
        return ResponseEntity.ok(new IdRes(calSvc.editSchedule(cId,newSchedule.getUserId(),newSchedule.getSchedule())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  deleteSchedule(@PathVariable(name = "id") String cid) throws CustomException {
        Long cId = Long.parseLong(cid);
        calSvc.deleteSchedule(cId);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
