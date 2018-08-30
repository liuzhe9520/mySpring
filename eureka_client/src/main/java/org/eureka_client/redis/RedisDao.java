package org.eureka_client.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisDao {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	public void setKey(String key,String value,long timeout){
		redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}
	
	public String getVal(String key){
		return redisTemplate.opsForValue().get(key);
	}
}
