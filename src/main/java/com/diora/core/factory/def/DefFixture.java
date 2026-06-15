package com.diora.core.factory.def;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Pool;
import com.diora.core.factory.B2Event;
import com.diora.core.factory.map.ShapeObject;

import java.io.Serializable;

import static com.diora.core.Game.GAME;

public class DefFixture implements Pool.Poolable, Serializable {

    private static final long serialVersionUID = 1L;
    private static FixtureDef fDef = new FixtureDef();
    public transient Shape shape;
    public ShapeObject shapeObject;
    public float friction;
    public float restitution;
    public float density;
    public boolean isSensor;
    public short categoryBits;
    public short maskBits;
    public short groupIndex;

    public DefFixture() {
        this.shapeObject = null;
        this.friction = 0.2f;
        this.restitution = 0;
        this.density = 0;
        this.isSensor = false;
        this.categoryBits = 0x0001;
        this.maskBits = -1;
        this.groupIndex = 0;
    }

    @Override
    public void reset() {
        shapeObject = null;
        friction = 0.2f;
        restitution = 0;
        density = 0;
        isSensor = false;
        categoryBits = 0x0001;
        maskBits = -1;
        groupIndex = 0;
    }

    public FixtureDef def(){
        fDef.shape = shape;
        fDef.friction = friction;
        fDef.restitution = restitution;
        fDef.density = density;
        fDef.isSensor = isSensor;
        fDef.filter.categoryBits = categoryBits;
        fDef.filter.maskBits = maskBits;
        fDef.filter.groupIndex = groupIndex;
        return fDef;
    }

    public void create(B2Event b2Event){
        this.def();
        shapeObject.setCallBack(shape -> {
            fDef.shape = shape;
            b2Event.OnFixtureDef(fDef);
        }).toShape();
    }

    public void create(float scale ,B2Event b2Event){
        this.def();
        shapeObject.setCallBack(shape -> {
            fDef.shape = shape;
            b2Event.OnFixtureDef(fDef);
        }).toShape(scale);
    }

    public static DefFixture obtain(){
        return GAME.worldFactory.defFixturePool.obtain();
    }

    public void free(){
        GAME.worldFactory.defFixturePool.free(this);
    }
}