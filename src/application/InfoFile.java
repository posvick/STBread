package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InfoFile {
	String path;
	String filename;
	String channel;
	String program;
	String info;
	String beginTime;
	String beginDate;
	String endTime;
	String endDate;
	
	public InfoFile(File file) {
		Tools tools = new Tools();
		BufferedReader input;
		String line;
		String tmp;
		try {
			input = new BufferedReader(new FileReader(file));
			while ((line = input.readLine())!= null) {
				if (line.startsWith("channel_name")) channel = line.substring(43);
				if (line.startsWith("program_name")) program = line.substring(43);
				if (line.startsWith("program_mess")) info = line.substring(43);
				if (line.startsWith("begin_time")) {
					tmp = line.substring(43);
					beginTime = tools.convertTime(tmp);
					beginDate = tools.convertDate(tmp);
				}
				if (line.startsWith("end_time")) {
					tmp = line.substring(43);
					endTime = tools.convertTime(tmp);
					endDate = tools.convertDate(tmp);
				}
			}
			filename = file.getName().substring(0, file.getName().length()-5);
			path = file.getPath().substring(0, file.getPath().lastIndexOf(File.separator));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return (channel + " - " + program);
	}

	public Long size() {
		try {
			String tsFile = this.path + "/" + this.filename + ".ts";
			return Files.size(Paths.get(tsFile));
		} catch (IOException e) {
			// .TS file not found
			return 0L;
		}
	}
	
	public Long size(int format) {
		Long tmp = this.size();
		switch (format) {
			case 1:
				tmp /= 1024;
				break;
			case 2:
				tmp /= (1024 * 1024);
				break;
		}
		return tmp;
	}
}
