package service.requests;

public class ShowAllRequest extends Request{
	
	public ShowAllRequest(){
		super.requestName = "SHOW_ALL";
	}
	public ShowAllRequest(String request){
		super.requestName = request;
	}
}
