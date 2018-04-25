package model;

public class PageTableEntry {
	
	private int pid;
	private int presentBit;
	private int modifyBit;
	private int pageNumber;
	private int frameNumber;
	private int lastAccessTime;
	
	public PageTableEntry(){
		presentBit = 0;
		modifyBit = 0;
		lastAccessTime = 0;
		frameNumber = -1;
	}
	/*
	public PageTableEntry(int presentBit, int modifyBit, int frameNumber, int lastAccessTime) {
		super();
		this.presentBit = presentBit;
		this.modifyBit = modifyBit;
		this.frameNumber = frameNumber;
		this.lastAccessTime = lastAccessTime;
	}
	*/
	
	public PageTableEntry(int pageNumber,int presentBit,int modifyBit,int lastAccessTime,int frameNumber) {	//nodig voor pagetable op te stellen 
		this.pageNumber=pageNumber;
		this.presentBit=presentBit;
		this.modifyBit=modifyBit;
		this.lastAccessTime=lastAccessTime;
		this.frameNumber=frameNumber;
	
	}
	
	public PageTableEntry(int frameNumber, int pageNumber, int pid) {	//nodig voor ramtable op te stellen 
		this.frameNumber=frameNumber;
		this.pageNumber=pageNumber;
		this.pid=pid;
	}

	//getters en setters
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getPresentBit() {
		return presentBit;
	}

	public void setPresentBit(int presentBit) {
		this.presentBit = presentBit;
	}

	public int getModifyBit() {
		return modifyBit;
	}

	public void setModifyBit(int modifyBit) {
		this.modifyBit = modifyBit;
	}

	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getFrameNumber() {
		return frameNumber;
	}
	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}
	public int getLastAccessTime() {
		return lastAccessTime;
	}
	public void setLastAccessTime(int lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	
	
	


}
