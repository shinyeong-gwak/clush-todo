package clush.todo.clushtodo.controller;

import clush.todo.clushtodo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@RestController
@RequestMapping("/sse")
public class NotiController {
    @Autowired
    NotificationService notiSvc;
    @GetMapping( path = "/connect",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@RequestParam("userId") String userId) {
        return notiSvc.connect(userId);
    }

}
