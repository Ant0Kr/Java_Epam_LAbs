package models;

import javafx.beans.property.SimpleStringProperty;

/**
 * 
 * <br>
 * <b>Description:</b><br>
 * Class container that contains data that required for work with SQLite
 * database.<br>
 * Class alse required for create different tables.
 *
 */
public class Data {

	private SimpleStringProperty file_name;
	private SimpleStringProperty way;
	private SimpleStringProperty extension;
	private SimpleStringProperty size;

	public Data(String file_name, String way, String extension, String size) {
		this.file_name = new SimpleStringProperty(file_name);
		this.way = new SimpleStringProperty(way);
		this.extension = new SimpleStringProperty(extension);
		this.size = new SimpleStringProperty(size);
	}

	public String getFile_name() {
		return file_name.get();
	}

	public void setName(String fName) {
		file_name.set(fName);
	}

	public String getWay() {
		return way.get();
	}

	public void setAuthor(String fName) {
		way.set(fName);
	}

	public String getExtension() {
		return extension.get();
	}

	public void setData(String fName) {
		extension.set(fName);
	}

	public String getSize() {
		return size.get();
	}

	public void setSize(String fName) {
		size.set(fName);
	}
}
