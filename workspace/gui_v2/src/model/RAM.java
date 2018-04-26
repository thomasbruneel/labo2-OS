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
        for(int i=0; i<12; i++) frames.add(new PageTableEntry());
        inRAM = new ArrayList<>();
        naarRAM = 0;
        naarDISC = 0;
        alleProcessen = new ArrayList<>();
    }

    public boolean hasProces(Proces p){return inRAM.contains(p);}

    public void clear() {
        frames = new ArrayList<>();
        for(int i=0; i<12; i++) frames.add(new PageTableEntry());
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

            for(Proces p:inRAM) sizeDown(p);

        }
        inRAM.add(proces);
    }

    private void sizeDown(Proces p) {
        List<PageTableEntry> framesOfP = framesOfP(p.getPid());
        while (framesOfP.size() > 12/(inRAM.size()+1)) {
            PageTableEntry pte = lruProces(p);
            if(pte.wasWritten()) naarDISC++;
            pte.reset();
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
                pte.reset();
            }
        }

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
}
