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

    public int framesPP(){
        if (inRAM.size() == 0) return 0;
        return 12/inRAM.size();
    }



    public void clear() {
        frames = new ArrayList<>();
        inRAM = new ArrayList<>();
    }

    public void newProces(Proces p, int adres) {

        if(adres == Integer.MAX_VALUE){                             //net opgestart proces, gewoon plaats maken
            //plaats toewijzen aan nieuw proces
            if (framesPP() == 3){       //RAM vol
                int pid = lruRAM();
            } else {

            }
        }
    }

    private int lruRAM() {
        int last = Integer.MAX_VALUE;
        for(Integer integer: frames);       //vast
        return Integer.MAX_VALUE;
    }

    public void terminate(Proces weg) {
    }
}
