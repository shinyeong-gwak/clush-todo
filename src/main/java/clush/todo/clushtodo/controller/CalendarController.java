package clush.todo.clushtodo.controller;

import clush.todo.clushtodo.dto.Schedule;
import clush.todo.clushtodo.dto.ScheduleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(name = "/schedule")
public class CalendarController {

    @PostMapping
    public ResponseEntity<Object> addSchedule(@RequestBody ScheduleDTO.AddReq addReq) {
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Schedule>> getSchedules(@RequestParam(name = "userId") String userId){
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object>  editSchedule(@PathVariable(name = "iid") String id, Schedule newSchedule) {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object>  deleteSchedule(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
