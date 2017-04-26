package parallel.example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * stream API를 이용하여 중복제거.
 * 병렬처리를 위해 parallerStream()을 사용했고. 중복제거를 위해 distinct()를 이용.
 *  
 * @author july
 */
public class StreamSample {

	/**
	 * 중복저게를 했으므로 결과가 1이상이면 다른 문자열이 존재한다고 판단한다.
	 * 
	 * @param list
	 */
	public void distinct(List<String> list){
		List<String> result = 
				list.parallelStream()
					.distinct()
					.collect(Collectors.toList());
		
		System.out.println(">>> size: "+result.size());
	}
}
