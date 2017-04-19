package service.requests;

import models.Person;
import models.User;

public class Request {
	
	String requestName;
	Person oldPerson;
	Person newPerson;
	User user;
	String surname;
	String name;
	String fathername;
	String eMail;
	
	public Request(String requestName,Person old,Person newP,User user){
		this.requestName = requestName;
		this.oldPerson = old;
		this.newPerson = newP;
		this.user = user;
	}
	
	public String getRequestName(){
		return requestName;
	}
	
	public Person getOldPerson(){
		return oldPerson;
	}

	public Person getNewPerson(){
		return newPerson;
	}
	
	public User getUser(){
		return user;
	}
	
}
