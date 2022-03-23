package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Tools {

	public File open(String folder) throws FileNotFoundException {
		File tmp = new File(folder);
		if (tmp.exists())
			return(tmp);
		else
			throw new FileNotFoundException("Folder doesn't exists!");
	}
	
	public InfoFile[] read(File folder) {
		InfoFile[] files;
		files = new InfoFile[folder.listFiles().length];
		int i = 0;
		
		for (File file : folder.listFiles()) {
			String filename = file.getName();
			if (filename.endsWith(".info")) {
				files[i++] = new InfoFile(file);
			}
		}
		InfoFile[] ret = new InfoFile[i];
		System.arraycopy(files, 0, ret, 0, i);
		return ret;
	}

	public int check(String folderName) {
		File folder;
		try {
			folder = this.open(folderName);
			String[] files = folder.list(new FilenameFilter() {
				public boolean accept(File folder, String fileName) {
					if (fileName.lastIndexOf(".")==-1) return false;
					if ((fileName.substring(fileName.lastIndexOf("."))).equals(".info"))
						return true;
					else return false;
				}
			});
			return files.length;
		} catch (FileNotFoundException e) {
			System.out.println("Folder doesn't exists or is empty!");
			return 0;
		}
		
	}

	public String convertDate(String dateFromFile) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		long l = Long.parseLong(dateFromFile);
		LocalDate ld = Instant.ofEpochMilli(l*1000).atZone(ZoneId.of("Europe/Prague")).toLocalDate();
		
		return ld.format(formatter);
	}

	public String convertTime(String timeFromFile) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		long l = Long.parseLong(timeFromFile);
		LocalTime lt = Instant.ofEpochMilli(l*1000).atZone(ZoneId.of("Europe/Prague")).toLocalTime();
		
		return lt.format(formatter);
	}
}