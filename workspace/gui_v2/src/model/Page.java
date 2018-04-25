package model;

public class Page {

    private boolean written;

    private int lastAccessTime;

    private int framenr;

    private int pid;

    public Page(int pid){
        written = false;
        lastAccessTime = Integer.MAX_VALUE;
        framenr = Integer.MAX_VALUE;
        this.pid = pid;
    }
}
