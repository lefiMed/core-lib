package com.diora.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.diora.core.Game;

public class Skin extends com.badlogic.gdx.scenes.scene2d.ui.Skin {

    Drawable missedDrawable;
    String nameMissedDrawable;

    public Skin(FileHandle skinFile) {
        super(skinFile);
    }

    public Skin(FileHandle internal, TextureAtlas atlas) {
        super(internal, atlas);
    }

    @Override
    public void load(FileHandle skinFile) {
        missedDrawable = new BaseDrawable();
        nameMissedDrawable= "missedDrawable";
        super.load(skinFile);
    }

    @Override
    public Drawable getDrawable(String name) {
        Drawable drawable = null;
        try {
            drawable = super.getDrawable(name);
        }catch (Exception e) {
           // e.printStackTrace();
        }


        if(drawable == null){
            Gdx.app.error(name, "missed drawable");
            drawable = missedDrawable;
        }
        return drawable;
    }

    @Override
    public TextureRegion getRegion(String name) {
        TextureRegion region = null;
        try {
            region = super.getRegion(name);
        }catch (Exception e){
            region = Game.GAME.asset.getNotFoundRegion();
        }
        return region;
    }
}
