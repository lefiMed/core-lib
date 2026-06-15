package com.diora.core.view.fragment;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Disposable;
import com.diora.core.stage.StageCreator;
import com.diora.core.view.screens.GameScreen;

public abstract class Fragment implements Disposable{

    protected GameScreen gameScreen;
    public StageCreator sc;
    protected TiledMap map;
    public Group group;
   // private ButtonImage background;
   public Fragment(GameScreen gameScreen){

   }

   public Fragment(GameScreen gameScreen, String name){
       this(gameScreen, name, false);
   }
    public Fragment(GameScreen gameScreen, String name,boolean isFullScreen){
        this.gameScreen = gameScreen;
        this.sc = gameScreen.sc;
        //TOdo add frag to collect to dispos all map
        map = new TmxMapLoader().load("tmx/fragment/"+name+".tmx");
        group = new Group();
        group.setName(name);
        group.setTransform(false);
        if(!isFullScreen) {
            sc.bound(group, map);
            sc.stage.addActor(group);
        }
        else {
            sc.screenStage.setBounds(group, sc.toRectangle(map));
            sc.screenStage.addActor(group);
        }
        sc.mapToGroup(map, group);
        /*
        group.setScale(sc.screenStage.getWidth() < sc.screenStage.getHeight()?
                sc.screenStage.getWidth()/sc.stage.getWidth():
                sc.screenStage.getHeight()/sc.stage.getHeight());
                */
    }

    protected  <T> T get(Class<T> type, String name){
        return sc.getIn(type,name,group);
    }

    protected void addListener(String name,Runnable runnable){
        sc.addListenerIn(group, name, runnable);
    }

    @Override
    public void dispose() {
        map.dispose();
        group.clear();
        group.remove();
    }
}
