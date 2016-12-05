package executor.example;

import java.util.concurrent.Callable;

public class CallableImpl implements Callable<Boolean>{

	private int a;
	private int b;
	
	public CallableImpl() {
	}
	
	public CallableImpl(int a, int b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public Boolean call() throws Exception {
		Math m = new Math();
		boolean result = m.cal(a, b);
		return result;
	}
}
