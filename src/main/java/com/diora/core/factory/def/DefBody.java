package com.diora.core.factory.def;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Pool;

import java.io.Serializable;

import static com.diora.core.Game.GAME;

public class DefBody implements Pool.Poolable, Serializable {

    private static final long serialVersionUID = 1L;
    private static final BodyDef bodyDef = new BodyDef();
    public BodyDef.BodyType type;
    public final Vector2 position;
    public float angle;
    public final Vector2 linearVelocity;
    public float angularVelocity;
    public float linearDamping;
    public float angularDamping;
    public boolean allowSleep;
    public boolean awake;
    public boolean fixedRotation;
    public boolean bullet;
    public boolean active;
    public float gravityScale;

    public DefBody() {
        this.type = BodyDef.BodyType.StaticBody;
        this.position = new Vector2();
        this.linearVelocity = new Vector2();
        this.allowSleep = true;
        this.awake = true;
        this.active = true;
        this.gravityScale = 1.0f;
    }

    @Override
    public void reset() {
        type = BodyDef.BodyType.StaticBody;
        position.set(0,0);
        angle = 0;
        linearVelocity.set(0,0);
        angularVelocity = 0;
        linearDamping = 0;
        angularDamping = 0;
        allowSleep = true;
        awake = true;
        fixedRotation = false;
        bullet = false;
        active = true;
        gravityScale = 1.0f;
    }

    public BodyDef def(){
        bodyDef.type = this.type;
        bodyDef.position.set(this.position);
        bodyDef.angle = this.angle;
        bodyDef.linearVelocity.set(this.linearVelocity);
        bodyDef.angularVelocity = this.angularVelocity;
        bodyDef.linearDamping = this.angularVelocity;
        bodyDef.angularDamping = this.angularVelocity;
        bodyDef.allowSleep = this.allowSleep;
        bodyDef.awake = this.awake;
        bodyDef.fixedRotation = this.fixedRotation;
        bodyDef.bullet = this.bullet;
        bodyDef.active = this.active;
        bodyDef.gravityScale = this.gravityScale;
        return bodyDef;
    }

    public static DefBody obtain(){
        return GAME.worldFactory.defBodyPool.obtain();
    }

    public void free(){
        GAME.worldFactory.defBodyPool.free(this);
    }
}