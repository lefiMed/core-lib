package com.diora.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ExtendBackGround {

    Texture texture;
    Viewport viewport;
    Batch batch;

    public ExtendBackGround(Batch batch,Texture texture) {
        this.batch = batch;
        this.texture = texture;
        viewport = new FillViewport(1440, 2400);
        viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
    }

    public void draw(float dt){
        viewport.apply(true);
        batch.setColor(Color.WHITE);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(texture,0,0);
        batch.end();
    }

    public void resize(int width, int height){
        viewport.update(width, height,true);
    }

    public void dispose(){
        texture.dispose();
    }
}
