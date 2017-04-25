package parallel.example;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * ForkJoin을 사용한 것과 비교하기 위해 for문을 사용하여 진행
 * @author july
 *
 */
public class NormalSample {

	public void check(){
		String filePath = this.getClass().getResource("/parallel_sample_file").getPath();
		Path path = FileSystems.getDefault().getPath(filePath.substring(1, filePath.length()));
		
		try {
			List<String> readAllLines = Files.readAllLines(path);
			
			String source = readAllLines.get(0);
			boolean result = true;
			
			for (String s : readAllLines) {
				if(!source.equals(s)){
					result = false;
				}
			}
			
			System.out.println("result ="+result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
