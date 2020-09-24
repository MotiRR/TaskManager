package org.vtb.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vtb.controller.classes.ResponseMessage;
import org.vtb.entity.Subscribe;
import org.vtb.entity.User;
import org.vtb.service.SubscribeService;
import org.vtb.service.TaskService;
import org.vtb.service.UserService;
import org.vtb.utills.ParseHeader;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/subscribe")
public class SubscribeController {

    private ParseHeader parseHeader;

    private SubscribeService subscribeService;

    private UserService userService;

    private TaskService taskService;

    @GetMapping
    public Boolean getSubscribe(@RequestHeader Map<String, String> headers,
                                @RequestParam(value = "taskId") Long taskId) {
        parseHeader.setHeaders(headers);
        User user = userService.findByLogin(parseHeader.getLogin());
        if (user == null) return false;
        Subscribe subscribe = subscribeService.findSubscribeByUserIdAndTaskId(user.getId(), taskId);
        return subscribe.getIsSubscribe();
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> subscribe(@RequestHeader Map<String, String> headers,
                                                     @RequestParam(value = "taskId") Long taskId) {
        String message = "";
        parseHeader.setHeaders(headers);
        User user = userService.findByLogin(parseHeader.getLogin());
        try {
            Subscribe subscribe = subscribeService.createSubscribe(user.getId(), taskId);
            message = "Пользователь " + parseHeader.getLogin() + "подписан";
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Подписка не была офорлена";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping
    public ResponseEntity<ResponseMessage> subscribe(@RequestHeader Map<String, String> headers,
                                                     @RequestParam(value = "taskId") Long taskId,
                                                     @RequestParam(value = "isSubscribe") Boolean isSubscribe) {
        String message = "";
        parseHeader.setHeaders(headers);
        User user = userService.findByLogin(parseHeader.getLogin());
        try {
            Subscribe subscribe = subscribeService.findSubscribeByUserIdAndTaskId(user.getId(), taskId);
            if (subscribe == null) {
                throw new RuntimeException("Подписка не найдена");
            }
            subscribe.setIsSubscribe(isSubscribe);
            subscribeService.saveOrUpdate(subscribe);
            message = "Подписка пользователя " + parseHeader.getLogin() + "обновлена";
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Подписка не была изменена";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

}
