package com.diora.core.sprite;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.diora.core.utils.Global;
import com.diora.core.view.screens.Play;

public class SpriteManager implements Disposable{

    private Array<SpriteObject> sprites = new Array<>();
    private Play play;
    private boolean isChanged;
    private final int maxClean = 1;
    private int countClean = 0;

    public SpriteManager(Play play) {
        this.play = play;
    }

    public void add(Array<SpriteObject> tiles){
        sprites.addAll(tiles);
    }

    public void add(SpriteObject tile){
        sprites.add(tile);
    }

    public int getAllSprites(){
        return sprites.size;
    }

    public void setChanged(boolean changed) {
        countClean++;
        if(isChanged == changed) return;
        isChanged = changed;
    }

    public void cleanSprite(){
       // Global.log("clean semand / countClean : "+countClean);
        isChanged = false;
        if(countClean<maxClean) return;
        Array<SpriteObject> tmp = new Array<>(sprites.size-maxClean);
        for (int i = 0; i < sprites.size; i++) {
            SpriteObject spo = sprites.get(i);
            if(spo.isDestroyed()){
                spo.destroyBody();
                spo.onDestroy();
                sprites.set(i, null);
            }else {
                tmp.add(spo);
            }
        }
        sprites = tmp;

       // Global.log("clean sprite / size : "+sprites.size);
        countClean = 0;
    }

    public void update(float dt){
        if(isChanged) cleanSprite();
        for (int i = 0; i < sprites.size; i++) {
            sprites.get(i).update(dt);
        }
    }

    public void draw(Batch batch ){
        batch.setColor(1,1,1,1);
        for (int i = 0; i < sprites.size; i++) {
            sprites.get(i).render(batch);
        }
    }

    public void moveAtEnd(SpriteObject sprite){
        int begin = sprites.indexOf(sprite,true);
        int end = sprites.size-1;
        sprites.swap(begin, end);
    }

    @Override
    public void dispose() {
        for (int i = 0; i < sprites.size; i++) {
            sprites.get(i).onDestroy();
        }
        sprites.clear();
        sprites = null;
    }
  }




