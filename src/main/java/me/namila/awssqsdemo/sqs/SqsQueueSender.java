package me.namila.awssqsdemo.sqs;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.core.TopicMessageChannel;
import lombok.extern.slf4j.Slf4j;
import me.namila.awssqsdemo.model.SampleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class SqsQueueSender {

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${me.namila.sqs.name}")
    private String queueName;

    @Autowired
    public SqsQueueSender(AmazonSQSAsync amazonSQSAsync) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
    }

    public void send(SampleModel message) {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(TopicMessageChannel.MESSAGE_GROUP_ID_HEADER, "1");
        headers.put(TopicMessageChannel.MESSAGE_DEDUPLICATION_ID_HEADER, message.hashCode() + "");
        this.queueMessagingTemplate.convertAndSend(queueName, message, headers);
        log.info("Message sent: {}", message.toString());

    }
}
