package second.com.wxueyuan.variable.concurrency.syncMethod;
/**
 * 这个实验中，我们可以看到instance ret的值是线程不安全的
 * 
 * Thread C a set over
 * Thread D b set over
 * Thread D ret = 200
 * Thread C ret = 200
 * 
 * 当C线程进入到test方法后修改了ret的值为 100，但是sleep 2s
 * 当D线程进入到test方法后，修改了ret的值为200
 * 此时两个线程输出的ret值均为200，由于线程运行顺序的不确定性，因此也有可能两个值都为100
 * 
 * 解决办法很简单，再test方法之前加上synchronized关键字以保证这个方法在
 * 同一时刻只有一个线程能够访问,见syncTest()方法
 * Author: Jesmin
 * Description: 
 *
 */
public class _2_InstanceVariableConcurrency {
	
	private int ret = 0;
	
	 public void test(String s) {

		try {
			if(s.equals("a")) {
				ret = 100;
				System.out.println(Thread.currentThread().getName()+" a set over");
				Thread.sleep(2000);
				
			}else {
				ret = 200;
				System.out.println(Thread.currentThread().getName()+" b set over");
			}
			System.out.println(Thread.currentThread().getName()+" ret = " + ret);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	synchronized public void syncTest(String s) {

			try {
				if(s.equals("a")) {
					ret = 100;
					System.out.println(Thread.currentThread().getName()+" a set over");
					Thread.sleep(2000);
					
				}else {
					ret = 200;
					System.out.println(Thread.currentThread().getName()+" b set over");
				}
				System.out.println(Thread.currentThread().getName()+" ret = " + ret);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		} 
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		_2_InstanceVariableConcurrency ref = new _2_InstanceVariableConcurrency();
		ThreadC tc = new ThreadC(ref);
		ThreadD td = new ThreadD(ref);
		tc.start();
		td.start();
	}

}
class ThreadC extends Thread{
	private _2_InstanceVariableConcurrency ref;
	
	public ThreadC(_2_InstanceVariableConcurrency ref) {
		this.setName("Thread C");
		this.ref = ref;
	}
	
	@Override
	public void run() {
		ref.test("a");
	}
}

class ThreadD extends Thread{
	private _2_InstanceVariableConcurrency ref;
	
	public ThreadD(_2_InstanceVariableConcurrency ref) {
		this.setName("Thread D");
		this.ref = ref;
	}
	
	@Override
	public void run() {
		ref.test("b");
	}
}