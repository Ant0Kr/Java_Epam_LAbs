package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import models.User;
import models.User.ParserName;
import models.User.Rights;

public class MainClient{
	
	private String address;
	private int PORT;
	private DataInputStream in;
	private DataOutputStream out;
	private User infoUser;
	private Socket socket;
	
	public MainClient(String login,String pass,Rights rights,ParserName parser){
		address = "127.0.0.1";
		PORT = 5555;
		infoUser = new User(login,pass,rights,parser);
	}
	
	public Boolean ClientConnection() throws IOException{
		InetAddress IPadress = InetAddress.getByName(address);
		try{
			socket = new Socket(IPadress,PORT);
		}catch(Exception e){
			System.out.println("Server disconnected!");
			return false;
		}
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		return true;
	}
	
	public DataInputStream getInputStream(){
		return in;
	}
	public DataOutputStream getOutputStream(){
		return out;
	}
	
	public User getUser(){
		return infoUser;
	}
	
	public void setUser(User user){
		this.infoUser = user;
	}
		
}