package me.namila.awssqsdemo.service;

import lombok.extern.slf4j.Slf4j;
import me.namila.awssqsdemo.model.SampleModel;
import me.namila.awssqsdemo.sqs.SqsQueueSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SqsService {

    @Autowired
    private SqsQueueSender sqsQueueSender;

    public void sendMsg(SampleModel sampleModel) {
        this.sqsQueueSender.send(sampleModel);
    }

    @Async("MyCustomAsync2")
    public void handleMessage(String message, @Header("MessageGroupId") String id) throws InterruptedException {
        log.info("message received msg: {}, MessageGroupId: {}", message, id);
        Thread.sleep(5000);
    }

}
