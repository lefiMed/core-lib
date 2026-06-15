package com.diora.core.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.diora.core.ui.Skin;

public class Asset implements Disposable {

    public AssetManager manger;
    public Skin skin;
    public TextureAtlas sprite, ui;
    private TextureRegion notFoundRegion;

    public Asset(AssetLoader loader) {
        manger = new AssetManager();
        loader.onLoad(manger);
    }

    public void finishLoading(){
        createSkin("ui/atlas/ui.json", getAtlas("ui"));
    }

    public boolean isUpdated(){
        return manger.update();
    }

    public void createSkin (String skinPath, TextureAtlas atlas){
        try {
            skin = new Skin(Gdx.files.internal(skinPath), atlas);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Music getMusic(String ch){
       return manger.get("music/"+ch+".ogg", Music.class);
    }

    public Sound getSound(String ch){
        return manger.get("music/"+ch+".ogg", Sound.class);
    }

    public TextureRegion getRegion(String nameAtlas , String nameRegion){
        return getFromAtlas(getAtlas(nameAtlas), nameRegion);
    }

    public TextureRegion getRegionSprite(String nameRegion){
        if(sprite == null){
            sprite = getAtlas("sprite");
        }
        return getFromAtlas(sprite, nameRegion);
    }

    public TextureRegion getRegionUi(String nameRegion){
        if(ui == null){
            ui = getAtlas("ui");
        }
        return getFromAtlas(ui, nameRegion);
    }

    private TextureRegion getFromAtlas(TextureAtlas atlas , String nameRegion){
        TextureRegion region = atlas.findRegion(nameRegion);
        if(region == null){
            region = getNotFoundRegion();
            Gdx.app.error("Fix it","connot find region : "+nameRegion+" on atlas ");
        }
        return region;
    }

    public TextureAtlas getAtlas(String ch){
        return manger.get("ui/atlas/"+ch+".atlas", TextureAtlas.class);
    }

    public FileHandle getFile(String ch){
        return manger.get(ch, FileHandle.class);
    }

    public TextureRegion getNotFoundRegion(){
        if(notFoundRegion == null){
            notFoundRegion = new TextureRegion(new Texture("ui/texture/notFound.png"));
        }
        return notFoundRegion;
    }

    @Override
    public void dispose() {
        manger.dispose();
        if(skin!=null)
            skin.dispose();
    }
}
