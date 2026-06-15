package com.diora.core.factory.def;

import com.badlogic.gdx.physics.box2d.Body;
import com.diora.core.world.RectWorld;

public class DefSprite {

    private static DefSprite defSprite = new DefSprite();
    public String name;
    public RectWorld bounds;
    public Body body;

    public DefSprite setName(String name) {
        this.name = name;
        return this;
    }

    public static DefSprite obtain(){
        return defSprite;
    }

    public void free(){
        name = null;
        body = null;
        bounds = null;
    }
}
