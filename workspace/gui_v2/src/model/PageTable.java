package model;

import java.util.List;

public class PageTable {
	private int id;
	private List<PageTableEntry> pageTable;
	
	
	
	//getters en setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<PageTableEntry> getPageTable() {
		return pageTable;
	}
	public void setPageTable(List<PageTableEntry> pageTable) {
		this.pageTable = pageTable;
	}
	
	
}
