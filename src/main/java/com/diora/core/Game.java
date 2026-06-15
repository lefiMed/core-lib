package com.diora.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.diora.core.android.Android;
import com.diora.core.android.GameAction;
import com.diora.core.animation.transitions.type.AnimTransition;
import com.diora.core.factory.WorldFactory;
import com.diora.core.game.utils.Asset;
import com.diora.core.game.utils.AssetLoader;
import com.diora.core.game.utils.Dj;
import com.diora.core.game.utils.Preference;
import com.diora.core.graphgl.shaders.Shaders;
import com.diora.core.view.screens.DioraScreen;
import com.diora.core.view.screens.GameScreen;
import com.diora.core.view.screens.Transition;

public abstract class Game implements ApplicationListener {

    public static Game GAME;
    public static final boolean IS_DEBUG = true;
    public Screen screen;
    public SpriteBatch batch;
    public Asset asset;
    public Preference pref;
    public Dj dj;

    public GameAction gameAction;
    public Android android;
    public boolean isAndroid;
    public float ppm;
    public WorldFactory worldFactory;
    public Shaders shaders;

    public FrameBuffer currentFbo;
    public FrameBuffer nextFbo;

    public void load(AssetLoader loader, GameAction action, String namePref, float ppm) {
        if(GAME!=null)
            return;
        else
            GAME = this;
        this.asset = new Asset(loader);
        this.pref = new Preference(namePref);
        this.dj = new Dj(this);
        this.ppm = ppm;
        this.gameAction = action;
        this.isAndroid = Gdx.app.getType().equals(Application.ApplicationType.Android);
        if(android == null)
            this.android = new Android(this);

        createFramesBuffers(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        shaders = new Shaders(null,null);
        Gdx.app.log("game", "load");
        android.load.load();
    }

    public void createFramesBuffers(int width, int height){
        Gdx.app.log(""+width,""+height);
        if(currentFbo != null) currentFbo.dispose();
        if(nextFbo != null) nextFbo.dispose();
        currentFbo = new FrameBuffer(Pixmap.Format.RGB565, width, height, false);
        nextFbo = new FrameBuffer(Pixmap.Format.RGB565,  width, height, false);
        nextFbo.end();
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        Gdx.app.log("game","create");
    }

    @Override
    public void render () {
        if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        if (screen != null) screen.resize(width, height);
        Gdx.app.log("game","resize");
    }

    @Override
    public void pause() {
        if (screen != null) screen.pause();
        Gdx.app.log("game","pause");
        if(dj!=null) dj.pauseAll();
    }

    @Override
    public void resume() {
        if (screen != null) screen.resume();
        Gdx.app.log("game","resume");
        if(dj!=null) dj.resumeAll();
    }

    @Override
    public void dispose () {
        if(dj!=null) dj.dispose();
        screen.dispose();
        asset.dispose();
        batch.dispose();
        if(currentFbo != null) currentFbo.dispose();
        if(nextFbo != null) nextFbo.dispose();
        Gdx.app.log("","end game");
    }

    public GameScreen getGameScreen(){
        if(this.screen instanceof Transition)
            return ((GameScreen) ((Transition) screen).nextScreen);
        else
            return ((GameScreen) this.screen);
    }

    public void setScreen(GameScreen screen, AnimTransition transition) {
        DioraScreen OldScreen = ((DioraScreen) this.screen);
        this.screen = new Transition(this, OldScreen, screen, transition);
    }

    public void setScreen(GameScreen gameScreen) {
        AnimTransition anim = gameScreen.getTransition();
        if(anim!=null){
            setScreen(gameScreen, anim);
        }else {
            if(this.screen != null){
                this.screen.hide();
                this.screen.dispose();
            }
            this.screen = gameScreen;
            this.screen.show();
            //this.screen.resize();
        }
    }

    public void changeScreen (GameScreen screen) {
        if (this.screen != null) this.screen.hide();
        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }
}
