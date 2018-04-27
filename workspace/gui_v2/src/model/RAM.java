package model;

import java.util.ArrayList;
import java.util.List;

public class RAM {

    private List<PageTableEntry> frames;
    private List<Proces> inRAM;
    private int naarRAM;
    private int naarDISC;
    private List<Proces> alleProcessen;

    //Constructor die de RAM in een vaste starttoestand initialiseerd
    public RAM() {
        frames = new ArrayList<>();
        for(int i=0; i<12; i++) frames.add(new PageTableEntry(i,-1,-1));
        inRAM = new ArrayList<>();
        naarRAM = 0;
        naarDISC = 0;
        alleProcessen = new ArrayList<>();
    }

    //controleert of een proces geheugen gealloceerd heeft in het RAM
    public boolean hasProces(int pid){
        boolean hasProces = false;
        for (Proces p: inRAM) if (p.getPid() == pid) hasProces = true;
        return hasProces;
    }

    //herorganiseert het RAM-geheugen: verwijdert het minst actieve proces
    //of verlaagt het aantal frames dat elk proces mag innemen
    public void makeRoom(Proces proces) {
        if(inRAM.size() == 4){
            PageTableEntry pte = lruRAM();
            Proces procesToRemove = null;
            for (Proces p:inRAM){
                if(p.getPid() == pte.getPid()) procesToRemove = p;
            }
            removeFromRAM(procesToRemove);
        }
        else{
            for(Proces p:inRAM) {
                sizeDown(p);
            }
        }
        inRAM.add(proces);
    }

    //Initialiseert een nieuw proces alvoorens geheugen te alloceren
    public void makeRoom(int pid){
        Proces proces = null;
        for(Proces p:alleProcessen) if(p.getPid() == pid) proces = p;
        makeRoom(proces);
    }

    //verwijdert het minst avtieve frame
    private void sizeDown(Proces p) {
        List<PageTableEntry> framesOfP = framesOfP(p.getPid());
        while (framesOfP.size() > 12/(inRAM.size()+1)) {
            PageTableEntry pte = lruProces(p);
            removePageFromRAM(pte);
            framesOfP = framesOfP(p.getPid());
        }
    }

    //Voert de read/write uit door een kopie van de juiste page van een proces op een lege plaats in het RAM te plaatsen
    public void newInstruction(int pid, int pagenr, int modifybit, int time) {
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

    //methode die een pge kopieert naar een frame in het RAM
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

    //bepaalt de lru page van een proces
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

    //bepaalt de lru frame van het volledige RAM
    private PageTableEntry lruRAM() {
        PageTableEntry lru = new PageTableEntry();
        for (PageTableEntry frame: frames){
            if(lru.getLastAccessTime() > frame.getLastAccessTime()) lru = frame;
        }
        return lru;
    }

    //Verwijdert elke actieve page van een proces uit het RAM
    public void terminate(int weg) {
        Proces proces = null;
        for(Proces p:alleProcessen) if(p.getPid() == weg) proces = p;
        removeFromRAM(proces);
    }

    //methode die elke actieve page van een proces uit het RAM haalt
    private void removeFromRAM(Proces procesToRemove) {
        for (PageTableEntry page:procesToRemove.getPageTabel()){
            if(page.getPresentBit() == 1){
                removePageFromRAM(page);
            }
        }
        inRAM.remove(procesToRemove);
    }

    //methode die een page uit het RAM haalt
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

    //geeft alle actieve frames van een bepaald proces
    private List<PageTableEntry> framesOfP(int pid) {
        List<PageTableEntry>  framesOfP= new ArrayList<>();
        for(PageTableEntry pte:frames) if(pte.getPid() == pid) framesOfP.add(pte);
        return framesOfP;
    }

    //geeft de pagetable van een proces terug op basis van de procesID
    public List<PageTableEntry> geefPageTableProces(int i){
        List<PageTableEntry> lijst=null;
        for(Proces p:alleProcessen){
            if(p.getPid()==i){
                lijst=p.getPageTabel();
            }
        }
        return lijst;
    }

    //getters en setters
    public List<PageTableEntry> getFrames() {
        return frames;
    }

    public int getNaarRAM() {
        return naarRAM;
    }

    public int getNaarDISC() {
        return naarDISC;
    }

    public void newProces(Proces p) {
        alleProcessen.add(p);
    }

}
