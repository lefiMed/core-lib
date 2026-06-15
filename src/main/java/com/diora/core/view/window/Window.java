package com.diora.core.view.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.diora.core.animation.tweens.Timeline;
import com.diora.core.animation.tweens.Tween;
import com.diora.core.animation.tweens.TweenCallback;
import com.diora.core.animation.tweens.TweenEquations;
import com.diora.core.animation.tweens.accessors.ActorAccessor;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.actor.ButtonImage;
import com.diora.core.view.screens.GameScreen;

public abstract class Window implements Disposable{

    public StageCreator sc;
    protected GameScreen gameScreen;
    public TiledMap map;
    public Group group;
    private ButtonImage background;

    public Window(GameScreen gameScreen, String name) {
        this(gameScreen, name, false);
    }

    public Window(GameScreen gameScreen,String name, boolean isClosable){
        this.gameScreen = gameScreen;
        this.sc = gameScreen.sc;
        if(sc.screenStage.windows.size == 0){
            sc.lastInputProcessor = Gdx.input.getInputProcessor();
            Gdx.input.setInputProcessor(sc.screenStage);
        }
        sc.screenStage.windows.add(this);

        map = new TmxMapLoader().load("tmx/windows/"+name+".tmx");
        background = new ButtonImage();
        background.setColor(1,1,1,0);
        if(map.getProperties().containsKey("color")){
            background.setPix(map.getProperties().get("color", Color.class));
        }
        background.setBounds(0,0,sc.screenStage.getWidth(),sc.screenStage.getHeight());
        background.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(isClosable)
                    hide();
            }
        });
        group = new Group();
        group.setTransform(true);
        sc.screenStage.addActor(background);
        sc.screenStage.addActor(group);
        sc.bound(group, map);
        sc.mapToGroup(map,group);
        scaleGroupe();

        intValue();
        setupTween();
        show(null);
        Tween.to(background,ActorAccessor.OPACITY,duration).target(1.0f).ease(TweenEquations.easeOutExpo).start(sc.tweenManager);
    }

    public void onShow(){

    }

    public void onHide(){

    }

    private void show(final Runnable runnable){
        showLine.setCallback((type, source) -> {
            if(type == TweenCallback.COMPLETE){
                onShow();
                if(runnable!=null) runnable.run();
                Gdx.app.log("win","show");
            }
        }).start(gameScreen.sc.tweenManager);
    }

    public void hide(){ hide(null); }

    public void hide(final Runnable runnable) {
        background.clear();
        Tween.to(background,ActorAccessor.OPACITY,duration).target(0.0f).ease(TweenEquations.easeOutExpo).start(sc.tweenManager);
        hideLine.setCallback((type, source) -> {
            if(type == TweenCallback.COMPLETE){
                onHide();
                dispose();
                if(runnable!=null) runnable.run();
                Gdx.app.log("win","hide");
            }
        }).start(gameScreen.sc.tweenManager);
    }

    protected  <T> T get(Class<T> type, String name){
        return sc.getIn(type,name,group);
    }

    protected void addListener(String name,Runnable runnable){
        sc.addListenerIn(group, name, runnable);
    }

    public void update(float  dt){

    }

    public void draw(Batch batch){

    }

    private void scaleGroupe(){
        Vector2 winWorldsize = new Vector2(1024.0f, 640.0f);
        if(sc.screenStage.getWidth() < sc.screenStage.getHeight()){
            winWorldsize.set(640.0f, 1024.0f);
        }
        if(sc.screenStage.getWidth() < sc.screenStage.getHeight()){
            group.setScale(sc.screenStage.getWidth() / winWorldsize.x);
        }else {
            group.setScale(sc.screenStage.getHeight() / winWorldsize.y);
        }
    }

    public void resize(){
        background.setBounds(0,0,sc.screenStage.getWidth(),sc.screenStage.getHeight());
        xCenter =(sc.screenStage.getWidth()-group.getWidth())/2.0f;
        yCenter =(sc.screenStage.getHeight()-group.getHeight())/2.0f;
        group.setPosition(xCenter, yCenter);
        scaleGroupe();
        currentScaleX = group.getScaleX();
        currentScaleY = group.getScaleY();
        right = sc.screenStage.getWidth();//x
        top = sc.screenStage.getHeight();//y
    }

    @Override
    public void dispose() {
        map.dispose();
        background.clear();
        background.remove();
        group.clear();
        group.remove();
        sc.screenStage.windows.pop();
        if(sc.screenStage.windows.size == 0){
            Gdx.input.setInputProcessor(sc.lastInputProcessor);
            sc.lastInputProcessor = null;
        }
    }

    protected float xCenter,yCenter;
    protected float top,buttom,left,right;
    protected float currentScaleX;
    protected float currentScaleY;
    protected Timeline showLine,hideLine;
    protected float duration;

    private void intValue(){
        duration = 0.6f;
        showLine = Timeline.createParallel();
        hideLine = Timeline.createParallel();
        xCenter =(sc.screenStage.getWidth()-group.getWidth())/2.0f;
        yCenter =(sc.screenStage.getHeight()-group.getHeight())/2.0f;
        currentScaleX = group.getScaleX();
        currentScaleY = group.getScaleY();
        left = - group.getWidth();//x
        right = sc.screenStage.getWidth();//x
        top = sc.screenStage.getHeight();//y
        buttom = -group.getHeight();//y
    }

    protected void reset(){
        group.setColor(1,1,1,0);
        group.setScale(0, 0);
        group.setPosition(xCenter, yCenter);
    }

    /**alpha
     * group.setColor(1,1,1,0);
     * showLine.push(Tween.to(group, ActorAccessor.OPACITY, duration).target(1));
     * hideLine.push(Tween.to(group, ActorAccessor.OPACITY, duration).target(0));
     * scale
     * group.setScale(currentScaleX, currentScaleY);
     * showLine.push(Tween.to(group, ActorAccessor.SCALE_XY, d).target(currentScaleX,currentScaleY));
     * hideLine.push(Tween.to(group, ActorAccessor.SCALE_XY, d).target(0,0));
     * slide
     * group.setPosition(xCenter, top); // slide
     * showLine.push(Tween.to(group, ActorAccessor.POS_XY, duration).target(xCenter, yCenter));
     * hideLine.push(Tween.to(group, ActorAccessor.POS_XY, duration).target(xCenter, buttom));
     * **/
    protected void setupTween(){
        reset();
        showLine.push(Tween.to(group, ActorAccessor.OPACITY, duration).ease(TweenEquations.easeOutExpo).target(1))
                .push(Tween.to(group, ActorAccessor.SCALE_XY, duration).ease(TweenEquations.easeOutBack).target(currentScaleX,currentScaleY));
                //.push(Tween.to(group, ActorAccessor.POS_XY, duration).target(xCenter, yCenter));
        hideLine.push(Tween.to(group, ActorAccessor.OPACITY, duration).ease(TweenEquations.easeOutExpo).target(0))
                .push(Tween.to(group, ActorAccessor.SCALE_XY, duration).ease(TweenEquations.easeOutExpo).target(0,0));
                //.push(Tween.to(group, ActorAccessor.POS_XY, duration).target(xCenter, buttom));
    }



}


