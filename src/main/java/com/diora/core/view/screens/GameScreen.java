package com.diora.core.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.diora.core.Game;
import com.diora.core.animation.transitions.type.AlphaTransition;
import com.diora.core.animation.transitions.type.AnimTransition;
import com.diora.core.stage.StageCreator;

import static com.badlogic.gdx.Gdx.gl;


public abstract class GameScreen implements DioraScreen {

    public StageCreator sc;
    public Game game;
    public boolean dispose = true;

    public GameScreen(Game game, String name) {
        this.game = game;
        this.sc = new StageCreator(game,"tmx/screens/"+name);
    }

    public GameScreen(Game game, StageCreator sc) {
        this.game = game;
        this.sc = sc;
    }

    /**
     * new AlphaTransition(true, .8f, null);
     * new ColorTransition(Color.BLACK, Interpolation.pow3, 0.4f, null);
     * new RotateTransition(370 , RotateTransition.Scaling.IN, Interpolation.smooth,.8f,null);
     * new CoinTransition(CoinTransition.Side.TOP_RIGHT,true, Interpolation.sineIn,0.8f,null);
     * new SlideTransition(randomEnum(SlideTransition.Direction.class), SlideTransition.Slide.IN,Interpolation.smooth,0.8f,null);
     **/
    public AnimTransition getTransition(){
        return new AlphaTransition(true, .8f, null);
    }

    public GameScreen(Game game) {
        this.game = game;
    }

    public void setSc(StageCreator sc) {
        this.sc = sc;
    }

    @Override
    public void show() {
        Gdx.app.log("Screen","show");
        sc.onShow();
    }

    public void update ( float delta){
        sc.update(delta);
    }

    @Override
    public void captureRender(float dt) {

    }

    @Override
    public void render(float delta) {
        update(delta);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sc.draw(delta);
    }

    @Override
    public void resize(int width, int height) {
       // Gdx.app.log("Screen","resize"+width+" "+height);
        sc.resize(width, height);
    }

    @Override
    public void pause() {
        Gdx.app.log("Screen","pause");
        //sc.tweenManager.pause();
    }

    @Override
    public void resume() {
        Gdx.app.log("Screen","resume");
       // sc.tweenManager.resume();
    }

    @Override
    public void hide() {
        Gdx.app.log("Screen","hide");
    }

    @Override
    public void dispose() {
        Gdx.app.log("Screen","dispose");
        sc.dispose();
    }
}