package model;

public class Page {

    private boolean written;

    private int lastAccessTime;

    private int framenr;

    public Page(){
        written = false;
        lastAccessTime = Integer.MAX_VALUE;
        framenr = Integer.MAX_VALUE;
    }
}
