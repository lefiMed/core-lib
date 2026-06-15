package com.diora.core.stage.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.diora.core.graphgl.FullScreen;

public class Background extends FullScreen implements Disposable {

    private TextureRegion back;

    public Background(Batch batch, Texture texture){
        super(batch);
        back = new TextureRegion(texture);
        back.setRegion(0,0,screenWidth,screenHeight);
    }

    public Background(Batch batch, TextureRegion region){
        super(batch);
        back = region;
    }

    public Background(Batch batch, Color color){
        super(batch);
        Pixmap pmap = new Pixmap(8,8, Pixmap.Format.RGBA8888);
        pmap.setColor(color);
        pmap.fill();
        back = new TextureRegion(new Texture(pmap));
        pmap.dispose();
    }

    @Override
    public void draw(float dt) {
        super.draw(dt);
        batch.setColor(1,1,1,1);
        batch.begin();
        batch.draw(back,0,0, screenWidth, screenHeight);
        batch.end();
    }

    @Override
    public void dispose() {
    }
}

