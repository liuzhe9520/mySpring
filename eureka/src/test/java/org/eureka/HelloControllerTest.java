package org.eureka;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/*@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)*/
public class HelloControllerTest {

	@LocalServerPort
	private int port;
	
	private URL baseUrl;
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
/*	@Before
	public void setUp() throws Exception{
		this.baseUrl = new URL("http://localhost:" + port + "/hi");
	}*/
	
/*	@Test
	public void getHi(){
		ResponseEntity<String> res = testRestTemplate.getForEntity(baseUrl.toString(), String.class);
		System.out.println(baseUrl);
		assert(res.getBody().equals("hello world"));
	}*/
}
