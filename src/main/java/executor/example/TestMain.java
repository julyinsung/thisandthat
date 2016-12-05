package executor.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestMain {

	public void test() {
		
		CallableImpl task1 = new CallableImpl(1, 2);
		CallableImpl task2 = new CallableImpl(4, 3);
		
		ExecutorService service = Executors.newFixedThreadPool(2);
		Future<Boolean> future1 = service.submit(task1);
		Future<Boolean> future2 = service.submit(task2);
		
		try {
			boolean rst1 = future1.get();
			boolean rst2 = future2.get();
			System.out.println("rst1: "+rst1);
			System.out.println("rst2: "+rst2);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
