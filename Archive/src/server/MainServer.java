package server;

import java.net.Socket;
import java.util.LinkedList;

import models.Person;
import models.User;
import service.SerializeMaker;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class MainServer {

	private static LinkedList<User> userList;
	private static LinkedList<Person> archiveCatalog;
	private static int userCounter = 0;
	final static int PORT = 5555;

	public static void main(String args[]) throws IOException {
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (Exception e) {
			System.out.println("Server already connected");
			return;
		}
		initUsersCatalog();
		initArchiveCatalog();

		System.out.println("Server connected!");
		while (true) {
			socket = serverSocket.accept();
			System.out.println("Client " + userCounter + " was connected!");
			userCounter++;
			ServerThread thread = new ServerThread(socket);
			thread.start();
		}
	}

	public static void initArchiveCatalog() {

		archiveCatalog = new LinkedList<Person>();
		File[] fList;
		File file = new File("Data\\Archive");
		fList = file.listFiles();
		for (int i = 0; i < fList.length; i++) {
			if (fList[i].isFile()) {
				String way = fList[i].getName();
				String newWay = new String();
				int j = 0;
				while (way.charAt(j) != '.') {
					newWay = newWay + way.charAt(j);
					j++;
				}
				archiveCatalog.add(SerializeMaker.load(newWay,"Data\\Archive\\"));
			}

		}
	}
	
	public static void initUsersCatalog() {

		userList = new LinkedList<User>();
		File[] fList;
		File file = new File("Data\\Users");
		fList = file.listFiles();
		for (int i = 0; i < fList.length; i++) {
			if (fList[i].isFile()) {
				String way = fList[i].getName();
				String newWay = new String();
				int j = 0;
				while (way.charAt(j) != '.') {
					newWay = newWay + way.charAt(j);
					j++;
				}
				userList.add(SerializeMaker.load(newWay,"Data\\Users\\"));
			}

		}
	}


	public static LinkedList<Person> getCatalog() {
		return archiveCatalog;
	}
	
	public static LinkedList <User> getUsersCatalog() {
		return userList;
	}
}