package com.diora.core.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;

import com.diora.core.Game;

public class TouchFixture {

    private Vector2 point ;
    private QueryCallback callback;
    private World world;
    private FixtureListener fixtureListener;
    private final float amount = 10.0f / Game.GAME.ppm;

    public TouchFixture(World world) {
        this.world = world;
        point = new Vector2();
        //callback = this::doThis;
        callback = new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                return doThis(fixture);
            }
/*
            @Override
            public boolean reportParticle(ParticleSystem system, int index) {
                return false;
            }

            @Override
            public boolean shouldQueryParticleSystem(ParticleSystem system) {
                return false;
            }

 */
        };
        fixtureListener = (bit, object) -> {
            Gdx.app.log("Fixture touched"+bit,""+object);
            return false;
        };
    }

    public void sendQuery(float x, float y){
        point.set(x,y);
        world.QueryAABB(callback, point.x -amount, point.y -amount, point.x +amount, point.y +amount);
    }

    private boolean doThis (Fixture fixture){
        if(fixture.testPoint(point)){
            return fixtureListener.onFixtureTouched(fixture.getFilterData().categoryBits, fixture.getUserData());
        }else
            return true;
    }

    public void setFixtureListener(FixtureListener fixtureListener) {
        this.fixtureListener = fixtureListener;
    }

    public interface FixtureListener{

        /**
         switch(bit)
         case CategoryBits : object.cast(SpriteObject).onTouched; return false; **/
        boolean onFixtureTouched(short bit, Object object);
    }
}

