package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;

public class MainController implements Initializable {
	@FXML
	private Button pickFolder;
	@FXML
	private TextField folderPath;
	@FXML
	private TableView<MoviesModel> table;
	@FXML
	private TableColumn<MoviesModel, String> colChanell;
	@FXML
	private TableColumn<MoviesModel, String> colProgram;
	@FXML
	private TableColumn<MoviesModel, String> colDate;
	@FXML
	private TableColumn<MoviesModel, Long> colSize;
	@FXML
	private Button readMovies;
	@FXML
	private Button deleteSelected;
	@FXML
	private ProgressBar diskSize;
	@FXML
	private Label usedSpace;
	
	private ObservableList<MoviesModel> moviesModels = FXCollections.observableArrayList();

	public void pickFolderClick(ActionEvent event) {
		DirectoryChooser dc = new DirectoryChooser();
		File selectedFolder = dc.showDialog(null);
		folderPath.setText(selectedFolder.getPath());
	}
	
	public void readMoviesClick(ActionEvent event) {
		Tools tools = new Tools();
		InfoFile[] files;
		File folder;
		
		try {
			folder = tools.open(folderPath.getText());
			files = tools.read(folder);
			
			for (InfoFile movie : files) {
				moviesModels.add(new MoviesModel(movie.channel, movie.program, movie.beginDate, movie.size(2), movie.filename));
			}
			deleteSelected.setDisable(false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		double percent = (double)(tools.getDiskSize(folderPath.getText())-tools.getFreeSize(folderPath.getText()))/tools.getDiskSize(folderPath.getText());
		String value = String.format("%5d/%5d GB", tools.getDiskSize(folderPath.getText())-tools.getFreeSize(folderPath.getText()), tools.getDiskSize(folderPath.getText()));
		usedSpace.setText(value);
		diskSize.setProgress(percent);
	}
	
	public void deleteSelectedClick(ActionEvent event) throws IOException {
		Tools tools = new Tools();
		ObservableList<MoviesModel> selected = table.getSelectionModel().getSelectedItems();
		for (MoviesModel movie : selected) {
			File dir = new File(folderPath.getText());
			File[] files = dir.listFiles((d, name) -> name.startsWith(movie.getFilename()));
		
			for (File data : files) {
				Files.deleteIfExists(data.toPath());
			}
			
			moviesModels.remove(movie);
		}
		String value = String.format("%5d/%5d GB", tools.getDiskSize(folderPath.getText())-tools.getFreeSize(folderPath.getText()), tools.getDiskSize(folderPath.getText()));
		usedSpace.setText(value);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        colChanell.setCellValueFactory(new PropertyValueFactory<>("Chanell"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("Program"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("Size"));
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setItems(moviesModels);
	}
}