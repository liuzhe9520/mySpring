package org.eureka.controller;


import javax.servlet.http.HttpServletRequest;

import org.eureka.bean.ConfigBean;
import org.eureka.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableConfigurationProperties({ConfigBean.class,User.class})
public class LucyController {

	@Autowired
	ConfigBean configBean;
	
	@Autowired
	User user;
	
	@GetMapping("/lucy")
	public String lucy(){
		
		return configBean.getGreeting() + "-" +configBean.getName() + "_" + configBean.getUuid()
		+ "_" + configBean.getMax();
	}
	
	@Value("${java.home}")
	private String javaHome;
	
	@GetMapping("/user")
	public String user(){
		return "name :" + user.getName() + ",age" + user.getAge();
	}
	
	@GetMapping("/ip")
	public String getIp(HttpServletRequest request){
		
		return request.getRemoteAddr();
	}
	
	@GetMapping("/ts/{seq}")
	public String getTs(HttpServletRequest request,@PathVariable("seq")String seq){
		String no = request.getParameter("no");
		String url = request.getRequestURL().toString();
		System.out.println("url:" + url);
		System.out.println("seq:" + seq);
		System.out.println("no:" + no);
		return request.getRemoteAddr();
	}
}
