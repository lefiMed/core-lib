package com.diora.core.animation.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.Disposable;
import com.diora.core.animation.transitions.type.AnimTransition;
import com.diora.core.graphgl.FullScreen;
import com.diora.core.view.screens.GameScreen;

import static com.badlogic.gdx.Gdx.gl;

public class Transition extends FullScreen implements Disposable, Screen{

    private AnimTransition transition;
    private FrameBuffer currentFbo;
    private FrameBuffer nextFbo;
    private Screen screen;
    public GameScreen nextScreen;

    private boolean isRunning,isPaused;

    public Transition(Batch batch, Screen screen, GameScreen nextScreen, AnimTransition transition) {
        super(batch);
        this.nextScreen = nextScreen;
        this.screen = screen;
        this.isRunning = true;
        this.transition = transition;
        //pause the screens /1
        if(screen != null) screen.pause();
        if(nextScreen != null) nextScreen.pause();
        //life cycle of transtiion
        show();
        resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);
        transition.onStart();
    }

    @Override
    public void render(float delta) {
        if(!isPaused){
            if (isRunning && transition.isComplete()) {
                transition.onFinished();
                isRunning = false;
                dispose();
            } else {
                currentFbo.begin();
                this.screen.render(delta);
                currentFbo.end();
                nextFbo.begin();
                this.nextScreen.render(delta);
                nextFbo.end();

                gl.glClearColor(0.5f,0.5f,0.5f,0.5f);
                gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                draw(delta);
                transition.render(batch, currentFbo.getColorBufferTexture(), nextFbo.getColorBufferTexture(),delta);
            }
        }
    }

    @Override
    public void hide() {
    }

    public void resize(int width, int height) {
        super.resize(width, height);
        // Resize 2
        if (nextScreen != null) nextScreen.resize(width, height);
        // resize FBOs
        if(currentFbo != null) currentFbo.dispose();
        if(nextFbo != null) nextFbo.dispose();
        currentFbo = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        nextFbo = new FrameBuffer(Pixmap.Format.RGBA8888,  width, height, false);
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    @Override
    public void dispose() {
        if(screen != null) screen.hide();
        if(nextScreen != null) nextScreen.show();
        if(nextScreen != null) nextScreen.resume();

        nextScreen.game.setScreen(nextScreen);

        if(currentFbo != null) currentFbo.dispose();
        if(nextFbo != null) nextFbo.dispose();
        if(screen != null) screen.dispose();
    }

}
