package rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserMapper{
	//@JsonProperty("content")
	private int content;
	
	public UserMapper(@JsonProperty("content") String content){
		this.content = Integer.parseInt(content);
	}
	
	public void setContent(int content){
		this.content = content;
	}
	
	public int getContent(){
		return content;
	}
}
