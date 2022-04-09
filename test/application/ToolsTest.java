package application;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ToolsTest {

	@Test
	void CheckFolderContentTrue() {
		Tools tools = new Tools();
		int result = tools.check("/home/alden/eclipse-test-java-workspace/STBReaderGUI/data/");
		assertEquals(result, 660, "First folder");
		result = tools.check("/home/alden/eclipse-test-java-workspace/STBReader/data/");
		assertEquals(result, 660, "Second folder");
	}
	
	@Test
	void CheckFolderContentFalse() {
		Tools tools = new Tools();
		int result = tools.check("/home/alden/");
		assertEquals(result, 0);
	}
	
	@Test
	void CheckFolderDoesntExist() {
		Tools tools = new Tools();
		int result = tools.check("Non existing path");
		assertEquals(result, 0);
	}
	
	@Test
	void isNotFolder() {
		Tools tools = new Tools();
		FileNotFoundException thrown = Assertions.assertThrows(FileNotFoundException.class,
				() -> {
					tools.open("Nonexisting path");
				}, "Folder doesn't exists!");
		Assertions.assertEquals("Folder doesn't exists!", thrown.getMessage());
	}
	
	@Test
	void ReadAllFilesInArrayFromEmptyFolder() {
		Tools tools = new Tools();
		InfoFile[] files;
		File folder;
		try {
			folder = tools.open("/home/alden/");
			files = tools.read(folder);
			assertEquals(files.length, 0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	void ReadAllFilesInArrayFromFolder() {
		Tools tools = new Tools();
		InfoFile[] files;
		File folder;
		try {
			folder = tools.open("/home/alden/eclipse-test-java-workspace/STBReaderGUI/data/");
			files = tools.read(folder);
			if (files.length!=0) { 
				assertEquals(files.length, tools.check("/home/alden/eclipse-test-java-workspace/STBReaderGUI/data/"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	void PrintFirstRecordNameInFolder() {
		Tools tools = new Tools();
		InfoFile[] files;
		File folder;
		
		try {
			folder = tools.open("/home/alden/eclipse-test-java-workspace/STBReaderGUI/data/");
			files = tools.read(folder);
			
			assertEquals(files[0].toString(), "CT :D/art HD T2 - Vampirina");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	void ConvertUnixtimestampToLocalFormatExist() {
		Tools tools = new Tools();
		String date = tools.convertDate("1609613682");
		assertEquals(date, "02.01.2021");
	}
	
	@Test
	void ConvertUnixtimestampToLocalFormatMaxDate() {
		Tools tools = new Tools();
		String date = tools.convertDate("9999999999");
		assertEquals(date, "20.11.2286");
	}
	
	@Test
	void ConvertUnixtimestampToLocalFormatTime() {
		Tools tools = new Tools();
		String time = tools.convertTime("1609613682");
		assertEquals(time, "19:54");
	}
	
	@Test
	void ConvertUnixtimestampToLocalFormatTimeNext() {
		Tools tools = new Tools();
		String time = tools.convertTime("1609613742");
		assertEquals(time, "19:55");
	}
	
	@Test
	void CheckSizeOfFileZero() {
		Tools tools = new Tools();
		InfoFile[] files;
		File folder;
		
		try {
			folder = tools.open("/home/alden/eclipse-test-java-workspace/STBReaderGUI/data/");
			files = tools.read(folder);
			
			assertEquals(files[0].size(), 0L);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	void PrintMovieNamesWithFileSizes() {
		Tools tools = new Tools();
		InfoFile[] files;
		File folder;
		
		try {
			folder = tools.open("/home/alden/eclipse-test-java-workspace/STBReaderGUI/data/");
			files = tools.read(folder);
			
			for (InfoFile movie : files) {
				System.out.println(movie.filename + " - " + movie.size(2) + "Mb");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	void CheckDiskSize() {
		Tools tools = new Tools();
		double size = tools.getDiskSize("/home/alden/");
		assertEquals(758, size);
	}
	
	@Test
	void CheckFreeDiskSize() {
		Tools tools = new Tools();
		double size = tools.getFreeSize("/home/alden/");
		assertEquals(21, size);
	}
}