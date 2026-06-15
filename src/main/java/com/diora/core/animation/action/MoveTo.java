package com.diora.core.animation.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class MoveTo extends MoveToAction {

    private boolean isStartSet;

    public MoveTo() {
        Gdx.app.log("MoveTo","constract");
    }

    @Override
    protected void begin() {
        if(!isStartSet)
            super.begin();
    }

    @Override
    public void setStartPosition(float x, float y) {
        super.setStartPosition(x, y);
        isStartSet = true;
    }
}
