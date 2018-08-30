package org.config.server.mq;

import java.util.Map;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

@Component
public class Consumer {

	@JmsListener(destination = "sample.queue")
    public void receiveQueue(Map map) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i = 0 ; i <3 ; i ++){
			System.out.println("===============" + Thread.currentThread().getName() + ",msg:" + map.get("msg"));
		}
    }
	
	@JmsListener(destination = "sample.queue")
    public void receiveQueue2(Map map) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i = 0 ; i <3 ; i ++){
			System.out.println("===============" + Thread.currentThread().getName() + "receiveQueue2,msg:" + map.get("msg"));
		}
    }
}
