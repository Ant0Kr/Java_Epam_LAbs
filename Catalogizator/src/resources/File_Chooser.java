package resources;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import models.Data;

import java.io.File;
import java.sql.SQLException;

import controllers.WindowController;
import implementations.*;

/**
 * 
 * <br>
 * <b>Description:</b><br>
 * Class that provides window for choose adding file, and perform adding file in
 * database. This class also can to perform check available data size for adding
 * files.
 *
 */
public class File_Chooser {

	static File file = null;

	/**
	 * 
	 * @param num
	 * 
	 *            <br>
	 *            <b>Description:</b><br>
	 *            This method create window for choose adding files.
	 */
	public static void Create_Choose_Window(int num) {
		FileChooser fileChooser = new FileChooser();// Класс работы с диалогом
													// выборки и сохранения
		fileChooser.setTitle("Open Document");// Заголовок диалога

		switch (num) {
		case 1:
			fileChooser.getExtensionFilters()
					.addAll(new FileChooser.ExtensionFilter("TEXT files", "*.txt", "*.pdf", "*.djvu"));
			break;
		case 3:
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3", "*.mp3"));
			break;
		case 2:
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Video files", "*.avi", "*.mov", "*.flv", "*.mp4", "*mpeg"));
			break;
		case 4:
			fileChooser.getExtensionFilters()
					.addAll(new FileChooser.ExtensionFilter("Doc files", "*.docx", "*.doc", "*.txt"));
			break;
		}
		file = fileChooser.showOpenDialog(null);

	}

	/**
	 * 
	 * @param user_name
	 * @param num
	 * @param user_flag
	 * @return Boolean
	 * @throws ClassNotFoundException
	 * @throws SQLException<br>
	 * 
	 * @Description This Method perform adding selected files in database, and
	 *              also perform checked available data size for user.
	 * 
	 */
	public static Boolean add_Entry(String user_name, int num, Boolean user_flag)
			throws ClassNotFoundException, SQLException {

		if (file == null)
			return false;
		UserDaoImplements.getInstance().ConnectionDB();
		if (user_flag) {

			UserDaoImplements.getInstance().check_Date();
			double size = UserDaoImplements.getInstance().get_Size(user_name);

			if (size < (double) file.length() / 1048576) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText("Error!");
				alert.setContentText("Your can't adding this file.\nYou don't have enough memory!");
				alert.showAndWait();
				UserDaoImplements.getInstance().CloseDB();
				return false;
			} else {

				UserDaoImplements.getInstance().edit_Size(user_name, size - ((double) file.length() / 1048576));
				UserDaoImplements.getInstance().CloseDB();
			}
		}
		String fileName = file.getName();

		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			fileName = fileName.substring(fileName.lastIndexOf("."));
		else
			fileName = "";
		DataDaoImplements.getInstance().ConnectionDB();
		double d = (double) file.length() / 1048576;
		DataDaoImplements.getInstance().WriteDB(get_Name(file.getName().toString()), file.toString(), fileName,
				Double.toString(d), num);
		WindowController.getList().addFirst(new Data(get_Name(file.getName().toString()), file.toString(), fileName,
				Double.toString(d)));
		DataDaoImplements.getInstance().CloseDB();
		return true;

	}

	/**
	 * 
	 * @param st
	 * @return String Method that returns file_name without existance
	 */
	public static String get_Name(String st) {

		int index = st.indexOf(".");
		return st.substring(0, index);
	}

}