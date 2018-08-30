package org.config.server.mq;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@Configuration
public class Config {

	@Bean
    public Queue queue() {
       return new ActiveMQQueue("sample.queue");
    }
}
