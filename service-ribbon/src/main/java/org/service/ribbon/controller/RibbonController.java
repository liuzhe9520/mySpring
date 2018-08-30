package org.service.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RibbonController {
	
	@Autowired
	private LoadBalancerClient loadBalancer;
	
	@GetMapping("/testRibbon")
	public String testRibbon(@RequestParam( value = "name" , required = false , defaultValue = "stores") String name){
		ServiceInstance instance = loadBalancer.choose(name);
		return instance.getHost() + ":" + instance.getPort();
	}
}
