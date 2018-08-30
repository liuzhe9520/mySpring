package org.config.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.config.client.config.TulingProperties;
import org.config.client.model.ResBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@SpringBootApplication
@RestController
@EnableConfigurationProperties(TulingProperties.class)
@EnableDiscoveryClient
@EnableEurekaClient
public class ConfigClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientApplication.class, args);
	}
	
//	@Value("${foo}")
//    String foo;
//    @RequestMapping(value = "/hi")
//    public String hi(){
//        return foo;
//    }
    
    private static final Logger LOG = Logger.getLogger(ConfigClientApplication.class.getName());


    @RequestMapping("/hi")
    public String home(){
        LOG.log(Level.INFO, "hi is being called");
        return "hi i'm miya!";
    }

    @RequestMapping("/miya")
    public String info(){
        LOG.log(Level.INFO, "info is being called");
        return restTemplate.getForObject("http://localhost:8763/info",String.class);
    }
    
    
    @Autowired(required = false)
    TulingProperties tulingProperties;
    
    @RequestMapping("/question")
    public String question(@RequestParam(value = "info",required = false)String info){
        LOG.log(Level.INFO, "question is being : " + info);
        if(!StringUtils.isEmpty(info)){
//        	try {
//				info = URLEncoder.encode(info, "utf-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
        	String url = tulingProperties.getUrl();
        	String key = tulingProperties.getKey();
        	url = url.replace("{apiKey}", key);
        	url = url.replace("{info}", info);
        	String res = restTemplate.getForObject(url,String.class);
        	LOG.log(Level.INFO, "tuling response : " + res);
        	if(res != null && !res.equals("")){
        		ResBody resBody = new Gson().fromJson(res, ResBody.class);
        		return resBody.getText() + "  " + resBody.getUrl();
        	}else{
        		return "服务好像挂了!";
        	}
        }
        
        return "question is empty!";
    }

    @Bean
    public RestTemplate getRestTemplate(){
    	return new RestTemplate();
    }
    @Autowired
    private RestTemplate restTemplate;


}
