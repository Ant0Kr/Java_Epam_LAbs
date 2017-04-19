package client.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.LinkedList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import client.MainClient;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mainGui.main;
import models.Person;
import models.User;
import service.SerializeMaker;
import service.requests.*;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ClientMainController {

	private static Person selectedPerson;
	private static User selectedUser;
	private static LinkedList<Person> archiveCatalog = null;
	private static LinkedList<User> userCatalog = null;
	private static TableView archiveTable = null;
	private static TableView userTable;
	private static TextField surnameField;
	private static TextField fathernameField;
	private static TextField nameField;
	private static TextField phoneField;
	private static TextField emailField;
	private static TextField nameJobField;
	private static TextField experienceJobField;
	private static Button showAllBtn;
	private static Button editBtn;
	private static Button deleteBtn;
	private static Button addBtn;
	private static Button searchBtn;
	private static Button changeRightsBtn;
	private static Button changeParserBtn;
	private static Button exitBtn;
	private static int rights;
	private static ContextMenu contextParserMenu;
	private static ContextMenu contextRightsMenu;

	public static BorderPane getPane(int rightsLevel) throws IOException {

		rights = rightsLevel;
		surnameField = new TextField();
		fathernameField = new TextField();
		nameField = new TextField();
		phoneField = new TextField();
		emailField = new TextField();
		nameJobField = new TextField();
		experienceJobField = new TextField();
		surnameField.lengthProperty().addListener(searchListener());
		nameField.lengthProperty().addListener(searchListener());
		fathernameField.lengthProperty().addListener(searchListener());
		phoneField.lengthProperty().addListener(searchListener());
		emailField.lengthProperty().addListener(searchListener());
		nameJobField.lengthProperty().addListener(searchListener());
		experienceJobField.lengthProperty().addListener(searchListener());
		surnameField.setPromptText("-surname-");
		fathernameField.setPromptText("-father name-");
		nameField.setPromptText("-name-");
		phoneField.setPromptText("-mobile phone-");
		emailField.setPromptText("-email-");
		nameJobField.setPromptText("-name of job-");
		experienceJobField.setPromptText("-job experience-");

		searchBtn = new Button("Search");
		searchBtn.setDisable(true);
		searchBtn.setOnAction(searchAction());
		exitBtn = new Button("Exit");
		exitBtn.setOnAction(exitAction());
		addBtn = new Button("Add");
		addBtn.setOnAction(addAction());
		deleteBtn = new Button("Delete");
		deleteBtn.setOnAction(deleteAction());
		deleteBtn.setDisable(true);
		editBtn = new Button("Edit");
		editBtn.setDisable(true);
		editBtn.setOnAction(editAction());
		showAllBtn = new Button("Show all");
		showAllBtn.setOnAction(showAllAction());

		searchBtn.setPrefWidth(100);
		exitBtn.setPrefWidth(90);
		addBtn.setPrefWidth(90);
		deleteBtn.setPrefWidth(90);
		editBtn.setPrefWidth(90);
		showAllBtn.setPrefWidth(90);
		searchBtn.setMinWidth(90);
		exitBtn.setMinWidth(80);
		addBtn.setMinWidth(80);
		deleteBtn.setMinWidth(80);
		editBtn.setMinWidth(80);
		showAllBtn.setMinWidth(80);

		HBox searchFirstLayout = new HBox(5);
		searchFirstLayout.getChildren().addAll(surnameField, nameField, fathernameField, phoneField);
		searchFirstLayout.setAlignment(Pos.CENTER);

		HBox searchSecondLayout = new HBox(5);
		searchSecondLayout.getChildren().addAll(emailField, nameJobField, experienceJobField, searchBtn);
		searchSecondLayout.setAlignment(Pos.CENTER);

		VBox finishSearchLayout = new VBox(5);
		finishSearchLayout.getChildren().addAll(searchFirstLayout, searchSecondLayout);
		finishSearchLayout.setPadding(new Insets(0, 20, 20, 20));
		finishSearchLayout.setAlignment(Pos.CENTER);

		VBox serviceBtnLayout = new VBox(5);
		serviceBtnLayout.getChildren().addAll(exitBtn, showAllBtn, editBtn, deleteBtn, addBtn);
		serviceBtnLayout.setPadding(new Insets(20, 20, 20, 20));
		serviceBtnLayout.setAlignment(Pos.TOP_RIGHT);

		VBox tableLayout = new VBox(5);
		tableLayout.getChildren().addAll(createArchiveTable());
		tableLayout.setPadding(new Insets(20, 0, 20, 20));
		tableLayout.setAlignment(Pos.TOP_LEFT);

		BorderPane mainPane = new BorderPane();

		if (rightsLevel == 0) {

			changeRightsBtn = new Button("Change rights");
			changeParserBtn = new Button("Change parser");
			changeRightsBtn.setDisable(true);
			changeParserBtn.setDisable(true);
			changeRightsBtn.setPrefWidth(120);
			changeParserBtn.setPrefWidth(120);
			
			contextParserMenu = new ContextMenu();
			MenuItem domItem = new MenuItem("DOM");
			MenuItem jDomItem = new MenuItem("JDOM");
			MenuItem saxItem = new MenuItem("SAX");
			MenuItem staxItem = new MenuItem("STAX");
			
			contextRightsMenu = new ContextMenu();
			MenuItem adminItem = new MenuItem("ADMINISTRATOR");
			MenuItem seniorItem = new MenuItem("SENIOR USER");
			MenuItem userItem = new MenuItem("USER");
			
			contextParserMenu.getItems().addAll(domItem,jDomItem,saxItem,staxItem);
			changeParserBtn.setContextMenu(contextParserMenu);
			changeParserBtn.setOnAction(changeParserAction());
			
			contextRightsMenu.getItems().addAll(adminItem,seniorItem,userItem);
			changeRightsBtn.setContextMenu(contextRightsMenu);
			changeRightsBtn.setOnAction(changeRightsAction());

			HBox tablePlusBtnLayout = new HBox(5);
			tablePlusBtnLayout.getChildren().addAll(tableLayout, serviceBtnLayout);
			tablePlusBtnLayout.setPadding(new Insets(0, 0, 20, 20));
			tablePlusBtnLayout.setAlignment(Pos.TOP_LEFT);

			HBox adminBtnLayout = new HBox(5);
			adminBtnLayout.getChildren().addAll(changeParserBtn, changeRightsBtn);

			VBox userTableLayout = new VBox(5);
			userTableLayout.getChildren().addAll(createUserTable());
			userTableLayout.setPadding(new Insets(20, 20, 0, 0));
			userTableLayout.setAlignment(Pos.TOP_RIGHT);

			VBox adminFinishLayout = new VBox(5);
			adminFinishLayout.getChildren().addAll(userTableLayout, adminBtnLayout);
			adminFinishLayout.setPadding(new Insets(0, 20, 20, 10));

			mainPane.setCenter(tablePlusBtnLayout);
			mainPane.setBottom(finishSearchLayout);
			mainPane.setRight(adminFinishLayout);

		} else {

			if (rightsLevel == 1) {
				setGuestHide();
			} else if (rightsLevel == 2) {
				setUserHide();
			}

			mainPane.setCenter(tableLayout);
			mainPane.setBottom(finishSearchLayout);
			mainPane.setRight(serviceBtnLayout);

		}
		return mainPane;

	}

	public static TableView createUserTable() {

		userTable = new TableView();
		userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				selectedUser = (User) userTable.getSelectionModel().getSelectedItem();
				if (rights != 1) {
					changeParserBtn.setDisable(false);
					changeRightsBtn.setDisable(false);
				}
			}
		});

		TableColumn login = new TableColumn("Login");
		login.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
		login.setPrefWidth(110);

		TableColumn pass = new TableColumn("Password");
		pass.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
		pass.setPrefWidth(110);

		TableColumn rights = new TableColumn("Rights");
		rights.setCellValueFactory(new PropertyValueFactory<User, String>("rights"));
		rights.setPrefWidth(100);

		TableColumn parser = new TableColumn("Parser");
		parser.setCellValueFactory(new PropertyValueFactory<User, String>("parser"));
		parser.setPrefWidth(70);

		userTable.getColumns().addAll(login, pass, rights, parser);
		userTable.setPrefSize(400, 300);

		Request request = new Request("GETUSERTABLE",null,null,null);
		try {
			ClientEntranceController.getClient().getOutputStream().writeUTF(SerializeMaker.serializeToXML(request));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String response = new String();
		try {
			response = ClientEntranceController.getClient().getInputStream().readUTF();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LinkedList<User> list = SerializeMaker.deserializeFromXML(response);
		setUserTable(list);
		
		
		if (userCatalog != null) {
			ObservableList<User> item = FXCollections.observableArrayList();
			for (int i = 0; i < userCatalog.size(); i++) {
				User user = userCatalog.get(i);
				item.addAll(user);
			}
			userTable.setItems(item);
		}
		return userTable;
	}

	public static TableView createArchiveTable() {

		archiveTable = new TableView();

		archiveTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				selectedPerson = (Person) archiveTable.getSelectionModel().getSelectedItem();
				if (rights != 1) {
					deleteBtn.setDisable(false);
					editBtn.setDisable(false);
				}
			}
		});

		TableColumn surname = new TableColumn("Surname");
		surname.setCellValueFactory(new PropertyValueFactory<Person, String>("surname"));
		surname.setPrefWidth(70);

		TableColumn name = new TableColumn("Name");
		name.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		name.setPrefWidth(70);

		TableColumn fathername = new TableColumn("Fathername");
		fathername.setCellValueFactory(new PropertyValueFactory<Person, String>("fathername"));
		fathername.setPrefWidth(110);

		TableColumn phone = new TableColumn("Phone number");
		phone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));
		phone.setPrefWidth(110);

		TableColumn email = new TableColumn("eMail");
		email.setCellValueFactory(new PropertyValueFactory<Person, String>("eMail"));
		email.setPrefWidth(130);

		TableColumn nameJob = new TableColumn("Job name");
		nameJob.setCellValueFactory(new PropertyValueFactory<Person, String>("nameJob"));
		nameJob.setPrefWidth(70);

		TableColumn experienceJob = new TableColumn("Job experience");
		experienceJob.setCellValueFactory(new PropertyValueFactory<Person, String>("experienceJob"));
		experienceJob.setPrefWidth(90);

		archiveTable.getColumns().addAll(surname, name, fathername, phone, email, nameJob, experienceJob);
		archiveTable.setPrefSize(800, 1500);
		if (archiveCatalog != null) {
			ObservableList<Person> item = FXCollections.observableArrayList();
			for (int i = 0; i < archiveCatalog.size(); i++) {
				Person person = archiveCatalog.get(i);
				item.addAll(person);
			}
			archiveTable.setItems(item);
		}

		return archiveTable;
	}

	public static void setGuestHide() {
		editBtn.setVisible(false);
		deleteBtn.setVisible(false);
		addBtn.setVisible(false);
	}

	public static void setUserHide() {
		deleteBtn.setVisible(false);
		addBtn.setVisible(false);
	}
	
	public static EventHandler<ActionEvent> changeRightsAction() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if (e.getSource() == changeRightsBtn) {

				contextRightsMenu.show(changeRightsBtn,Side.BOTTOM,0,0);
				}

			}
		};
	}
	
	public static EventHandler<ActionEvent> changeParserAction() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if (e.getSource() == changeParserBtn) {

				contextParserMenu.show(changeParserBtn,Side.BOTTOM,0,0);
				}

			}
		};
	}
	
	public static EventHandler<ActionEvent> searchAction() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if (e.getSource() == searchBtn) {

					Person searchPerson = new Person(surnameField.getText(), nameField.getText(),
							fathernameField.getText(), phoneField.getText(), emailField.getText(),
							nameJobField.getText(), experienceJobField.getText());

					Request request = new Request("SEARCH",searchPerson,null,null);
					try {
						ClientEntranceController.getClient().getOutputStream()
								.writeUTF(SerializeMaker.serializeToXML(request));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					String response = new String();
					try {
						response = ClientEntranceController.getClient().getInputStream().readUTF();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					LinkedList<Person> list = SerializeMaker.deserializeFromXML(response);
					setArchive(list);
					try {
						double height = main.get_stage().getHeight();
						double width = main.get_stage().getWidth();
						main.get_stage()
								.setScene(new Scene(ClientMainController.getPane(rights), width - 16.5, height - 39));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		};
	}

	public static EventHandler<ActionEvent> exitAction() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (e.getSource() == exitBtn) {
					Request request = new Request("EXIT",null,null,null);
					try {
						ClientEntranceController.getClient().getOutputStream()
								.writeUTF(SerializeMaker.serializeToXML(request));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					main.get_stage().setScene(ClientEntranceController.getEntranceScene());
					if (archiveCatalog != null) {
						archiveCatalog.clear();
						archiveCatalog = null;
					}

				}

			}
		};
	}

	public static EventHandler<ActionEvent> showAllAction() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (e.getSource() == showAllBtn) {
					Request request = new Request("SHOWALL",null,null,null);
					try {
						ClientEntranceController.getClient().getOutputStream()
								.writeUTF(SerializeMaker.serializeToXML(request));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					String response = new String();
					try {
						response = ClientEntranceController.getClient().getInputStream().readUTF();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					archiveCatalog = SerializeMaker.deserializeFromXML(response);
					try {
						double height = main.get_stage().getHeight();
						double width = main.get_stage().getWidth();
						main.get_stage()
								.setScene(new Scene(ClientMainController.getPane(rights), width - 16.5, height - 39));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		};
	}

	public static EventHandler<ActionEvent> deleteAction() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (e.getSource() == deleteBtn) {
					Request request = new Request("DELETE",selectedPerson,null,null);
					try {
						ClientEntranceController.getClient().getOutputStream()
								.writeUTF(SerializeMaker.serializeToXML(request));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					String response = new String();
					try {
						response = ClientEntranceController.getClient().getInputStream().readUTF();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					LinkedList<Person> list = SerializeMaker.deserializeFromXML(response);
					archiveCatalog.clear();
					archiveCatalog = list;
					try {
						double height = main.get_stage().getHeight();
						double width = main.get_stage().getWidth();
						main.get_stage()
								.setScene(new Scene(ClientMainController.getPane(rights), width - 16.5, height - 39));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		};
	}

	public static EventHandler<ActionEvent> addAction() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (e.getSource() == addBtn)
					ClientDialogController.getDialogWindow(true);

			}
		};
	}

	public static EventHandler<ActionEvent> editAction() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (e.getSource() == editBtn)
					ClientDialogController.getDialogWindow(false);

			}
		};
	}

	public static ChangeListener<Number> searchListener() {

		return new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub

				if (!surnameField.getText().isEmpty() || !nameField.getText().isEmpty()
						|| !fathernameField.getText().isEmpty() || !phoneField.getText().isEmpty()
						|| !emailField.getText().isEmpty() || !nameJobField.getText().isEmpty()
						|| !experienceJobField.getText().isEmpty()) {
					searchBtn.setDisable(false);
				} else {
					searchBtn.setDisable(true);
				}

			}

		};
	}

	public static void setArchive(LinkedList<Person> archive) {
		if (archiveCatalog == null)
			archiveCatalog = new LinkedList<Person>();
		archiveCatalog.clear();
		archiveCatalog = archive;
	}
	
	public static void setUserTable(LinkedList<User> userList){
		if (userCatalog == null)
			userCatalog = new LinkedList<User>();
		userCatalog.clear();
		userCatalog = userList;
	}

	public static int getRights() {
		return rights;
	}

	public static Person getSelectedPerson() {
		return selectedPerson;
	}

}