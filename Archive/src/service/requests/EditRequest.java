package service.requests;

import models.Person;

public class EditRequest extends Request{

	private Person oldPerson;
	private Person newPerson;
	
	public EditRequest(Person oldPerson,Person newPerson){
		super.requestName = "EDIT";
		this.oldPerson = oldPerson;
		this.newPerson = newPerson;
	}
	
	public Person getNewPerson(){
		return newPerson;
	}
	
	public Person getOldPerson(){
		return oldPerson;
	}
}
