package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Vector;

import models.Person;
import models.User;
import models.User.ParserName;
import models.User.Rights;
import service.SerializeMaker;
import service.requests.AddRequest;
import service.requests.AddUserRequest;
import service.requests.DeleteRequest;
import service.requests.EditRequest;
import service.requests.SearchRequest;
import service.requests.UserValidateRequest;

public class ServerThread extends Thread {

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private static Vector<ServerThread> allAttachedThreads = new Vector<ServerThread>();

	public ServerThread(Socket socket) throws IOException {
		this.socket = socket;
		allAttachedThreads.addElement(this);
		in = new DataInputStream(this.socket.getInputStream());
		out = new DataOutputStream(this.socket.getOutputStream());
	}

	public void run() {

		while (true) {
			String serializeObj = new String();
			String requestName = new String();
			try {
				serializeObj = in.readUTF();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				return;
			}
			int i = 0;
			int counter = 0;
			while (true) {
				if (serializeObj.charAt(i++) != '.') {
					continue;
				} else {
					counter++;
					if (counter == 2) {
						while (serializeObj.charAt(i) != '>')
							requestName = requestName + serializeObj.charAt(i++);
						break;
					}

				}
			}
			switch (requestName) {
			case "ShowAllRequest":
				synchronized (MainServer.getCatalog()) {
					try {

						out.writeUTF(SerializeMaker.serializeToXML(MainServer.getCatalog()));

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case "ExitRequest":
				return;

			case "DeleteRequest":
				DeleteRequest object = SerializeMaker.deserializeFromXML(serializeObj);
				LinkedList<Person> catalog = MainServer.getCatalog();
				synchronized (catalog) {
					for (int i1 = 0; i1 < catalog.size(); i1++) {

						if (catalog.get(i1).getSurname().equals(object.getSurname())
								&& catalog.get(i1).getName().equals(object.getName())
								&& catalog.get(i1).getFathername().equals(object.getFathername())
								&& catalog.get(i1).getEMail().equals(object.getEMail())) {
							File file = new File("Data\\Archive\\" + catalog.get(i1).getSurname() + "_"
									+ catalog.get(i1).getName() + "_" + catalog.get(i1).getFathername() + ".zip");
							if (file.delete())
								catalog.remove(i1);
							else
								System.out.println("Error of delete!File not found!");
							break;

						}
					}
				}
				try {
					out.writeUTF(SerializeMaker.serializeToXML(catalog));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "AddRequest":
				AddRequest addObject = SerializeMaker.deserializeFromXML(serializeObj);
				Person person = addObject.getPerson();
				LinkedList<Person> list = MainServer.getCatalog();
				synchronized (list) {
					list.add(person);
					SerializeMaker.saveXML(person,person.getSurname()+"_"+person.getName()+"_"+person.getFathername(),"Data\\Archive\\");
					try {
						out.writeUTF(SerializeMaker.serializeToXML(list));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			
			case "EditRequest":
				EditRequest editObject = SerializeMaker.deserializeFromXML(serializeObj);
				Person newPerson = editObject.getNewPerson();
				Person oldPerson = editObject.getOldPerson();
				LinkedList<Person> llist = MainServer.getCatalog();
				
				synchronized (llist) {
					
					for(int j = 0;j<llist.size();j++){
						if(llist.get(j).getSurname().equals(oldPerson.getSurname()) &&
								llist.get(j).getName().equals(oldPerson.getName()) &&
								llist.get(j).getFathername().equals(oldPerson.getFathername()) &&
								llist.get(j).getPhone().equals(oldPerson.getPhone()) &&
								llist.get(j).getEMail().equals(oldPerson.getEMail()) &&
								llist.get(j).getNameJob().equals(oldPerson.getNameJob()) &&
								llist.get(j).getExperienceJob().equals(oldPerson.getExperienceJob())){
							
							File file = new File("Data\\Archive\\" + llist.get(j).getSurname() + "_"
									+ llist.get(j).getName() + "_" + llist.get(j).getFathername() + ".zip");
							file.delete();
							
							if(!newPerson.getSurname().equals(""))
								llist.get(j).setSurname(newPerson.getSurname());
							if(!newPerson.getName().equals(""))
								llist.get(j).setName(newPerson.getName());
							if(!newPerson.getFathername().equals(""))
								llist.get(j).setFathername(newPerson.getFathername());
							if(!newPerson.getPhone().equals(""))
								llist.get(j).setPhone(newPerson.getPhone());
							if(!newPerson.getEMail().equals(""))
								llist.get(j).setEMail(newPerson.getEMail());
							if(!newPerson.getNameJob().equals(""))
								llist.get(j).setNameJob(newPerson.getNameJob());
							if(!newPerson.getExperienceJob().equals(""))
								llist.get(j).setExperienceJob(newPerson.getExperienceJob());
							
							SerializeMaker.saveXML(llist.get(j),llist.get(j).getSurname()+"_"+llist.get(j).getName()+"_"+llist.get(j).getFathername(),"Data\\Archive\\");
							try {
								out.writeUTF(SerializeMaker.serializeToXML(llist));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;

							
						}
					}
					
				}
				break;
			case "SearchRequest":
				SearchRequest searchObject = SerializeMaker.deserializeFromXML(serializeObj);
				Person searchPerson = searchObject.getPerson();
				LinkedList<Person> myList = MainServer.getCatalog();
				LinkedList<Person> finishList = new LinkedList<Person>();
				synchronized(myList){
					for(int k = 0;k<myList.size();k++){
						if(!searchPerson.getSurname().equals("")){
							if(!myList.get(k).getSurname().equals(searchPerson.getSurname()))
								continue;
						}
						if(!searchPerson.getName().equals("")){
							if(!myList.get(k).getName().equals(searchPerson.getName()))
								continue;
						}
						if(!searchPerson.getFathername().equals("")){
							if(!myList.get(k).getFathername().equals(searchPerson.getFathername()))
								continue;
						}
						if(!searchPerson.getPhone().equals("")){
							if(!myList.get(k).getPhone().equals(searchPerson.getPhone()))
								continue;
						}
						if(!searchPerson.getEMail().equals("")){
							if(!myList.get(k).getEMail().equals(searchPerson.getEMail()))
								continue;
						}
						if(!searchPerson.getNameJob().equals("")){
							if(!myList.get(k).getNameJob().equals(searchPerson.getNameJob()))
								continue;
						}
						if(!searchPerson.getExperienceJob().equals("")){
							if(!myList.get(k).getExperienceJob().equals(searchPerson.getExperienceJob()))
								continue;
						}
						finishList.add(myList.get(k));
						
						
					}
					try {
						out.writeUTF(SerializeMaker.serializeToXML(finishList));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			case "UserValidateRequest":
				
				UserValidateRequest request = SerializeMaker.deserializeFromXML(serializeObj);
				User client = request.getClient();
				LinkedList<User> catalogizer = MainServer.getUsersCatalog();
				Boolean flag = false;
				int index = 0;
				synchronized(catalogizer){
					
					for(int l = 0;l<catalogizer.size();l++){
						if(catalogizer.get(l).getLogin().equals(client.getLogin()) &&
								catalogizer.get(l).getPassword().equals(client.getPassword()))
						{
							flag = true;
							index = l;
							break;
						}
					}
					if(flag){
						//Exception!!
						client.setLogin(catalogizer.get(index).getLogin());
						client.setPassword(catalogizer.get(index).getPassword());
						client.setRights(catalogizer.get(index).getRights());
						client.setParser(catalogizer.get(index).getParser());
					}
					else
						client.setLogin("ERROR");
				}
				
				try {
					out.writeUTF(SerializeMaker.serializeToXML(client));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case "GetUserTableRequest":
				//GetUserTableRequest requestAnswer = SerializeMaker.deserializeFromXML(serializeObj);
				synchronized(MainServer.getUsersCatalog()){
					try {
						out.writeUTF(SerializeMaker.serializeToXML(MainServer.getUsersCatalog()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				break;
				
			case "AddUserRequest":
				AddUserRequest addUserRequest = SerializeMaker.deserializeFromXML(serializeObj);
				User user = addUserRequest.getUser();
				LinkedList<User> userList = MainServer.getUsersCatalog();
				Boolean addFlag = false;
				synchronized(userList){
					try {
						for(int m = 0;m<userList.size();m++){
							if(user.getLogin().equals(userList.get(m).getLogin())){
							    addFlag = true;
								break;
							}
						}
						if(addFlag){
							user.setLogin("ERROR");
						}else{
							userList.add(user);
							user.setParser(ParserName.SAX);
							user.setRights(Rights.USER);
							SerializeMaker.saveXML(user, user.getLogin(),"Data\\Users\\");
						}
							
						out.writeUTF(SerializeMaker.serializeToXML(user));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				break;
			}

		}

	}
}
