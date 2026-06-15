package com.diora.core.tool;

public class Time {
    protected long start;

    public Time() {
        this.start = System.currentTimeMillis();
    }

    public void start(){
        start = System.currentTimeMillis();
    }

    public void of(String tag){
        System.out.println("Time of "+tag + " : "+ ((System.currentTimeMillis() - start)/1000.0f));
        start();
    }

    public String get(String tag){
        return "Time of "+tag + " : "+ ((System.currentTimeMillis() - start)/1000.0f);
    }
}
