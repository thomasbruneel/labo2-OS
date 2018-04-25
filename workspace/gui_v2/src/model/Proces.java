package model;

import java.util.ArrayList;

public class Proces {
	private int pid;
	private ArrayList<Page> pages;

	
	
	public Proces(int pid){
		this.pid=pid;
		for(int i=0;i<16;i++) pages.add(new Page(pid));
	}
	
	public int getPid() {
		return pid;
	}

}