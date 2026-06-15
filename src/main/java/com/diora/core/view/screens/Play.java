package com.diora.core.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.diora.core.Game;
import com.diora.core.animation.tweens.Tween;
import com.diora.core.animation.tweens.accessors.BodyAccessor;
import com.diora.core.animation.tweens.accessors.SpriteAccessor;
import com.diora.core.mode.GameMode;
import com.diora.core.sprite.SpriteManager;
import com.diora.core.sprite.SpriteObject;
import com.diora.core.stage.StageCreator;
import com.diora.core.utils.Global;
import com.diora.core.view.scenes.Scene;
import com.diora.core.world.WorldCreator;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.gl;

public abstract class Play extends GameScreen {

    public World world;
    public WorldCreator worldCreator;
    public SpriteManager spriteManager;
    protected Box2DDebugRenderer b2dr;
    private InputProcessor screenInputs;
    //public Scene scene;
    public ArrayList<Scene> scenes;
    public int level;
    public TiledMap map, uiMap;
    public GameMode gameMode;
    public OrthographicCamera cam;
    public Vector3 point;
    public Stage stage;

    public boolean isPaused,isShow;

    static{
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Tween.registerAccessor(Body.class, new BodyAccessor());
    }

    public Play(Game game, GameMode gameMode) {
        super(game);
        this.gameMode = gameMode;
        this.level = gameMode.level;
        this.map = new TmxMapLoader().load(gameMode.pathTmx);
        this.uiMap = new TmxMapLoader().load("tmx/ui.tmx");
        this.sc = new StageCreator(game, map, game.ppm);
        sc.initMapRender(game.ppm);
        spriteManager = new SpriteManager(this);
        world = new World(new Vector2(0, -9.8f), true);
        worldCreator = new WorldCreator(this, map);
        updatefixtureListener();
        game.android.action.lightOn();
        point = new Vector3();
        scenes = new ArrayList<>();
        stage = sc.stage;
        cam = ((OrthographicCamera) stage.getCamera());
    }

    public abstract void intiMusic();

    /** world contact and fixture touch listener **/
    public abstract void updatefixtureListener();

    public abstract Class<? extends SpriteObject> layerToSprite(String layerName);

    public abstract void gameOver();

    public abstract void gameWin();

    public void handleInputs(){
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                point.set(x, y, 0);
                Global.log("stage play point: "+point.toString());
                worldCreator.touchFixture.sendQuery(x, y);
                return false;
            }
        });
    }

    public void setB2dr(boolean drawDebugB2) {
        b2dr = drawDebugB2 ? new Box2DDebugRenderer() : null;
    }

    protected void updateInputProcessor(){
        if(isShow){
            InputMultiplexer multiplexer = new InputMultiplexer();
            multiplexer.addProcessor(sc.getInput());
            for (Scene scene : scenes) {
                multiplexer.addProcessor(scene.sc.getInput());
            }
            Gdx.input.setInputProcessor(multiplexer);
        }
    }

    @Override
    public void show() {
        Gdx.app.log("","show play");
        isShow = true;
        updateInputProcessor();
        for (Scene scene : scenes) {
            scene.show();
        }
    }

    @Override
    public void update(float dt) {
        sc.update(dt);
        for (Scene scene : scenes) {
            scene.update(dt);
        }
        world.step(0.02f, 6, 2);
        spriteManager.update(dt);
        //sc.camera.updatePrefs();
    }

    @Override
    public void render(float delta) {
        if (!isPaused) {
            update(delta);
        } else {
            sc.update(delta);
        }
        gl.glClearColor(0.5f,0.5f,0.5f,0.5f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sc.drawBack(delta);
        sc.drawRender();

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        spriteManager.draw(game.batch);
        game.batch.end();
        if(b2dr!=null)
            b2dr.render(world, cam.combined);

        sc.drawStage();
        for (Scene scene : scenes) {
            scene.draw(delta);
        }
       // if(!isPaused) Gdx.graphics.requestRendering();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        for (Scene scene : scenes) {
            scene.resize(width, height);
        }
    }

    @Override
    public void pause() {
        isPaused = true;
        Gdx.app.log("","pause play");
    }

    @Override
    public void resume() {
        isPaused = false;
        Gdx.app.log("","resume play");
    }

    @Override
    public void dispose() {
        super.dispose();
        if(b2dr!=null)
            b2dr.dispose();
        for (Scene scene : scenes) {
            scene.dispose();
        }
        spriteManager.dispose();
        world.dispose();
        if(uiMap!=null)
            uiMap.dispose();
        //TODO replace on hide
        game.android.action.lightOff();
    }
}
