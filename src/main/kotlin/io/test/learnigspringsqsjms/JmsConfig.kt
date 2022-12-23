package io.test.learnigspringsqsjms

import com.amazon.sqs.javamessaging.SQSConnectionFactory
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.sqs.AmazonSQSAsyncClient
import com.amazonaws.services.sqs.AmazonSQSClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.destination.DynamicDestinationResolver
import javax.jms.ConnectionFactory
import javax.jms.Session


@Configuration
@EnableJms
class JmsConfig {

    @Bean
    fun connectionFactory(): SQSConnectionFactory = SQSConnectionFactory.builder()
        .withRegion(Region.getRegion(Regions.US_EAST_1))
        .withNumberOfMessagesToPrefetch(1)
        .withAWSCredentialsProvider(DefaultAWSCredentialsProviderChain())
        .build()

    @Bean
    fun jmsListenerContainerFactory(connectionFactory: ConnectionFactory): DefaultJmsListenerContainerFactory = with(
        DefaultJmsListenerContainerFactory()
    ){
        setConnectionFactory(connectionFactory)
        setDestinationResolver(DynamicDestinationResolver())
        setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE)
        setConcurrency("50-50")
            this
    }

    @Bean
    fun defaultJmsTemplate(connectionFactory: ConnectionFactory): JmsTemplate = JmsTemplate(connectionFactory)

    @Bean
    fun sqsClient() : AmazonSQSAsyncClient = AmazonSQSAsyncClient(DefaultAWSCredentialsProviderChain())

}