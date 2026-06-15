package com.diora.core.view.scenes;

import com.badlogic.gdx.utils.Disposable;
import com.diora.core.stage.StageCreator;
import com.diora.core.view.screens.Play;

public abstract class Scene implements Disposable {

     protected final Play play;
     public StageCreator sc;

    public Scene(String name, final Play screen){
        this.play = screen;
        sc = new StageCreator(play.game,"tmx/scenes/"+name);
    }

    protected void addToScreen(){
        play.scenes.add(this);
    }

    public void show(){

    }

    public void update(float dt){
        sc.update(dt);
    }

    public void draw(float dt){
        sc.draw(dt);
    }

    public void resize(int width, int height){
        sc.resize(width,height);
    }

    @Override
    public void dispose() {
        sc.dispose();
    }

}
