package webapplication.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdMapper{
	//@JsonProperty("content")
	private int content;
	
	public IdMapper(@JsonProperty("content") String content){
		this.content = Integer.parseInt(content);
	}
	
	public void setContent(int content){
		this.content = content;
	}
	
	public int getContent(){
		return content;
	}
}
