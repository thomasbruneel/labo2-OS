package model;

import java.util.ArrayList;
import java.util.List;

public class Proces {
	private int pid;
	private List<PageTableEntry> pageTabel; 

	
	
	public Proces(int pid) {
		this.pid=pid;
		pageTabel=new ArrayList<>();
		for(int i=0;i<16;i++){
			PageTableEntry pte=new PageTableEntry(pid,i,0,0,-1,-1);
			pageTabel.add(pte);
		}
	}
	
	public int getPid() {
		return pid;
	}

	public List<PageTableEntry> getPageTabel() {
		return pageTabel;
	}

	public void setPageTabel(List<PageTableEntry> pageTabel) {
		this.pageTabel = pageTabel;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}


	public PageTableEntry getPage(int pagenr) {
		PageTableEntry pte = null;
		for (PageTableEntry p: pageTabel){
			if(p.getPageNumber() == pagenr) pte = p;
		}
		return pte;
	}
}