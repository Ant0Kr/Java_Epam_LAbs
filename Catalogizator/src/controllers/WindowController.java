package controllers;

import models.*;
import resources.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import com.itextpdf.text.DocumentException;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
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

	private static Data selectedData;
	private static TextField search_field;
	private static Button search_btn;
	private static Button browse_btn;
	private static Button ago_btn;
	private static Button deleteBtn;
	private static Button generatePDF;
	private static Button cancelBtn;
	private static TableView table;
	private static int myNum;
	private static ComboBox section_Box;
	private static LinkedList<Data> storageList = new LinkedList<Data>();

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
	public static BorderPane setTableScene(int num, String st) {
		myNum = num;
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
		deleteBtn = new Button("Delete");
		deleteBtn.setDisable(true);
		deleteBtn.setOnAction(deleteAction());
		cancelBtn = new Button("Cancel");
		cancelBtn.setOnAction(cancelAction());
		generatePDF = new Button("Export...");
		generatePDF.setOnAction(exportAction());
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
		search_layout.getChildren().addAll(search_field, search_btn, browse_btn, generatePDF);
		search_layout.setPadding(new Insets(10, 10, 10, 10));

		HBox layout = new HBox(5);
		layout.getChildren().addAll(section_Box, ago_btn, deleteBtn, cancelBtn);
		layout.setPadding(new Insets(10, 10, 10, 10));

		VBox tableLayout = new VBox(10);
		switch (myNum) {
		case 1:
			table = cTable_Builder.get_table(1, st);
			break;
		case 2:
			table = cTable_Builder.get_table(2, st);
			break;
		case 3:
			table = cTable_Builder.get_table(3, st);
			break;
		case 4:
			table = cTable_Builder.get_table(4, st);
			break;

		}
		tableLayout.getChildren().addAll(table);
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				selectedData = (Data) table.getSelectionModel().getSelectedItem();
				deleteBtn.setDisable(false);
			}
		});
		tableLayout.setPadding(new Insets(10, 10, 10, 10));

		if (InputController.getGuestFlag() == true)
			browse_btn.setVisible(false);
		else
			browse_btn.setVisible(true);

		BorderPane finishPane = new BorderPane();
		finishPane.setBottom(search_layout);
		finishPane.setTop(layout);
		finishPane.setCenter(tableLayout);
		finishPane.setPadding(new Insets(10, 10, 10, 10));

		return finishPane;

	}

	public static EventHandler<ActionEvent> deleteAction() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				if (e.getSource() == deleteBtn) {

					try {
						DataDaoImplements.getInstance().ConnectionDB();
						DataDaoImplements.getInstance().deleteData(selectedData);
						DataDaoImplements.getInstance().CloseDB();
						Main.get_stage().setScene(
								new Scene(WindowController.setTableScene(myNum, ""), 540, 400));
						
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
	}
	
	public static EventHandler<ActionEvent> cancelAction() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				if (e.getSource() == cancelBtn) {

					Data item = null;
					try {
						item = getList().removeFirst();
					} catch (NoSuchElementException e1) {
						return;
					}

					/*
					 * Boolean flag = item.getFlag(); try {
					 * DaoStorageItemImplements.getInstance().ConnectionDB(); if
					 * (flag) {
					 * 
					 * if
					 * (DaoStorageItemImplements.getInstance().unloadData(item.
					 * getStringName(), item.getStringCount(),
					 * item.getStringSection())) { double width =
					 * Main.get_stage().getWidth(); double height =
					 * Main.get_stage().getHeight();
					 * Main.get_stage().setScene(new Scene(
					 * MainWindowController.setScene(MainWindowController.
					 * getCurrentSection(), false), width - 16.5, height - 39));
					 * }
					 * 
					 * } else {
					 * 
					 * DaoStorageItemImplements.getInstance().loadData(item.
					 * getStringName(), item.getStringCount(),
					 * item.getStringDate(), item.getStringSection()); double
					 * width = Main.get_stage().getWidth(); double height =
					 * Main.get_stage().getHeight(); Main.get_stage()
					 * .setScene(new Scene(
					 * MainWindowController.setScene(MainWindowController.
					 * getCurrentSection(), false), width - 16.5, height - 39));
					 * 
					 * } DaoStorageItemImplements.getInstance().CloseDB(); }
					 * catch (SQLException e) { // TODO Auto-generated catch
					 * block e.printStackTrace(); } catch
					 * (ClassNotFoundException e) { // TODO Auto-generated catch
					 * block e.printStackTrace(); }
					 * 
					 * }
					 */

				}
			}
		};
	}

	public static EventHandler<ActionEvent> exportAction() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				if (e.getSource() == generatePDF) {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Save Document");
					FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF", "*.pdf");
					fileChooser.getExtensionFilters().addAll(extFilter);
					File file = fileChooser.showSaveDialog(Main.get_stage());
					if (file != null) {
						String fileName = file.getName();
						if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
							fileName = fileName.substring(fileName.lastIndexOf("."));
						if (file != null && fileName.equals(".pdf")) {
							try {
								GeneratePDF.createPDF(file.toString());
							} catch (FileNotFoundException | ClassNotFoundException | DocumentException | SQLException
									| InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();

							}
						}
					}

				}
			}
		};
	}

	public static EventHandler<ActionEvent> ago_Action() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				if (e.getSource() == ago_btn) {
					storageList.clear();
					Main.get_stage().setResizable(false);
					Main.get_stage().setScene(new Scene(InputController.getPane(), 270, 135));
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
				if (e.getSource() == search_btn) {
					if (section_Box.getValue().toString() == "Films") {
						myNum = 2;
					} else if (section_Box.getValue().toString() == "Books") {
						myNum = 1;
					} else if (section_Box.getValue().toString() == "Documents") {
						myNum = 4;
					} else if (section_Box.getValue().toString() == "Audio") {
						myNum = 3;
					}
					Main.get_stage().setScene(
							new Scene(WindowController.setTableScene(myNum, search_field.getText()), 540, 400));

				}
			}

		};

	}

	public static EventHandler<ActionEvent> browse_Action() {

		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if (e.getSource() == browse_btn) {
					if (section_Box.getValue().toString() == "Films") {
						myNum = 2;
					} else if (section_Box.getValue().toString() == "Books") {
						myNum = 1;
					} else if (section_Box.getValue().toString() == "Documents") {
						myNum = 4;
					} else if (section_Box.getValue().toString() == "Audio") {
						myNum = 3;
					}
					File_Chooser.Create_Choose_Window(myNum);

					try {
						if (File_Chooser.add_Entry(InputController.getLogField(), myNum, InputController.getUserFlag()))
							Main.get_stage().setScene(new Scene(WindowController.setTableScene(myNum, ""), 540, 400));
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		};
	}

	public static String getCurrentSection() {
		return section_Box.getValue().toString();
	}

	public static int getMyNum() {
		return myNum;
	}

	public static LinkedList<Data> getList() {
		return storageList;
	}
}
