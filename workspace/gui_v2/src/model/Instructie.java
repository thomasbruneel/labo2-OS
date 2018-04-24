package model;

public class Instructie {
	private int pid;
	private String operatie;
	private long virtueelAdres;
	private int aantal;
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getOperatie() {
		return operatie;
	}
	public void setOperatie(String operatie) {
		this.operatie = operatie;
	}
	public long getVirtueelAdres() {
		return virtueelAdres;
	}
	public void setVirtueelAdres(long virtueelAdres) {
		this.virtueelAdres = virtueelAdres;
	}
	public int getAantal() {
		return aantal;
	}
	public void setAantal(int aantal) {
		this.aantal = aantal;
	}
	
	public Instructie(int pid,String operatie,long virtueelAdres){
		this.pid=pid;
		this.operatie=operatie;
		this.virtueelAdres=virtueelAdres;
	}
	
	
	

}
