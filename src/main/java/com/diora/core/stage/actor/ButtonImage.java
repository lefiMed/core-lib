package com.diora.core.stage.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonImage extends Image {

    public ButtonImage(Drawable drawable) {
        super(drawable);
    }

    public ButtonImage(Texture texture) {
        super(texture);
    }

    public ButtonImage() {
      super();
    }

    public void setPix(Color color){
        Pixmap pmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pmap.setColor(color);
        pmap.fill();
        setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(pmap))));
        pmap.dispose();
    }

    public void setRegion(TextureRegion region){
        setDrawable(new TextureRegionDrawable(region));
    }

}
