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
            Proces procesToRemove = null;
            for (Proces p:inRAM){
                if(p.getPid() == pte.getPid()) procesToRemove = p;
            }
            inRAM.remove(procesToRemove);
            removeFromRAM(procesToRemove);
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
            removePageFromRAM(pte);
            framesOfP = framesOfP(p.getPid());
        }
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

            Boolean inRam = false;
            for(PageTableEntry frame: framesOfP){
                if(frame.getPageNumber() == pagenr){
                    inRam = true;
                }
            }

            PageTableEntry page = proces.getPage(pagenr);
            page.setPresentBit(1);
            if (page.getModifyBit() == 0) page.setModifyBit(modifybit);
            page.setLastAccessTime(time);

            if(!inRam){
                naarRAM++;
                if(framesOfP.size() < 12/inRAM.size()){
                    addtoRam(page);
                } else {
                    PageTableEntry pageToRemove = lruProces(proces);

                    removePageFromRAM(pageToRemove);

                    addtoRam(page);
                }
            }
        }
    }

    private void addtoRam(PageTableEntry page) {

        boolean toegevoegd = false;
        int frameTeVervangen = Integer.MAX_VALUE;
        for(PageTableEntry frame:frames) {
            if (!toegevoegd && frame.getPageNumber() == -1) {
                frameTeVervangen = frame.getFrameNumber();
                toegevoegd = true;
            }
        }
        page.setFrameNumber(frameTeVervangen);
        frames.set(frameTeVervangen, new PageTableEntry(page));
    }

    private PageTableEntry lruProces(Proces proces) {
        PageTableEntry pageToRemove = new PageTableEntry();
        for(PageTableEntry page : proces.getPageTabel()){
            if(page.getPresentBit() == 1){
                if(page.getLastAccessTime() < pageToRemove.getLastAccessTime()){
                    pageToRemove = page;
                }
            }
        }

        return pageToRemove;
    }


    private PageTableEntry lruRAM() {
        PageTableEntry lru = new PageTableEntry();
        for (PageTableEntry frame: frames){
            if(lru.getLastAccessTime() > frame.getLastAccessTime()) lru = frame;
        }
        return lru;
    }

    private void removeFromRAM(Proces procesToRemove) {
        for (PageTableEntry page:procesToRemove.getPageTabel()){
            if(page.getPresentBit() == 1){
                removePageFromRAM(page);
            }
        }
    }

    private void removePageFromRAM(PageTableEntry pageToRemove) {
        pageToRemove.setPresentBit(0);
        pageToRemove.setFrameNumber(-1);
        if(pageToRemove.getModifyBit() == 1) naarDISC++;
        pageToRemove.setModifyBit(0);

        int frameToReset = -1;
        for (PageTableEntry frame: frames){
            if(frame.getPid() == pageToRemove.getPid() && frame.getPageNumber() == pageToRemove.getPageNumber()){
                frameToReset = frame.getFrameNumber();
            }
        }
        frames.set(frameToReset, new PageTableEntry(frameToReset,-1,-1));
    }

}
