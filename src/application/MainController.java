package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.scene.control.TableView;

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
	private Button printSelected;
	
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
				moviesModels.add(new MoviesModel(movie.channel, movie.program, movie.beginDate, movie.size(2)));
			}
			printSelected.setDisable(false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void printSelectedClick(ActionEvent event) {
		ObservableList<MoviesModel> selected = table.getSelectionModel().getSelectedItems();
		for (MoviesModel movie : selected) {
			System.out.println(movie.getProgram());
		}
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