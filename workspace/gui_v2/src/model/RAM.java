package model;

import java.util.ArrayList;
import java.util.List;

public class RAM {

    private List<PageTableEntry> frames;
    private List<Proces> inRAM;

    public RAM() {
        frames = new ArrayList<>();
        inRAM = new ArrayList<>();
    }

    public boolean hasProces(Proces p){return inRAM.contains(p);}

    public int framesPP(){
        if (inRAM.size() == 0) return 0;
        return 12/inRAM.size();
    }



    public void clear() {
        frames = new ArrayList<>();
        inRAM = new ArrayList<>();
    }

    public void terminate(Proces weg) {
    }
}
