package org.eureka_client.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.eureka_client.model.WeatherResponse;
import org.eureka_client.redis.RedisDao;
import org.eureka_client.service.WeatherDataService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

	@Autowired
	RedisDao redisDao;
	
	@Autowired
	private RestTemplate restTemplate;

	private final String WEATHER_API = "http://wthrcdn.etouch.cn/weather_mini";
	
	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		String key = "cid:" + cityId;
		String val = redisDao.getVal(key);
		String uri = WEATHER_API + "?citykey=" + cityId;
		ObjectMapper mapper = new ObjectMapper();
		if(val != null){
			try {
				return mapper.readValue(val, WeatherResponse.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		WeatherResponse weatherData = this.doGetWeatherData(uri);
		if(weatherData != null){
			String convertValue = mapper.convertValue(weatherData,String.class);
			redisDao.setKey(key, convertValue, 100);
		}
		
		return weatherData;
	}

	@Override
	public WeatherResponse getDataByCityName(String cityName) {
		String uri = WEATHER_API + "?city=" + cityName;
		String key = "cname:" + cityName;
		String val = redisDao.getVal(key);
		ObjectMapper mapper = new ObjectMapper();
		if(val != null){
			try {
				return mapper.readValue(val, WeatherResponse.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		WeatherResponse weatherData = null; 
		String convertValue = "{\"data\":{\"yesterday\":{\"date\":\"7日星期四\",\"high\":\"高温 33℃\",\"fx\":\"东风\",\"low\":\"低温 22℃\",\"fl\":\"<![CDATA[<3级]]>\",\"type\":\"多云\"},\"city\":\"北京\",\"aqi\":\"48\",\"forecast\":[{\"date\":\"8日星期五\",\"high\":\"高温 32℃\",\"fengli\":\"<![CDATA[<3级]]>\",\"low\":\"低温 25℃\",\"fengxiang\":\"西南风\",\"type\":\"多云\"},{\"date\":\"9日星期六\",\"high\":\"高温 23℃\",\"fengli\":\"<![CDATA[<3级]]>\",\"low\":\"低温 17℃\",\"fengxiang\":\"北风\",\"type\":\"雷阵雨\"},{\"date\":\"10日星期天\",\"high\":\"高温 28℃\",\"fengli\":\"<![CDATA[<3级]]>\",\"low\":\"低温 17℃\",\"fengxiang\":\"南风\",\"type\":\"多云\"},{\"date\":\"11日星期一\",\"high\":\"高温 29℃\",\"fengli\":\"<![CDATA[<3级]]>\",\"low\":\"低温 19℃\",\"fengxiang\":\"东南风\",\"type\":\"多云\"},{\"date\":\"12日星期二\",\"high\":\"高温 31℃\",\"fengli\":\"<![CDATA[<3级]]>\",\"low\":\"低温 20℃\",\"fengxiang\":\"南风\",\"type\":\"多云\"}],\"ganmao\":\"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。\",\"wendu\":\"30\"},\"status\":1000,\"desc\":\"OK\"}";
		//String convertValue	= this.httpRequest(uri,"GET",null);
		if(convertValue != null){
			try {
				weatherData = mapper.readValue(convertValue,WeatherResponse.class);
			}catch (IOException e) {
				e.printStackTrace();
			}
			redisDao.setKey(key, convertValue, 100);
		}else{
			weatherData = new WeatherResponse();
		}
		return weatherData;
	}

	private WeatherResponse doGetWeatherData(String uri) {
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		String strBody = null;

		strBody = response.getBody();

		ObjectMapper mapper = new ObjectMapper();
		WeatherResponse weather = null;

		try {
			weather = mapper.readValue(strBody, WeatherResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return weather;
	}
	
	public String httpRequest(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpUrlConn = (HttpURLConnection) urlConnection;
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setConnectTimeout(5000);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
//			httpUrlConn.setRequestProperty(\"Content-type\", \"application/x-java-serialized-object\");
			httpUrlConn.setRequestProperty("Content-Type", "application/json; charset=utf-8"); 

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

//			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

}
