package service.requests;

import models.Person;

public class AddRequest extends Request {

	private Person person;
	
	public AddRequest(Person person){
		super.requestName = "ADD";
		this.person = person;
	}

	public Person getPerson() {
		// TODO Auto-generated method stub
		return person;
	}
	
}

