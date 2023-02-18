package me.namila.awssqsdemo.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import io.awspring.cloud.autoconfigure.context.properties.AwsCredentialsProperties;
import io.awspring.cloud.autoconfigure.context.properties.AwsRegionProperties;
import io.awspring.cloud.core.region.RegionProvider;
import io.awspring.cloud.core.region.StaticRegionProvider;
import io.awspring.cloud.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;

@Configuration
public class AppConfig {

    @Value("${me.namila.sqs.messageCount}")
    private Integer messageCount;
    @Autowired
    private AwsCredentialsProperties awsCredentialsProperties;
    @Autowired
    private AwsRegionProperties awsRegionProperties;

    @Bean
    public ClientConfiguration clientConfiguration() {
        return new ClientConfiguration();
    }

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsCredentialsProperties.getAccessKey(), awsCredentialsProperties.getSecretKey()));
    }

    @Bean
    public RegionProvider regionProvider() {
        return new StaticRegionProvider(awsRegionProperties.getStatic());
    }

    @Bean
    public AmazonSQSAsync amazonSQS(AWSCredentialsProvider awsCredentialsProvider, RegionProvider regionProvider,
                                    ClientConfiguration clientConfiguration) {
        return AmazonSQSAsyncClientBuilder.standard().withCredentials(awsCredentialsProvider)
                .withClientConfiguration(clientConfiguration).withRegion(regionProvider.getRegion().getName()).build();
    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSqs, @Qualifier("MyCustomAsync") AsyncTaskExecutor asyncTaskExecutor) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSqs);
        factory.setMaxNumberOfMessages(messageCount);
        factory.setTaskExecutor(asyncTaskExecutor);
        return factory;
    }
}
