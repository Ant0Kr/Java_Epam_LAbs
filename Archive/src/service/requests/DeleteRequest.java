package service.requests;

public class DeleteRequest extends Request{
	
	private String surname;
	private String name;
	private String fathername;
	private String email;
	
	public DeleteRequest(String surname,String name,String fathername,String email){
		super.requestName = "DELETE";
		this.surname = surname;
		this.name = name;
		this.fathername = fathername;
		this.email = email;
	}
	
	public String getSurname(){
		return surname;
	}
	
	public String getName(){
		return name;
	}
	
	public String getFathername(){
		return fathername;
	}
	
	public String getEMail(){
		return email;
	}
	
}
