package service.requests;

public class ExitRequest extends Request{
	public ExitRequest(){
		super.requestName = "EXIT";
	}
	public ExitRequest(String request){
		super.requestName = request;
	}
}
