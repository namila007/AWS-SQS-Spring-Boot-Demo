package me.namila.awssqsdemo.controller;


import me.namila.awssqsdemo.model.SampleModel;
import me.namila.awssqsdemo.service.SqsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1")
public class Controller {

    @Autowired
    private SqsService service;

    @PostMapping("/publish")
    public ResponseEntity<String> createSqsMessage(@RequestBody SampleModel sampleModel) {
        service.sendMsg(sampleModel);
        return ResponseEntity.ok("sent");
    }
}
