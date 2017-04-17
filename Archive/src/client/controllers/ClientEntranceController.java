package client.controllers;

import mainGui.main;
import models.Person;
import models.User;
import models.User.ParserName;
import models.User.Rights;
import service.SerializeMaker;
import service.requests.AddUserRequest;
import service.requests.SearchRequest;
import service.requests.UserValidateRequest;

import java.io.IOException;
import java.util.LinkedList;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import client.MainClient;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ClientEntranceController {

	private static MainClient client;
	private static Label log_label;
	private static Label pass_label;
	private static TextField log_field;
	private static PasswordField pass_field;
	private static TextField regLogField;
	private static TextField regPassField;
	private static Button sign_btn;
	private static Button regBtn;
	private static Button goBtn;

	public static Scene getEntranceScene() {

		log_label = new Label("Login");
		pass_label = new Label("Password");
		sign_btn = new Button("Sign in");
		sign_btn.setOnAction(sign_Action());
		regBtn = new Button("Reqister");
		regBtn.setPrefWidth(70);
		regBtn.setOnAction(regAction());

		log_field = new TextField();
		pass_field = new PasswordField();

		VBox label_layout = new VBox(13);
		label_layout.getChildren().addAll(log_label, pass_label);

		VBox line_layout = new VBox(5);
		line_layout.getChildren().addAll(log_field, pass_field);

		HBox V_layout = new HBox(8);
		V_layout.getChildren().addAll(label_layout, line_layout);

		HBox hor_layout = new HBox(84);
		hor_layout.getChildren().addAll(regBtn, sign_btn);

		VBox fin_layout = new VBox(5);
		fin_layout.getChildren().addAll(V_layout, hor_layout);

		BorderPane border = new BorderPane();
		border.setCenter(fin_layout);
		border.setPadding(new Insets(30, 0, 0, 20));

		Scene scene = new Scene(border, 250, 130);
		return scene;

	}

	public static EventHandler<ActionEvent> regAction() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				goBtn = new Button("Go");
				goBtn.setDisable(true);
				goBtn.setPrefWidth(70);
				goBtn.setOnAction(goAction());
				regLogField = new TextField();
				regPassField = new TextField();
				log_label = new Label("Login");
				pass_label = new Label("Password");
				regLogField.lengthProperty().addListener(regListener());
				regPassField.lengthProperty().addListener(regListener());

				VBox label_layout = new VBox(13);
				label_layout.getChildren().addAll(log_label, pass_label);

				VBox line_layout = new VBox(5);
				line_layout.getChildren().addAll(regLogField, regPassField);

				HBox V_layout = new HBox(8);
				V_layout.getChildren().addAll(label_layout, line_layout);

				HBox hor_layout = new HBox(74);
				Label spaceLabel = new Label();
				hor_layout.getChildren().addAll(spaceLabel, goBtn);

				VBox fin_layout = new VBox(7);
				fin_layout.getChildren().addAll(V_layout, hor_layout);

				BorderPane border = new BorderPane();
				border.setCenter(fin_layout);
				border.setPadding(new Insets(30, 0, 0, 20));

				Scene scene = new Scene(border, 250, 130);
				main.get_stage().setScene(scene);

			}
		};
	}

	public static ChangeListener<Number> regListener() {

		return new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub

				if (!regLogField.getText().isEmpty() && !regPassField.getText().isEmpty()) {
					goBtn.setDisable(false);
				} else {
					goBtn.setDisable(true);
				}

			}

		};
	}

	public static EventHandler<ActionEvent> goAction() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					User user = new User(regLogField.getText(), regPassField.getText(), Rights.USER, ParserName.SAX);
					AddUserRequest request = new AddUserRequest(user);
					client = new MainClient("Guest", "Guest", Rights.GUEST, ParserName.JDOM);
					client.ClientConnection();
					client.getOutputStream().writeUTF(SerializeMaker.serializeToXML(request));

					String response = new String();

					response = client.getInputStream().readUTF();

					client.setUser(SerializeMaker.deserializeFromXML(response));
					if(client.getUser().getLogin().equals("ERROR")){
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Information");
						alert.setHeaderText("Error!");
						alert.setContentText("User with this login already registered.\n\r Please, repeat input!");
						alert.showAndWait();
						return;
					}
					else
						main.get_stage().setScene(new Scene(ClientMainController.getPane(2), 805, 600));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		};
	}

	public static EventHandler<ActionEvent> sign_Action() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				try {
					if ((pass_field.getText().isEmpty() || log_field.getText().isEmpty())
							|| (pass_field.getText().isEmpty() && log_field.getText().isEmpty())) {
						client = new MainClient("Guest", "Guest", Rights.GUEST, ParserName.JDOM);

						if (client.ClientConnection())
							main.get_stage().setScene(new Scene(ClientMainController.getPane(1), 805, 600));

					} else if (!pass_field.getText().isEmpty() && !log_field.getText().isEmpty()) {
						client = new MainClient(log_field.getText(), pass_field.getText(), null, null);
						client.ClientConnection();

						UserValidateRequest request = new UserValidateRequest(client.getUser());
						
							client.getOutputStream().writeUTF(SerializeMaker.serializeToXML(request));
						

						String response = new String();

						response = client.getInputStream().readUTF();

						client.setUser(SerializeMaker.deserializeFromXML(response));
						if (client.getUser().getLogin().equals("ERROR")) {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("Information");
							alert.setHeaderText("Error!");
							alert.setContentText("Your enter data is uncorrect. Please, repeat input!");
							alert.showAndWait();
							return;
						} else {
							if (client.getUser().getRights() == Rights.ADMINISTRATOR)
								main.get_stage().setScene(new Scene(ClientMainController.getPane(0), 1250, 600));
							else if (client.getUser().getRights() == Rights.SENIORUSER)
								main.get_stage().setScene(new Scene(ClientMainController.getPane(3), 805, 600));
							else
								main.get_stage().setScene(new Scene(ClientMainController.getPane(2), 805, 600));
						}

					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Server is disconnected!");
				}
			}
		};
	}

	public static MainClient getClient() {
		return client;
	}

}