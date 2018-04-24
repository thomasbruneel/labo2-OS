package model;

public class PageTableEntry {
	
	private int pid;
	private boolean presentBit;
	private boolean modifyBit;
	private int pageNumber;
	private int frameNumber;
	private int lastAccessTime;
	
	public PageTableEntry(){
		presentBit = false;
		modifyBit = false;
		lastAccessTime = 0;
		frameNumber = -1;
	}
	
	public PageTableEntry(boolean presentBit, boolean modifyBit, int frameNumber, int lastAccessTime) {
		super();
		this.presentBit = presentBit;
		this.modifyBit = modifyBit;
		this.frameNumber = frameNumber;
		this.lastAccessTime = lastAccessTime;
	}
	
	//getters en setters
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public boolean isPresentBit() {
		return presentBit;
	}
	public void setPresentBit(boolean presentBit) {
		this.presentBit = presentBit;
	}
	public boolean isModifyBit() {
		return modifyBit;
	}
	public void setModifyBit(boolean modifyBit) {
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
