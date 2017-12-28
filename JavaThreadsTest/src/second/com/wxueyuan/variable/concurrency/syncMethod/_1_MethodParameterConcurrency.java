package second.com.wxueyuan.variable.concurrency.syncMethod;

public class _1_MethodParameterConcurrency {
	
	/**
	 * 如果两个线程同时去修改方法内的局部变量，并不会出现线程不安全的情况
	 * 因为方法内的变量是线程私有的不会出现
	 * 调用方法时，该方法的栈帧会被分别压入到线程私有的虚拟机栈中
	 * @param s
	 */
	public void sayHello(String s) {
		try {
			String ret = "hello to";
			if(s.equals("a")) {
				ret = ret.concat(" a");
				System.out.println("a set over");
				Thread.sleep(2000);
				
			}else {
				ret = ret.concat(" b");
				System.out.println("b set over");
			}
			System.out.println("ret = " + ret);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		_1_MethodParameterConcurrency ref = new _1_MethodParameterConcurrency();
		ThreadA ta = new ThreadA(ref);
		ThreadB tb = new ThreadB(ref);
		ta.start();
		tb.start();
	}

}

class ThreadA extends Thread{
	private _1_MethodParameterConcurrency ref;
	
	public ThreadA(_1_MethodParameterConcurrency ref) {
		this.ref = ref;
	}
	
	@Override
	public void run() {
		ref.sayHello("a");
	}
}

class ThreadB extends Thread{
	private _1_MethodParameterConcurrency ref;
	
	public ThreadB(_1_MethodParameterConcurrency ref) {
		this.ref = ref;
	}
	
	@Override
	public void run() {
		ref.sayHello("b");
	}
}