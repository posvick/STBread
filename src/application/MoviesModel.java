package application;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class MoviesModel {

	private SimpleStringProperty chanell;
    private SimpleStringProperty program;
    private SimpleStringProperty date;
    private SimpleLongProperty size;
    private SimpleStringProperty filename;
    
    public MoviesModel(String chanell, String program, String date, long size, String filename) {
    	this.chanell = new SimpleStringProperty(chanell);
    	this.program = new SimpleStringProperty(program);
    	this.date = new SimpleStringProperty(date);
    	this.size = new SimpleLongProperty(size);
    	this.filename = new SimpleStringProperty(filename);
    }
	public String getChanell() {
		return this.chanell.get();
	}
	public void setChanell(String chanell) {
		this.chanell = new SimpleStringProperty(chanell);
	}
	public String getProgram() {
		return this.program.get();
	}
	public void setProgram(String program) {
		this.program = new SimpleStringProperty(program);
	}
	public String getDate() {
		return this.date.get();
	}
	public void setDate(String date) {
		this.date = new SimpleStringProperty(date);
	}
	public long getSize() {
		return this.size.get();
	}
	public void setSize(long size) {
		this.size = new SimpleLongProperty(size);
	}
	public String getFilename() {
		return this.filename.get();
	}
	public void setFilename(String filename) {
		this.filename = new SimpleStringProperty(filename);
	}
	
}
