package model;

public class Instructie {
	private int pid;
	private String operatie;
	private long virtueelAdres;

	public int getPid() {
		return pid;
	}
	public String getOperatie() {
		return operatie;
	}
	public long getVirtueelAdres() {
		return virtueelAdres;
	}

	public Instructie(int pid,String operatie,long virtueelAdres){
		this.pid=pid;
		this.operatie=operatie;
		this.virtueelAdres=virtueelAdres;
	}
	
	
	

}
