package controllers;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import implementations.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;

@SuppressWarnings("unused")
/**
 * 
 * Class that provides controls in input window.<br>
 * This class contain all parts of user window.<br>
 * Class designed for simple user authorization.
 */
public class InputController {

	private static Label log_label; // Enter and Admin Windows
	private static Label pass_label;
	private static TextField log_field;
	private static PasswordField pass_field;
	private static Button admin_btn;
	private static Button guest_btn;
	private static Button sign_btn;
	private static Boolean guest_flag;
	private static Boolean user_flag;
	public static final Logger LOG = Logger.getLogger(InputController.class);
	
	public static BorderPane getPane() {

		log_label = new Label("Login");
		pass_label = new Label("Password");
		admin_btn = new Button("Admin");
		admin_btn.setOnAction(admin_Action());
		sign_btn = new Button("Sign in");
		sign_btn.setOnAction(sign_Action());
		sign_btn.setDisable(true);
		guest_btn = new Button("Guest");
		guest_btn.setOnAction(guest_Action());
		log_field = new TextField();
		pass_field = new PasswordField();
		log_field.lengthProperty().addListener(user_Listener());
		pass_field.lengthProperty().addListener(user_Listener());
		guest_flag = false;
		user_flag = false;
		
		

		VBox label_layout = new VBox(13);
		label_layout.getChildren().addAll(log_label, pass_label);

		VBox line_layout = new VBox(5);
		line_layout.getChildren().addAll(log_field, pass_field);

		HBox V_layout = new HBox(8);
		V_layout.getChildren().addAll(label_layout, line_layout);

		HBox btn_layout = new HBox(5);
		btn_layout.getChildren().addAll(admin_btn, guest_btn);

		HBox hor_layout = new HBox(50);
		hor_layout.getChildren().addAll(btn_layout, sign_btn);

		VBox fin_layout = new VBox(5);
		fin_layout.getChildren().addAll(V_layout, hor_layout);

		BorderPane border = new BorderPane();
		border.setCenter(fin_layout);
		border.setPadding(new Insets(30, 0, 0, 20));

		return border;

	}

	public static Boolean getGuestFlag() {
		return guest_flag;
	}

	public static Boolean getUserFlag() {
		return user_flag;
	}

	public static EventHandler<ActionEvent> admin_Action() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (e.getSource() == admin_btn) {
					Main.get_stage().setScene(new Scene(AdminController.getPane(), 260, 120));

				}

			}
		};
	}

	public static EventHandler<ActionEvent> guest_Action() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				if (e.getSource() == guest_btn) {
					LOG.info("Guest perform enterance into the system!");
					guest_flag = true;
					Main.get_stage().setScene(new Scene(WindowController.setTableScene(1, ""), 540, 400));
				}
			}
		};
	}

	public static EventHandler<ActionEvent> sign_Action() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				if (e.getSource() == sign_btn) {
					user_flag = true;

					try {
						UserDaoImplements.getInstance().ConnectionDB();
						if (UserDaoImplements.getInstance().check_User(log_field.getText(), pass_field.getText())) {
							UserDaoImplements.getInstance().CloseDB();
							Main.get_stage().setScene(new Scene(WindowController.setTableScene(1, ""), 540, 400));
							Main.get_stage().setResizable(false);
							LOG.info("User - "+log_field.getText()+" perform enterance into the system!");
						} else {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("Information");
							alert.setHeaderText("Error!");
							alert.setContentText("Your enter data is uncorrect. Please, repeat input!");
							alert.showAndWait();
							UserDaoImplements.getInstance().CloseDB();
						}
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}

		};
	}

	public static ChangeListener<Number> user_Listener() {

		return new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				if (log_field.getText().isEmpty() || pass_field.getText().isEmpty()) {
					sign_btn.setDisable(true);
				} else {
					sign_btn.setDisable(false);
				}
			}

		};
	}

	public static String getLogField() {
		return log_field.getText();
	}

}
