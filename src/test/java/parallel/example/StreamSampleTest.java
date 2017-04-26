package parallel.example;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class StreamSampleTest {

	@Test
	public void test() {
		// fileName = parallel_sample_file
		String filePath = this.getClass().getResource("/parallel_sample_file").getPath();
		Path path = FileSystems.getDefault().getPath(filePath.substring(1, filePath.length()));
		
		try {
			new StreamSample().distinct(Files.readAllLines(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
