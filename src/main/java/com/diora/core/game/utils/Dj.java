package com.diora.core.game.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.diora.core.Game;

public class Dj implements Disposable {

    public boolean isMusicAllow ,isSoundAllow;
    public Music music;
    private Game game;
    public Sound clickActor;
    private String mainMusicName;

    public Dj(Game game) {
        this.game = game;
        mainMusicName = "music";
        updatePrefs();
    }

    public void updatePrefs(){
        isMusicAllow = game.pref.isMusicAllow();
        isSoundAllow = game.pref.isSoundAllow();
    }

    public void updateWithPrefs(){
        if(isMusicAllow){
            if(music!=null) {
                music.stop();
            }
            music = getmusic(mainMusicName);
            music.setLooping(true);
            music.play();
        }else {
            if(music!=null){
                music.stop();
            }
        }
        if(isSoundAllow){
            if(clickActor== null){
                clickActor = getSound("click");
            }
        }
    }

    public void setMainMusicName(String mainMusicName) {
        this.mainMusicName = mainMusicName;
        updateWithPrefs();
    }

    public Music setMusic(String ch) {
        this.music = getmusic(ch);
        music.setLooping(true);
        return music;
    }

    public Music getmusic(String ch){
        return game.asset.getMusic(ch);
    }

    public Sound getSound(String ch){
        return game.asset.getSound(ch);
    }

    public void playClickedActorSound(){
        if(isSoundAllow){
            clickActor.play();
        }
    }

    public void playSound(String ch){
        if(isSoundAllow) {
           getSound(ch).play();
        }
    }

    public void playSound(String ch,float pinch){
        if(isSoundAllow) {
            Sound sound = getSound(ch);
            sound.play(1,pinch,-1);
        }
    }

    public void pauseMusic(){
        if (music != null && music.isPlaying())
            music.pause();
    }

    public void playMusic(){
        if (isMusicAllow && music != null){
            music.setLooping(true);
            music.play();
        }
    }

    public void pauseAll(){
        if(music!=null && music.isPlaying()){
            music.pause();
        }
    }

    public void resumeAll(){
        if(music!=null && isMusicAllow){
            music.play();
        }
    }

    @Override
    public void dispose() {
        if(music != null)
            music.dispose();
    }

}
