package models;



public class User {
	private String login;
	private String password;
	public static enum  ParserName{
		JDOM,
		DOM,
		SAX,
		STAX
		
	};
	public static enum Rights {
		ADMINISTRATOR, 
		SENIORUSER,
		USER,
	};

	private ParserName parser;
	private Rights rights;
	
	public User(String login,String password,Rights rights,ParserName parser){
		this.login = login;
		this.password = password;
		this.rights = rights;
		this.parser = parser;
	}
	
	public String getLogin(){
		return login;
	}
	
	public String getPassword(){
		return password;
	}
	
	public Rights getRights(){
		return rights;
	}

	
	public ParserName getParser(){
		return parser;
	}
	
	public void setLogin(String login)
	{
		this.login = login;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setRights(Rights rights)
	{
		this.rights = rights;
	}
	
	public void setParser(ParserName parser)
	{
		this.parser = parser;
	}
}
