package service.requests;

import models.User;

public class AddUserRequest extends Request{
	
	private User user;
	
	public AddUserRequest(User user){
		super.requestName = "ADDUSER";
		this.user = user;
	}
	
	public User getUser(){
		return user;
	}
}
