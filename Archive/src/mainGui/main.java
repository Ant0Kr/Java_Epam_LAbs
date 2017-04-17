package mainGui;

import java.io.IOException;

import client.*;
import client.controllers.ClientDialogController;
import client.controllers.ClientEntranceController;
import client.controllers.ClientMainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.User;
import service.SerializeMaker;

public class main extends Application {

	private static Stage stage;
	@Override
	public void start(Stage primaryStage) throws IOException{
		// TODO Auto-generated method stub
		stage = new Stage();
		stage.setScene(ClientEntranceController.getEntranceScene());
		/*String login = "3333";
		User user = new User("3333",login,User.Rights.GUEST);
		SerializeMaker.saveXML(user, login, "Data\\Users\\");*/
		stage.setTitle("Archive");
		stage.show();
		
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	public static Stage get_stage() {
		return stage;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}