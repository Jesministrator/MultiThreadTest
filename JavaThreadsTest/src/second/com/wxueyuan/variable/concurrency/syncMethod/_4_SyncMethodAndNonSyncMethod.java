package second.com.wxueyuan.variable.concurrency.syncMethod;
/**
 * 从这个例子中我们synchronized关键字修饰方法时，代表着进入这个方法需要获得对象的锁
 * 但是在同一时刻只有一个线程能获得对象的锁
 * 因此在这个例子当中当ThreadG执行syncMethodA()方法时获得了ref对象的锁
 * 那么ThreadH就必须要等待syncMethodA()方法执行完毕，再去申请ref对象的锁，然后开始执行syncMethodB()方法
 * 但是对于ThreadI来说，它需要执行的方法是没有synchronized关键字的nonSyncMethodC，因此它并不需要等待ref对象的锁
 * 而是直接调用了方法，并直接执行
 * Author: Jesmin
 * Description: 
 *
 */
public class _4_SyncMethodAndNonSyncMethod {

	synchronized public void syncMethodA() {
		System.out.println(Thread.currentThread().getName()+" is invoking syncMethodA");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	synchronized public void SyncMethodB() {
		System.out.println(Thread.currentThread().getName()+" is invoking SyncMethodB");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void nonSyncMethodC() {
		System.out.println(Thread.currentThread().getName()+" is invoking nonSyncMethodC");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		_4_SyncMethodAndNonSyncMethod ref = new _4_SyncMethodAndNonSyncMethod();
		ThreadG tg = new ThreadG(ref);
		ThreadH th = new ThreadH(ref);
		ThreadI ti = new ThreadI(ref);
		tg.start();
		th.start();
		ti.start();
	}

}
class ThreadG extends Thread{
	private _4_SyncMethodAndNonSyncMethod ref;
	
	public ThreadG(_4_SyncMethodAndNonSyncMethod ref) {
		this.setName("Thread G");
		this.ref = ref;
	}
	
	@Override
	public void run() {
		ref.syncMethodA();
	}
}

class ThreadH extends Thread{
	private _4_SyncMethodAndNonSyncMethod ref;
	
	public ThreadH(_4_SyncMethodAndNonSyncMethod ref) {
		this.setName("Thread H");
		this.ref = ref;
	}
	
	@Override
	public void run() {
		ref.SyncMethodB();
	}
}

class ThreadI extends Thread{
	private _4_SyncMethodAndNonSyncMethod ref;
	
	public ThreadI(_4_SyncMethodAndNonSyncMethod ref) {
		this.setName("Thread I");
		this.ref = ref;
	}
	
	@Override
	public void run() {
		ref.nonSyncMethodC();
	}
}