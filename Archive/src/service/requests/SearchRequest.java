package service.requests;

import models.Person;

public class SearchRequest extends Request{

	private Person person;
	
	public SearchRequest(Person person){
		super.requestName = "SEARCH";
		this.person = person;
	}
	
	public Person getPerson(){
		return person;
	}
}
