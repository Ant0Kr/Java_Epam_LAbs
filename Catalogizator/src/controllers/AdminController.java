package controllers;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import implementations.DataDaoImplements;
import implementations.UserDaoImplements;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * 
 * Class that provides controls in admin window.<br>
 * This class contain all parts of admin window.<br>
 * Class designed for admin authorization.
 */
@SuppressWarnings("unused")
public class AdminController {

	private static Label pass_label;
	private static PasswordField adm_pass_field;
	private static Button Ok_btn;
	private static Button ago_btn;
	public static final Logger LOG = Logger.getLogger(AdminController.class);
	
	public static VBox getPane() {

		pass_label = new Label("Password");
		adm_pass_field = new PasswordField();
		adm_pass_field.lengthProperty().addListener(adm_Listener());
		Ok_btn = new Button("Ok");
		Ok_btn.setMinWidth(50);
		Ok_btn.setDisable(true);
		Ok_btn.setOnAction(Ok_Action());
		ago_btn = new Button("Back");
		ago_btn.setMinWidth(70);
		ago_btn.setOnAction(ago_Action());

		HBox H1_layout = new HBox(5);
		HBox H2_layout = new HBox(85);
		H1_layout.getChildren().addAll(pass_label, adm_pass_field);
		H2_layout.getChildren().addAll(ago_btn, Ok_btn);
		VBox V_layout = new VBox(5);
		V_layout.getChildren().addAll(H1_layout, H2_layout);
		V_layout.setPadding(new Insets(40, 0, 0, 25));

		return V_layout;

	}

	public static EventHandler<ActionEvent> ago_Action() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				if (e.getSource() == ago_btn) {
					Main.get_stage().setScene(new Scene(InputController.getPane(), 250, 135));

				}
			}

		};
	}

	public static EventHandler<ActionEvent> Ok_Action() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				if (e.getSource() == Ok_btn) {

					try {
						UserDaoImplements.getInstance().ConnectionDB();
						if (UserDaoImplements.getInstance().check_User("admin", adm_pass_field.getText())) {
							UserDaoImplements.getInstance().CloseDB();
							
							Main.get_stage().setScene(new Scene(WindowController.setTableScene(1, ""), 540, 400));
							Main.get_stage().setResizable(false);
							LOG.info("Admin perform enterance into the system!");
						} else {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("Information");
							alert.setHeaderText("Error!");
							alert.setContentText("Your password is uncorrect. Please, repeat input!");
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

	public static ChangeListener<Number> adm_Listener() {

		return new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				if (adm_pass_field.getText().isEmpty()) {
					Ok_btn.setDisable(true);
				} else {
					Ok_btn.setDisable(false);
				}
			}

		};
	}
}
