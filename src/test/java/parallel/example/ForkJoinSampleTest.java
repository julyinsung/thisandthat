package parallel.example;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class ForkJoinSampleTest {

	@Test
	public void test() {
		// fileName = parallel_sample_file
		String filePath = this.getClass().getResource("/parallel_sample_file").getPath();
		Path path = FileSystems.getDefault().getPath(filePath.substring(1, filePath.length()));
		
		try {
//			List<String> readAllLines = Files.readAllLines(path);
			
//			String[] array = new String[readAllLines.size()];
			
			boolean result = ForkJoinSample.read(Files.readAllLines(path));
			
			System.out.println("Fork Join result ="+result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
