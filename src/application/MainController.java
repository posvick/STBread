package application;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.scene.control.TableView;

public class MainController {
	@FXML
	private Button pickFolder;
	@FXML
	private TextField folderPath;
	@FXML
	private TableView table;
	@FXML
	private Button readMovies;

	public void pickFolderClick(ActionEvent event) {
		DirectoryChooser dc = new DirectoryChooser();
		File selectedFolder = dc.showDialog(null);
		folderPath.setText(selectedFolder.getPath());
		
	}
}
