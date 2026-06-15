package com.diora.core.stage.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.diora.core.stage.StageCreator;

public class CheckBoxImage extends Image {

    private boolean isChecked;
    private Drawable on_up,on_down,off_up,off_down;

    public CheckBoxImage(StageCreator sc, String name) {
        super();
        on_up = sc.game.asset.skin.getDrawable(name+"_on_up");
        on_down = sc.game.asset.skin.getDrawable(name+"_on_down");
        off_up = sc.game.asset.skin.getDrawable(name+"_off_up");
        off_down = sc.game.asset.skin.getDrawable(name+"_off_down");
        isChecked = sc.game.pref.ps.getBoolean(name,true);
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(isChecked)
                    setDrawable(on_down);
                else
                    setDrawable(off_down);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isChecked = !isChecked;
                setValue(isChecked);
                sc.game.pref.ps.putBoolean(name, isChecked);
                sc.game.pref.ps.flush();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                setValue(isChecked);
            }
        });
        setValue(isChecked);
    }

    public void setValue(boolean isChecked){
        this.isChecked = isChecked;
        if(isChecked)
            setDrawable(on_up);
        else
            setDrawable(off_up);
    }

    public boolean isChecked() {
        return isChecked;
    }
}
