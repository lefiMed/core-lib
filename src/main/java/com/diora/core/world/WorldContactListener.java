package com.diora.core.world;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
/*
import com.badlogic.gdx.physics.box2d.particle.ParticleBodyContact;
import com.badlogic.gdx.physics.box2d.particle.ParticleContact;
import com.badlogic.gdx.physics.box2d.particle.ParticleSystem;
 */
public class WorldContactListener implements ContactListener {

    protected Fixture fA, fB;
    protected int mask;

    @Override
    public void beginContact(Contact contact) {
        fA = contact.getFixtureA(); fB = contact.getFixtureB();
        mask = fA.getFilterData().categoryBits | fB.getFilterData().categoryBits;
    }

    protected  <T> T obj(Class<T> type){
        //Gdx.app.log("","contact "+type.getSimpleName());
        return type.cast(( type.isInstance(fA.getUserData()) ? fA.getUserData() : fB.getUserData()));
    }

    @Override
    public void endContact(Contact contact) {
        //fA = contact.getFixtureA(); fB = contact.getFixtureB();
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

/*
    @Override
    public void beginParticleBodyContact(ParticleSystem system, ParticleBodyContact contact) {

    }

    @Override
    public void endParticleBodyContact(Fixture fixture, ParticleSystem system, int index) {

    }

    @Override
    public void beginParticleContact(ParticleSystem system, ParticleContact contact) {

    }

    @Override
    public void endParticleContact(ParticleSystem system, int indexA, int indexB) {

    }

 */
}
