package org.config.client.model;

public class ResBody {
	private String code;
	private String text;
	private String url;
	
	public String getUrl() {
		if(url != null){
			url = url.replaceAll("\t", "");
		}else{
			url = "";
		}
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
