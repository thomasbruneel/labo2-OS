package model;

import java.util.ArrayList;
import java.util.List;

public class RAM {

    private List<PageTableEntry> frames;
    private List<Proces> inRAM;
    private int naarRAM;
    private int naarDISC;
    private List<Proces> alleProcessen;

    public RAM() {
        frames = new ArrayList<>();
        for(int i=0; i<12; i++) frames.add(new PageTableEntry(i,-1,-1));
        inRAM = new ArrayList<>();
        naarRAM = 0;
        naarDISC = 0;
        alleProcessen = new ArrayList<>();
    }

    public boolean hasProces(int pid){
        boolean hasProces = false;
        for (Proces p: inRAM) if (p.getPid() == pid) hasProces = true;
        return hasProces;
    }

    public void clear() {
        frames = new ArrayList<>();
        for(int i=0; i<12; i++) frames.add(new PageTableEntry(i));
        inRAM = new ArrayList<>();
        naarRAM = 0;
        naarDISC = 0;
        alleProcessen = new ArrayList<>();
    }

    public void terminate(Proces weg) {
    }

    public void makeRoom(Proces proces) {
        if(inRAM.size() == 4){
            PageTableEntry pte = lruRAM();
            Proces dummy = null;
            for (Proces p:inRAM){
                if(p.getPid() == pte.getPid()) dummy = p;
            }
            inRAM.remove(dummy);
            handleWrite(dummy.getPid());
        }
        else{

            for(Proces p:inRAM) {
                sizeDown(p);
            }

        }
        inRAM.add(proces);
    }

    public void makeRoom(int pid){
        Proces proces = null;
        for(Proces p:alleProcessen) if(p.getPid() == pid) proces = p;
        makeRoom(proces);
    }

    private void sizeDown(Proces p) {
        List<PageTableEntry> framesOfP = framesOfP(p.getPid());
        while (framesOfP.size() > 12/(inRAM.size()+1)) {
            PageTableEntry pte = lruProces(p);
            if(pte.wasWritten()) naarDISC++;
            reset(pte);
        }
    }

    private PageTableEntry lruProces(Proces proces) {
        PageTableEntry pte = new PageTableEntry();
        for (PageTableEntry p: frames){
            if((pte.getLastAccessTime() > p.getLastAccessTime()) && (p.getPid() == proces.getPid())) pte = p;
        }
        return pte;
    }

    private List<PageTableEntry> framesOfP(int pid) {
        List<PageTableEntry>  framesOfP= new ArrayList<>();
        for(PageTableEntry pte:frames) if(pte.getPid() == pid) framesOfP.add(pte);
        return framesOfP;
    }

    private void handleWrite(int pid) {
        for(PageTableEntry pte:frames){
            if(pte.getPid() == pid) {
                if(pte.wasWritten()) naarDISC++;
                reset(pte);
            }
        }

    }

    private void reset(PageTableEntry pte) {
        pte.setFrameNumber(-1);
        pte.setModifyBit(0);
        pte.setPresentBit(0);
    }

    private PageTableEntry lruRAM() {
        PageTableEntry pte = new PageTableEntry();
        for (PageTableEntry p: frames){
            if(pte.getLastAccessTime() > p.getLastAccessTime()) pte = p;
        }
        return pte;
    }

    public List<PageTableEntry> getFrames() {
        return frames;
    }

    public List<Proces> getInRAM() {
        return inRAM;
    }

    public int getNaarRAM() {
        return naarRAM;
    }

    public int getNaarDISC() {
        return naarDISC;
    }

    public List<Proces> getAlleProcessen() {
        return alleProcessen;
    }

    public void newProces(Proces p) {
        alleProcessen.add(p);
    }
    public List<PageTableEntry> geefPageTableProces(int i){
    	List<PageTableEntry> lijst=null;
    	for(Proces p:alleProcessen){
    		if(p.getPid()==i){
    			lijst=p.getPageTabel();
    		}
    	}
		return lijst;
    	
    }

    public void newInstruction(int pid, int pagenr, int modifybit, int time) {
        if(!hasProces(pid)) System.out.println("GROTE FOUT!!!");
        else {
            Proces proces = null;
            for(Proces p: alleProcessen) if(p.getPid() == pid) proces = p;
            List<PageTableEntry> framesOfP = framesOfP(pid);
            naarDISC++;
            if(framesOfP.size() < 12/inRAM.size()){
                addtoRam(proces.getPage(pagenr),modifybit,time);

            } else {
                PageTableEntry pte = lruProces(proces);
                if(pte.wasWritten()) naarDISC++;
                reset(pte);
            }
        }
    }

    private void addtoRam(PageTableEntry page, int modifybit, int time) {
        page.setPresentBit(1);
        if (page.getModifyBit() == 0) page.setModifyBit(modifybit);
        page.setLastAccessTime(time);

        boolean toegevoegd = false;
        for(PageTableEntry frame:frames)
            if (!toegevoegd && frame.getPageNumber() == -1){
                frame = page;
                toegevoegd = true;
            }
    }
}
