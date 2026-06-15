package com.diora.core.graphgl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

public class Gradient extends FullScreen implements Disposable {

    private boolean isOnnerRender;
    private ShapeRenderer shapeRenderer;
    private Color cDown, cUp;

    public Gradient(Batch batch, ShapeRenderer shapeRenderer, Color cdown, Color cup) {
        super(batch);
        this.cDown = cdown;
        this.cUp = cup;
        this.shapeRenderer = shapeRenderer;
    }

    public Gradient(Batch batch, Color cdown, Color cup) {
        super(batch);
        this.cDown = cdown;
        this.cUp = cup;
        shapeRenderer = new ShapeRenderer();
        isOnnerRender = true;
    }

    @Override
    public void draw(float dt) {
        super.draw(dt);
        shapeRenderer.setProjectionMatrix(combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, screenWidth, screenHeight, cDown, cDown, cUp, cUp);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        if(isOnnerRender)
            shapeRenderer.dispose();
    }
}
