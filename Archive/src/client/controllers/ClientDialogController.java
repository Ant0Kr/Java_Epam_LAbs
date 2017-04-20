package client.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mainGui.main;
import models.Person;
import service.SerializeMaker;
import service.requests.Request;


public class ClientDialogController {

	private static TextField surnameField;
	private static TextField nameField;
	private static TextField fathernameField;
	private static TextField phoneField;
	private static TextField emailField;
	private static TextField nameJobField;
	private static TextField experienceJobField;
	private static Label surnameLabel;
	private static Label nameLabel;
	private static Label fathernameLabel;
	private static Label phoneLabel;
	private static Label emailLabel;
	private static Label nameJobLabel;
	private static Label experienceLabel;
	private static Button runBtn;
	private static Stage stage = null;
	private static Boolean myflag;

	public static void getDialogWindow(Boolean flag) {
		
		myflag = flag;
		if (stage == null) {
			surnameField = new TextField();
			surnameField.setPromptText("-surname-");
			surnameField.lengthProperty().addListener(buttonListener());
			nameField = new TextField();
			nameField.setPromptText("-name-");
			nameField.lengthProperty().addListener(buttonListener());
			fathernameField = new TextField();
			fathernameField.setPromptText("-fathername-");
			fathernameField.lengthProperty().addListener(buttonListener());
			phoneField = new TextField();
			phoneField.setPromptText("-phone-");
			phoneField.lengthProperty().addListener(buttonListener());
			emailField = new TextField();
			emailField.setPromptText("-email-");
			emailField.lengthProperty().addListener(buttonListener());
			nameJobField = new TextField();
			nameJobField.setPromptText("-job-");
			nameJobField.lengthProperty().addListener(buttonListener());
			experienceJobField = new TextField();
			experienceJobField.setPromptText("-experience-");
			experienceJobField.lengthProperty().addListener(buttonListener());

			surnameLabel = new Label("Surname:");
			nameLabel = new Label("Name:");
			fathernameLabel = new Label("Father name:");
			phoneLabel = new Label("Phone:");
			emailLabel = new Label("eMail:");
			nameJobLabel = new Label("Job name:");
			experienceLabel = new Label("Job experience:");
			runBtn = new Button("Run");
			runBtn.setDisable(true);
			runBtn.setPrefWidth(70);
			runBtn.setOnAction(runAction());

			VBox fieldLayout = new VBox(5);
			fieldLayout.getChildren().addAll(surnameField, nameField, fathernameField, phoneField, emailField,
					nameJobField, experienceJobField);

			VBox labelLayout = new VBox(14);
			labelLayout.getChildren().addAll(surnameLabel, nameLabel, fathernameLabel, phoneLabel, emailLabel,
					nameJobLabel, experienceLabel);

			HBox labelFieldLayout = new HBox(5);
			labelFieldLayout.getChildren().addAll(labelLayout, fieldLayout);

			HBox btnLayout = new HBox(80);
			btnLayout.getChildren().addAll(new Label(), runBtn);

			VBox finish = new VBox(15);
			finish.getChildren().addAll(labelFieldLayout, btnLayout);
			finish.setPadding(new Insets(20, 20, 20, 20));

			stage = new Stage();
			stage.setScene(new Scene(finish, 270, 270));
			stage.setResizable(false);
			stage.setTitle("Dialog");
		}
		stage.show();

	}

	public static ChangeListener<Number> buttonListener() {

		return new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				if (myflag) {
					if (surnameField.getText().isEmpty() || nameField.getText().isEmpty()
							|| fathernameField.getText().isEmpty() || phoneField.getText().isEmpty()
							|| emailField.getText().isEmpty() || nameJobField.getText().isEmpty()
							|| experienceJobField.getText().isEmpty()) {
						runBtn.setDisable(true);
					} else {
						runBtn.setDisable(false);
					}
				} else {
					if (!surnameField.getText().isEmpty() || !nameField.getText().isEmpty()
							|| !fathernameField.getText().isEmpty() || !phoneField.getText().isEmpty()
							|| !emailField.getText().isEmpty() || !nameJobField.getText().isEmpty()
							|| !experienceJobField.getText().isEmpty()) {
						runBtn.setDisable(false);
					} else {
						runBtn.setDisable(true);
					}
				}
			}

		};
	}

	public static EventHandler<ActionEvent> runAction() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if (e.getSource() == runBtn) {
					if (myflag) {
						Request request = new Request("ADDPERSON",new Person(surnameField.getText(), nameField.getText(),
								fathernameField.getText(), phoneField.getText(), emailField.getText(),
								nameJobField.getText(), experienceJobField.getText()),null,null);
						try {
							ClientEntranceController.getClient().getOutputStream().writeUTF(SerializeMaker.serializeToXML(request));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}else{
						Person oldPerson = ClientMainController.getSelectedPerson();
						Person newPerson = new Person(surnameField.getText(), nameField.getText(),
								fathernameField.getText(), phoneField.getText(), emailField.getText(),
								nameJobField.getText(), experienceJobField.getText());
						
						Request request = new Request("EDIT",oldPerson, newPerson,null);
						try {
							ClientEntranceController.getClient().getOutputStream().writeUTF(SerializeMaker.serializeToXML(request));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		
					}
					
					String response = new String();
					try {
						response = ClientEntranceController.getClient().getInputStream().readUTF();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					LinkedList<Person> list = SerializeMaker.deserializeFromXML(response);
					ClientMainController.changeArchiveTable(list);
					clearFields();
					stage.close();
				}

			}
		};
	}
	
	public static void clearFields(){
		surnameField.clear();
		nameField.clear();
		fathernameField.clear();
		phoneField.clear();
		emailField.clear();
		nameJobField.clear();
		experienceJobField.clear();
	}

}
