package model;

import java.util.ArrayList;
import java.util.List;

public class RAM {

    private List<Frame> frames;
    private List<Proces> inRAM;

    public RAM() {
        frames = new ArrayList<>();
        inRAM = new ArrayList<>();
    }

    public boolean hasProces(Proces p){return inRAM.contains(p);}

    public int frmesPP(){return inRAM.size();}



    public void clear() {
        frames = new ArrayList<>();
        inRAM = new ArrayList<>();
    }
}
