package org.config.server;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Configuration
@EnableDiscoveryClient
@EnableEurekaClient
@EnableConfigServer
@RestController
@SpringBootApplication
public class ConfigServerApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(ConfigServerApplication.class, args);

	}
	
	@Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

	@GetMapping("/hi")
	public String hi(@RequestParam("msg")String msg){
		Map map = new HashMap();
		map.put("msg", msg);
		this.jmsMessagingTemplate.convertAndSend(this.queue, map);
		return "success";
	}

}
