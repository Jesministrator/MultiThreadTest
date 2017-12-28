package second.com.wxueyuan.variable.concurrency.syncMethod;
/**
 * 从这个例子中可以看出，尽管实例变量是线程不安全，但是如果传入的是两个不同的对象，它们各自的
 * 实例变量自然不会想同了
 * Author: Jesmin
 * Description: 
 *
 */
public class _3_TwoObjectTwoLock {

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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		_3_TwoObjectTwoLock ref = new _3_TwoObjectTwoLock();
		_3_TwoObjectTwoLock ref2 = new _3_TwoObjectTwoLock();
		ThreadE te = new ThreadE(ref);
		ThreadF tf = new ThreadF(ref2);
		te.start();
		tf.start();
	}

}
class ThreadE extends Thread{
	private _3_TwoObjectTwoLock ref;
	
	public ThreadE(_3_TwoObjectTwoLock ref) {
		this.setName("Thread E");
		this.ref = ref;
	}
	
	@Override
	public void run() {
		ref.test("a");
	}
}

class ThreadF extends Thread{
	private _3_TwoObjectTwoLock ref;
	
	public ThreadF(_3_TwoObjectTwoLock ref) {
		this.setName("Thread F");
		this.ref = ref;
	}
	
	@Override
	public void run() {
		ref.test("b");
	}
}