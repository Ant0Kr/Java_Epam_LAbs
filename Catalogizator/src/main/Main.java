package main;

import controllers.*;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * <br>
 * <b>Description:</b><br>
 * Main class that contain point of entrance. <br>
 * <b>Extends Application</b><br>
 *
 */ 
public class Main extends Application {

	private static Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		stage = new Stage();
		stage.setScene(new Scene(InputController.getPane(), 250, 135));
		stage.setResizable(false);
		stage.setTitle("Catalogizator");
		stage.show();
	}

	public static Stage get_stage() {
		return stage;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
