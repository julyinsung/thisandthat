package file.example;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class FileHelpTest {

	Path base;
	String fromFolder = "folder_a";
	String toFolder = "folder_b";
	
	@Before
	public void setup(){
//		String fromPath = "C:\Users\july\Documents\testfiles";
		base = FileSystems.getDefault().getPath("c:", "Users", "july", "Documents", "testfiles");
	}
	
	@Test
	public void testCopy() {
		Path from = base.resolve(fromFolder);
		Path to = base.resolve(toFolder);
		try {
			Iterator<Path> it = Files.list(from).iterator();
			while(it.hasNext()){
				Path source = it.next();
				Path target =  to.resolve(source.getFileName());
				System.out.println(source.toString());
				Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMove() {
		Path from = base.resolve(fromFolder);
		Path to = base.resolve(toFolder);
		try {
			Iterator<Path> it = Files.list(from).iterator();
			while(it.hasNext()){
				Path source = it.next();
				Path target =  to.resolve(source.getFileName());
				System.out.println(source.toString());
				Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
