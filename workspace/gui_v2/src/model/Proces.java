package model;

import java.util.ArrayList;

public class Proces {
	private int pid;
	private List<PageTableEntry> pageTabel; 

	
	
	public Proces(int pid) {
		this.pid=pid;
		pageTabel=new ArrayList<>();
		for(int i=0;i<16;i++){
			PageTableEntry pte=new PageTableEntry(i,-1,-1,-1,-1);
			pageTabel.add(pte);
		}
	}
	
	public int getPid() {
		return pid;
	}

}