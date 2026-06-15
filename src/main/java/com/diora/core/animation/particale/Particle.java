package com.diora.core.animation.particale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Particle {

    private ParticleEffect particleEffect;
    private boolean isComplete;
    private Viewport viewport;

    public Particle(String name, float x, float y, Viewport viewport) {
        this.viewport = viewport;
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("ui/particle/" + name), Gdx.files.internal("ui/particle/"));
        particleEffect.allowCompletion();
        particleEffect.setPosition(x, y);
    }

    public boolean isComplete(){
        return isComplete;
    }

    public void start() {
        particleEffect.start();
        isComplete = false;
    }

    public void stop(){
        isComplete = true;
    }

    public void update(float dt) {
        if(!isComplete) {
            particleEffect.update(dt);
            isComplete = particleEffect.isComplete();
        }
    }

    //TODO check this at end
    public void draw(Batch batch) {
        if(!isComplete) {
            viewport.apply(true);
            batch.setProjectionMatrix(viewport.getCamera().combined);
            batch.begin();
            particleEffect.draw(batch);
            batch.end();
            // reset old glview and projection
        }
    }

    public void dispose() {
        particleEffect.dispose();
    }
}
