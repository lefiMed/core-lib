package com.diora.core.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Disposable;
import com.diora.core.Game;
import com.diora.core.animation.transitions.type.AnimTransition;
import com.diora.core.graphgl.FullScreen;

import static com.badlogic.gdx.Gdx.gl;

public class Transition extends FullScreen implements Disposable, Screen{

    private AnimTransition transition;
    private DioraScreen screen;
    public DioraScreen nextScreen;
    private Game game;

    private boolean isRunning,isPaused;

    public Transition(Game game, DioraScreen screen, DioraScreen nextScreen, AnimTransition transition) {
        super(game.batch);
        this.game = game;
        this.nextScreen = nextScreen;
        this.screen = screen;
        this.isRunning = true;
        this.transition = transition;
        if(screen != null) screen.pause();
        if(nextScreen != null) nextScreen.pause();
        show();
        if(screenWidth!=game.nextFbo.getWidth() || screenHeight!=game.nextFbo.getHeight()){
            game.createFramesBuffers(screenWidth,screenHeight);
        }
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
                isRunning = false;
                transition.onFinished();
                dispose();
            } else {
                super.draw(delta);
                this.screen.captureRender(delta);
                game.currentFbo.begin();
                //gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                this.screen.render(delta);
                game.currentFbo.end();
                this.nextScreen.captureRender(delta);
                game.nextFbo.begin();
               // gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                this.nextScreen.render(delta);
                game.nextFbo.end();

                gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
                gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                draw(delta);
                transition.render(batch, game.currentFbo.getColorBufferTexture(), game.nextFbo.getColorBufferTexture(),delta);
            }
        }
    }

    @Override
    public void hide() {
    }

    public void resize(int width, int height) {
        super.resize(width, height);
        if(screen != null) screen.resize(width, height);
        if (nextScreen != null) nextScreen.resize(width, height);
        game.createFramesBuffers(width, height);
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    @Override
    public void dispose() {
        if(screen != null){
            screen.hide();
            if(screen instanceof GameScreen){
                if(((GameScreen) screen).dispose)
                    screen.dispose();
            }else {
                screen.dispose();
            }
        }

        if(nextScreen!=null){
            game.screen = nextScreen;
            nextScreen.resume();
            nextScreen.show();
        }
    }

}
