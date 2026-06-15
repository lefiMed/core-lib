package com.diora.core.graphgl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;

public class FullScreen {

    protected Batch batch;
    public int screenWidth, screenHeight;
    protected final Matrix4 combined = new Matrix4();

    public FullScreen(Batch batch) {
        this.batch = batch;
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        combined.setToOrtho2D(0,0, screenWidth, screenHeight);
    }

    public FullScreen() {
    }

    public void draw(float dt){
        //HdpiUtils.glViewport(0, 0, screenWidth, screenHeight);
        Gdx.gl20.glViewport(0, 0, screenWidth, screenHeight);
        batch.setProjectionMatrix(combined);
    }

    public void resize(int width, int height){
        screenWidth = width;
        screenHeight = height;
        combined.setToOrtho2D(0,0, screenWidth, screenHeight);
//        HdpiUtils.glViewport(0, 0, screenWidth, screenHeight);
//        batch.setProjectionMatrix(combined);
    }

}
