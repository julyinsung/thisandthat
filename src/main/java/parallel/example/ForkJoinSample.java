package parallel.example;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 파일을 읽어서(2000000line) 이중 다른 단어가 존재하는지 ForkJoin을 사용하여 구현
 * (http://www.popit.kr/java8-stream%EC%9D%98-parallel-%EC%B2%98%EB%A6%AC/)
 * 
 * 아래 NormalSample는 이것과 비교하기 위한 클래스로 for문만을 이용하여 구현.
 * 결과는 for문이 약간 더 빠르다. 
 * 
 * @author july
 *
 */
public class ForkJoinSample extends RecursiveTask<Boolean>{
	
	static final int SEQUENTIAL_THRESHOLD = 500000; // 충분히 작은 단위는 여기서 5로 정했다. 정하기 나름.
	
	int low;   // 배열의 시작 index
    int high;  // 배열의 끝 index
    List<String> array; // 전체 배열의 reference
	String source; // 첫번째 라인의 단어
    
	public ForkJoinSample(List<String> arr, int lo, int hi) {
		array = arr;
        low   = lo;
        high  = hi;
        source = arr.get(0);
	}
	
	@Override
	protected Boolean compute() {
		if(high - low <= SEQUENTIAL_THRESHOLD) { // 충분히 작은 배열 구간이면 값을 계산해 리턴한다.
			boolean result = true;

			for(int i = low; i < high; i++){
				if(!source.equals(array.get(i))){
					result = false;
				}
			}
			
			System.out.println(Thread.currentThread().getName() + ", low=" + low + ", high=" + high + ", result=" + result);
			
            return result ;
            
         } else { 
            // 배열이 기준보다 크다면 divede and conquer방식으로 적당히 둘이상의 객체로 나누고, 
            // 현재 쓰레드에서 처리할 객체는 compute를 호출해 값을 계산하고, fork할 객체는 join하여 값을 기다린후 얻는다.           
            int mid = low + (high - low) / 2;
            //System.out.println("values low["+low+"], high["+high+"], mid["+ mid +"]");

            ForkJoinSample left  = new ForkJoinSample(array, low, mid);
            ForkJoinSample right = new ForkJoinSample(array, mid, high);
            left.fork();
            Boolean rightAns = right.compute();
            Boolean leftAns  = left.join();
            
            if(rightAns && leftAns){
            	return true;
            } else {
            	return false;
            }
         }
	}

	static Boolean read(List<String> readAllLines){
		// ForkJoinPool의 시작전과 후의 thread pool size를 비교한다.
        int beforeSize=ForkJoinPool.commonPool().getPoolSize();
        System.out.println("ForkJoin commonPool beforeSize="+beforeSize);

        // common pool: 자동으로 pool갯수를 정해준다.
		boolean result = ForkJoinPool.commonPool().invoke(new ForkJoinSample(readAllLines, 0, readAllLines.size()));
		
        // custom pool: pool 갯수를 임의로 정할 수 있다.
//        ForkJoinPool myPool = new ForkJoinPool(3);
//        boolean result = myPool.invoke(new ForkJoinSample(readAllLines, 0, readAllLines.size()));
		
        int afterSize=ForkJoinPool.commonPool().getPoolSize();
        System.out.println("ForkJoin commonPool afterSize="+afterSize);
        return result;
	}

}
