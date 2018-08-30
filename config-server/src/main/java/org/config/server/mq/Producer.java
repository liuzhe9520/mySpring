package org.config.server.mq;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
//@EnableScheduling
public class Producer {

	@Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    //@Scheduled(fixedDelay=3000)//每3s执行1次
    public void send() {

       this.jmsMessagingTemplate.convertAndSend(this.queue, "hi,activeMQ");

    }
}
