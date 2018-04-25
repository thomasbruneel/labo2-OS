package model;

import java.util.ArrayList;
import java.util.List;

public class RAM {

    private List<Integer> frames;
    private List<Proces> inRAM;

    public RAM() {
        frames = new ArrayList<>();
        inRAM = new ArrayList<>();
    }

    public boolean hasProces(Proces p){return inRAM.contains(p);}

    public int frmesPP(){
        if (inRAM.size() == 0) return 0;
        return 12/inRAM.size();
    }



    public void clear() {
        frames = new ArrayList<>();
        inRAM = new ArrayList<>();
    }

    public void newProces(Proces huidigeInstructie, int adres) {

        if(adres == Integer.MAX_VALUE){                             //net opgestart proces, gewoon plaats maken

        }
    }

    public void terminate(Proces weg) {
    }
}
