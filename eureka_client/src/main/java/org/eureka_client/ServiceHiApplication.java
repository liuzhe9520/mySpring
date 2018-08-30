package org.eureka_client;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eureka_client.model.WeatherResponse;
import org.eureka_client.service.WeatherDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
@RestController
public class ServiceHiApplication {

	public static void main(String[] args) {

		SpringApplication.run(ServiceHiApplication.class,args);
	}
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
	
	private static final Logger LOG = Logger.getLogger(ServiceHiApplication.class.getName());
	
/*	@Value("${server.port}")
    String port;
    @RequestMapping("/hi")
	public String hello(@RequestParam String name){
		
		return "hello:" + name + "; port is :" + port;
	}*/
    
    @RequestMapping("/hi")
    public String callHome(){
        LOG.log(Level.INFO, "calling trace service-hi  ");
        return restTemplate.getForObject("http://localhost:8881/miya", String.class);
    }
    @RequestMapping("/info")
    public String info(){
        LOG.log(Level.INFO, "calling trace service-hi ");

        return "i'm service-hi";

    }
    
    @Autowired
	private WeatherDataService weatherDataService;
	
	@GetMapping("/cityId/{cityId}")
	public WeatherResponse getReportByCityId(@PathVariable("cityId") String cityId) {
		return weatherDataService.getDataByCityId(cityId);
	}
	
	@GetMapping("/cityName/{cityName}")
	public WeatherResponse getReportByCityName(@PathVariable("cityName") String cityName) {
		return weatherDataService.getDataByCityName(cityName);
	}

    @Bean
    public AlwaysSampler defaultSampler(){
        return new AlwaysSampler();
    }

}
