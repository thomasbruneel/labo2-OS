package model;

public class Proces {
	private int pid; 
	private int lastAccesTime;
	
	
	
	public Proces(int pid){
		this.pid=pid;
		this.lastAccesTime=0;
	}
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getLastAccesTime() {
		return lastAccesTime;
	}
	public void setLastAccesTime(int lastAccesTime) {
		this.lastAccesTime = lastAccesTime;
	}
	
	
	
	
	
	
	
	

}