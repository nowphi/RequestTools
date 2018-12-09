package org.verivoxclientDAO;

public class VeriVoxThread implements Runnable {

	protected Thread t;
	protected IHttpsVeriVoxClient VeriVoxClient;
	protected String response;
	
	public VeriVoxThread() {
		VeriVoxClient = new HttpsVeriVoxClient();
	}
	public Thread WorkingThread() {
		return t;
	}
	public boolean isWorking() {
		return t.isAlive();
	}
	public void start() {
	      if (t == null) {
	         t = new Thread(this);
	         t.start ();
	      }
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return response;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub	
	}
}
