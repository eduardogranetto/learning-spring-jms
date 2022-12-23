package io.test.learnigspringsqsjms

import com.amazon.sqs.javamessaging.message.SQSMessage
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@Component
class MessageTestListener {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @JmsListener(destination = "test-queue")
    @Throws(Exception::class)
    fun receive(message: SQSMessage) {
        logger.info("Starting... ${Thread.currentThread()}")
        Observable.just(message)
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io()).toBlocking().subscribe {
                logger.info("Completed.. ${Thread.currentThread()}")
            }
        logger.info("Consumed.. ${Thread.currentThread()}")
    }

}