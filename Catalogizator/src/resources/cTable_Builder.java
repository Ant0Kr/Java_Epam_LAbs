package resources;

import java.sql.SQLException;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Data;
import implementations.*;

/**
 * 
 * <br>
 * <b>Description:</b><br>
 * Class that create table for scene.<br>
 * This class perform contact with database, and set data from database in new
 * table.
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class cTable_Builder {

	private static TableView table = null;

	/**
	 * 
	 * @param num
	 * @param st
	 * @return TableView
	 * 
	 *         <br>
	 * 		<b>Description:</b><br>
	 *         Method that build table for main Stage.
	 */
	public static TableView get_table(int num, String st) {

		if (table == null) {
			table = new TableView();

			TableColumn name = new TableColumn("File_name");
			name.setCellValueFactory(new PropertyValueFactory<Data, String>("file_name"));
			name.setMinWidth(190);

			TableColumn author = new TableColumn("Way");
			author.setCellValueFactory(new PropertyValueFactory<Data, String>("way"));
			author.setMinWidth(175);

			TableColumn data = new TableColumn("Extension");
			data.setCellValueFactory(new PropertyValueFactory<Data, String>("extension"));
			data.setMinWidth(80);

			TableColumn size = new TableColumn("Size(mb)");
			size.setCellValueFactory(new PropertyValueFactory<Data, String>("size"));
			size.setMinWidth(60);

			table.getColumns().addAll(name, author, data, size);
		}

		try {
			DataDaoImplements.getInstance().ConnectionDB();

			if (st == "")
				table.setItems(DataDaoImplements.getInstance().Read_Data(num));
			else
				table.setItems(DataDaoImplements.getInstance().Search(st, num));
			table.setMaxWidth(2000);
			table.setPrefHeight(1000);

			DataDaoImplements.getInstance().CloseDB();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return table;
	}

}
