package me.namila.awssqsdemo.sqs;

import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import me.namila.awssqsdemo.service.SqsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SqsQueueReceiver {

    @Autowired
    private SqsService service;

    @SqsListener(value = "simple-sqs.fifo", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(String message, @Header("MessageGroupId") String id) throws InterruptedException {
        service.handleMessage(message, id);
    }


}
