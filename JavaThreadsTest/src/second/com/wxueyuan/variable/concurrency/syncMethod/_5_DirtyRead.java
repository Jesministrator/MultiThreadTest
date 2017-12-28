package second.com.wxueyuan.variable.concurrency.syncMethod;

public class _5_DirtyRead {
	
	private String name = "defaultName";
	
	synchronized public void setName(String s) {
		System.out.println(Thread.currentThread().getName()+" enter setName method");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.name = s;
		System.out.println(Thread.currentThread().getName()+" setName as "+ s);
		
	}
	
	 public void getName() {
		System.out.println(Thread.currentThread().getName()+" getName = "+this.name);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		_5_DirtyRead ref = new _5_DirtyRead();
		SetThread st = new SetThread(ref);
		st.start();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ref.getName();
	}

}

class SetThread extends Thread{
	private _5_DirtyRead ref ;
	
	public SetThread(_5_DirtyRead ref) {
		this.setName("SetThread");
		this.ref = ref;
	}
	
	@Override
	public void run() {
		ref.setName("Jesmin");
	}
}
