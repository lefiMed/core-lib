package com.diora.core.stage.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

public class UiUtils implements Disposable {

    public Color color;
    public BitmapFont bitmapFont ;
    public Texture texture;
    public Drawable drawable;

    public UiUtils() {
        color = new Color(1.0f,1.0f,1.0f,1.0f);
        bitmapFont = new BitmapFont();
        texture = new Texture(Gdx.files.internal("ui/texture/notFound.png"));
        drawable = new TextureRegionDrawable(texture);
    }


    @Override
    public void dispose() {
        bitmapFont.dispose();
        color = null;
        drawable = null;
        texture.dispose();
    }
}
