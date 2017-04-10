package controllers;

import models.*;
import resources.*;

import java.sql.SQLException;

import implementations.DataDaoImplements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import main.Main;

/**
 * 
 * This class containt methods that control all user/admin actions. Class can
 * paint and repaint scene with main table, add data , search and ect.
 * 
 *
 */
@SuppressWarnings({ "unused", "rawtypes" })
public class WindowController {

	private static TextField search_field;
	private static Button search_btn;
	private static Button browse_btn;
	private static Button ago_btn;
	private static ComboBox section_Box;

	/**
	 * 
	 * @param num
	 * @param st
	 * @return VBox<br>
	 *         <b>Description:</b><br>
	 *         This method sets the scene with table on main stage. This method
	 *         also can repain scene if user change value in ComboBox.
	 */
	@SuppressWarnings("unchecked")
	public static VBox setTableScene(int num, String st) {

		section_Box = new ComboBox();
		section_Box.getItems().addAll("Books", "Audio", "Films", "Documents");
		section_Box.setValue("Books");
		section_Box.setPromptText("-section-");
		section_Box.setOnAction(section_Action());
		ago_btn = new Button("Back");
		ago_btn.setMinWidth(70);
		ago_btn.setOnAction(ago_Action());
		browse_btn = new Button("Browse");
		browse_btn.setMinWidth(100);
		browse_btn.setOnAction(browse_Action());
		search_btn = new Button("Search");
		search_btn.setOnAction(search_Action());
		search_field = new TextField();
		search_field.setPromptText("File name");
		switch (num) {
		case 1:
			section_Box.setValue("Books");
			break;
		case 2:
			section_Box.setValue("Films");
			break;
		case 3:
			section_Box.setValue("Audio");
			break;
		case 4:
			section_Box.setValue("Documents");
			break;

		}

		HBox search_layout = new HBox(5);
		search_layout.getChildren().addAll(search_field, search_btn, browse_btn);

		HBox layout = new HBox(5);
		layout.getChildren().addAll(section_Box, ago_btn);

		VBox finish = new VBox(10);
		switch (num) {
		case 1:
			finish.getChildren().addAll(layout, cTable_Builder.get_table(1, st), search_layout);
			break;
		case 2:
			finish.getChildren().addAll(layout, cTable_Builder.get_table(2, st), search_layout);
			break;
		case 3:
			finish.getChildren().addAll(layout, cTable_Builder.get_table(3, st), search_layout);
			break;
		case 4:
			finish.getChildren().addAll(layout, cTable_Builder.get_table(4, st), search_layout);
			break;

		}

		finish.setPadding(new Insets(10, 0, 0, 10));

		if (InputController.getGuestFlag() == true)
			browse_btn.setVisible(false);
		else
			browse_btn.setVisible(true);

		return finish;

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

	public static EventHandler<ActionEvent> section_Action() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if (section_Box.getValue().toString() == "Films") {
					Main.get_stage().setScene(new Scene(WindowController.setTableScene(2, ""), 540, 400));
				} else if (section_Box.getValue().toString() == "Books") {
					Main.get_stage().setScene(new Scene(WindowController.setTableScene(1, ""), 540, 400));
				} else if (section_Box.getValue().toString() == "Documents") {
					Main.get_stage().setScene(new Scene(WindowController.setTableScene(4, ""), 540, 400));
				} else if (section_Box.getValue().toString() == "Audio") {
					Main.get_stage().setScene(new Scene(WindowController.setTableScene(3, ""), 540, 400));
				}

			}

		};
	}

	public static EventHandler<ActionEvent> search_Action() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				int num = 0;
				if (e.getSource() == search_btn) {
					if (section_Box.getValue().toString() == "Films") {
						num = 2;
					} else if (section_Box.getValue().toString() == "Books") {
						num = 1;
					} else if (section_Box.getValue().toString() == "Documents") {
						num = 4;
					} else if (section_Box.getValue().toString() == "Audio") {
						num = 3;
					}
					Main.get_stage()
							.setScene(new Scene(WindowController.setTableScene(num, search_field.getText()), 540, 400));

				}
			}

		};

	}

	public static EventHandler<ActionEvent> browse_Action() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				int num = 0;
				if (e.getSource() == browse_btn) {
					if (section_Box.getValue().toString() == "Films") {
						num = 2;
					} else if (section_Box.getValue().toString() == "Books") {
						num = 1;
					} else if (section_Box.getValue().toString() == "Documents") {
						num = 4;
					} else if (section_Box.getValue().toString() == "Audio") {
						num = 3;
					}
					File_Chooser.Create_Choose_Window(num);

					try {
						if (File_Chooser.add_Entry(InputController.getLogField(), num, InputController.getUserFlag()))
							Main.get_stage().setScene(new Scene(WindowController.setTableScene(num, ""), 540, 400));
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		};
	}

}
