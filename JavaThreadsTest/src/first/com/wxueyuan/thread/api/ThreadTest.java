package first.com.wxueyuan.thread.api;

public class ThreadTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testDaemonThread();
	}
	
	/**
	 * isolatedThread方法，单独的三个线程实现类分别交给三个单独的Thread，
	 * 它们的变量是独立的，因此每个线程都会输出到1为止
	 */
	public static void isolatedThread() {
		Thread t1 = new Thread(new MyExecutor(),"thead a");
		Thread t2 = new Thread(new MyExecutor(),"thead b");
		Thread t3 = new Thread(new MyExecutor(),"thead c");
		t1.start();
		t2.start();
		t3.start();
	}
	
	/**
	 * mutualThread方法，三个单独的线程执行着同一个Runnable实现类，此时MyExecutor2中的x是被三个线程共享的
	 * 因此会出现线程不安全的情况，也就是说当x被某个线程修改后比如x--，此时另一个线程并没有及时的看到，而采用了旧的值
	 * 
	 * 
	 * i--操作实际分为了三步：
	 * 1.取出i的值
	 * 2.对i进行-1
	 * 3.将减1后的值再赋给i
	 */
	public static void mutualThread() {
		MyExecutor2 myExecutor = new MyExecutor2();
		Thread t1 = new Thread(myExecutor,"thead a");
		Thread t2 = new Thread(myExecutor,"thead b");
		Thread t3 = new Thread(myExecutor,"thead c");
		t1.start();
		t2.start();
		t3.start();
	}
	
	/**
	 * syncMutualThread方法，在这个例子中尽管x仍然被多个线程所共享，但是我们加了synchronized代码块
	 * 从而保证在同一时刻只有一个线程能够获得myExecutor实例的锁
	 * 当某一个线程获得了锁之后，它去执行代码块中的i--，只有当i--的三步都完成了之后
	 * 也就是线程工作内存中的值都同步回主内存中之后，并由主内存再向各个线程工作内存同步之后
	 * 该线程才会释放锁，由剩下的两个线程去竞争该锁，以此循环直到三个线程都执行完毕
	 */
	public static void syncMutualThread() {
		MyExecutor3 myExecutor = new MyExecutor3();
		Thread t1 = new Thread(myExecutor,"thead a");
		Thread t2 = new Thread(myExecutor,"thead b");
		Thread t3 = new Thread(myExecutor,"thead c");
		t1.start();
		t2.start();
		t3.start();
	}
	
	/**
	 * isAlive()方法是用来判断我们的线程是否处在活跃状态
	 * 即线程已经启动且尚未中止的状态
	 * 所有实现了Runnable接口的类代表着这个类中的run方法可以被我们新建的线程所执行
	 * 在这个例子中我们可以看到由于Thread类实现了Runnable接口，因此当它的对象被当作参数传入到Thread中去之后
	 * 实际上run方法中输出的this.getName是Alive Thread，而AliveThread并没有被当成是一个线程被启动，因此无论如何
	 * this.isAlive()都会返回false
	 */
	public static void testIsAlive() {
		Thread t1 = new Thread(new AliveThread("Alive Thread"),"thead a");
		System.out.println("beforeBegin "+t1.isAlive());
		t1.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("afterBegin " +t1.isAlive());
		
	}

	/**
	 * testIsAlive2是针对上面的方法进行的休整，如果我们直接AliveThread.start()来运行的话
	 * 运行的AliveThread中的run方法中输出的两个name都是Alive Thread
	 * 此时的this.isAlive方法返回的就肯定是true了
	 * 
	 */

	public static void testIsAlive2() {
		AliveThread t1 = new AliveThread("Alive Thread");
		System.out.println("beforeBegin "+t1.isAlive());
		t1.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("afterBegin " +t1.isAlive());
		
	}
	/**
	 * 尽管我们尝试对it进行interrupt，并且输出it.interrupted()，但是这个返回值永远会为false
	 * 因为it.interrupted()返回的是执行这句代码的线程是否被interrupt了
	 * 因此如果我们想要输出一个true，则可以选择Thread.currentThread().interrupt()
	 * 来使main线程interrupted
	 * 但是这个方法会清除interrupted状态，也就是说调用第二次就会返回false了
	 */
	public static void testInterrupted() {
		InterruptedThread it = new InterruptedThread();
		it.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		it.interrupt();
		Thread.currentThread().interrupt();
		System.out.println(it.interrupted());
		System.out.println(it.interrupted());
	}
	/**
	 * 这个testInterrupted2方法用来与上一个方法做对比，使用it.isInterrupted()方法能够判断
	 * it这个线程是否被interrupted
	 * this.interrupted()方法使用来测试当前线程是否在中断状态，执行后会清除状态
	 * this.isInterrupted()方法是用来测试this这个线程对象的是否处于中断状态，执行这个方法不会清除状态
	 */
	public static void testInterrupted2() {
		InterruptedThread it = new InterruptedThread();
		it.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		it.interrupt();
		
		System.out.println(it.isInterrupted());
		System.out.println(it.isInterrupted());
		
	}
	/**
	 * 随着main线程的结束，dt线程也跟着结束了，不会继续输出了
	 * 可以将dt.setDaemon(true);这句话注释了然后对比不同之处。
	 */
	public static void testDaemonThread() {
		DaemonThread dt = new DaemonThread();
		dt.setDaemon(true);
		dt.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("这是main线程的最后的一句代码执行完后，main线程就结束了");
	}

}



class MyExecutor implements Runnable{
	
	private int x = 5;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(x>0) {
			System.out.println(Thread.currentThread().getName()+"   "+x--);
		}
	}
	
}
class MyExecutor2 implements Runnable{
	
	private int x = 5;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(x>0) {
			System.out.println(Thread.currentThread().getName()+"   "+x--);
		}
	}
	
}

class MyExecutor3 implements Runnable{
	
	private int x = 5;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized (this) {
				System.out.println(Thread.currentThread().getName()+"   "+x--);
		}
		
	}
	
}

class AliveThread extends Thread{
	
	public AliveThread(String name) {
		this.setName(name);
	}
	
	@Override
	public void run() {
		System.out.println("run "+this.isAlive());
		System.out.println("currentThreadName "+Thread.currentThread().getName());
		System.out.println("thisName "+this.getName());
	}
	
}

class InterruptedThread extends Thread{
	@Override
	public void run() {
		try{
			for(int i=0;i<500000;i++) {
				System.out.println(i);
				if(this.isInterrupted()) {
					throw new InterruptedException();
				}
					
				System.out.println(i);
			}
		}catch(InterruptedException e) {
			System.out.println("依靠catch来停止代码吧");
			e.printStackTrace();
		}
		
	}
}

class DaemonThread extends Thread{
	@Override
	public void run() {
		int i=0;
		while(true) {
			System.out.println(i++);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
