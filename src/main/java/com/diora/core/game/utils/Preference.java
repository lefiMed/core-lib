package com.diora.core.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Preference {

    public Preferences ps;

    public Preference(String namepref) {
        ps = Gdx.app.getPreferences(namepref);
    }

    public void save() {
        ps.flush();
    }

    public  boolean isMusicAllow(){
        return ps.getBoolean("music");
    }

    public  boolean isSoundAllow(){
        return ps.getBoolean("sound");
    }

    public void updateMusic(boolean b){
        ps.putBoolean("music", b);
        ps.flush();
    }

    public void updateSound(boolean b){
        ps.putBoolean("sound", b);
        ps.flush();
    }

    public String getName(){
        return  ps.getString("my_name","");
    }
    public void updateName(String name ){
        ps.putString("my_name",name);
        ps.flush();
    }

    public int getScore(){
        return  ps.getInteger("my_score",0);
    }
    public void update_Score(int newScore){
        int score = newScore + getScore();
        ps.putInteger("my_score",score);
        ps.flush();
    }

    public String getUrl(){
        return  ps.getString("my_url");
    }
    public void updateUrl(String newUrl ){
        ps.putString("my_url",newUrl);
        ps.flush();
    }

    public void updatePageLevel(int pageLevel){
        ps.putInteger("page_lvl",pageLevel);
        ps.flush();
    }
    public void updatePageLevel(int pageLevel, String folder) {
        this.ps.putInteger("page_lvl_"+folder, pageLevel);
        this.ps.flush();
    }

    public int getPageLevel(){
        return ps.getInteger("page_lvl",1);
    }
    public int getPageLevel(String folder) {
        return this.ps.getInteger("page_lvl_"+folder, 1);
    }

    public void updateLevel(int level){
        if(level>getLevel()) {
            ps.putInteger("level", level);
            ps.flush();
        }
    }
    public void updateLevel(int level, String folder) {
        if (level > this.getLevel(folder)) {
            this.ps.putInteger("level_"+folder, level);
            this.ps.flush();
        }
    }

    public int getLevel(){
        return ps.getInteger("level",1);
    }
    public int getLevel(String folder) {
        return this.ps.getInteger("level_"+folder, 1);
    }

}
