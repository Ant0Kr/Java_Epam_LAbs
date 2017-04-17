package service.requests;

import client.MainClient;
import models.User;

public class UserValidateRequest extends Request{
	
	private User client;
	
	public UserValidateRequest(User client){
		super.requestName = "USERVALIDATE";
		this.client = client;
		
	}

	public User getClient(){
		return client;
	}
}
