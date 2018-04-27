package model;

public class PageTableEntry {
	
	private int pid;
	private int presentBit;
	private int modifyBit;
	private int pageNumber;
	private int frameNumber;
	private int lastAccessTime;
	
	public PageTableEntry(){
		pid = -1;
		presentBit = 0;
		modifyBit = 0;
		lastAccessTime = Integer.MAX_VALUE;
		frameNumber = -1;
	}
	
	public PageTableEntry(int pid, int pageNumber,int presentBit,int modifyBit,int lastAccessTime,int frameNumber) {	//nodig voor pagetable op te stellen
		this.pid = pid;
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

	public PageTableEntry(PageTableEntry page) {
		this.pid = page.pid;
		this.presentBit = page.presentBit;
		this.frameNumber = page.frameNumber;
		this.modifyBit = page.modifyBit;
		this.lastAccessTime = page.lastAccessTime;
		this.pageNumber = page.pageNumber;
	}

	//getters en setters
	public int getPid() {
		return pid;
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
