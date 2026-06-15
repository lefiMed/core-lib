package com.diora.core.animation.transitions.type;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;

public abstract class AnimTransition {

    private float duration,time;
    private boolean isComplete;
    private final TransitionListener listener;
    public interface TransitionListener {
        void onStart();
        void onFinished();
    }
    protected Interpolation interpolation;

    public AnimTransition(Interpolation interpolation, float duration, TransitionListener listener) {
        this.listener = listener;
        this.duration = duration;
        this.interpolation = interpolation;
    }

    public AnimTransition(float duration, TransitionListener listener) {
        this(null, duration, listener);
    }

    public void onStart(){
        if(listener!=null) listener.onStart();
    }

    public void onFinished(){
        if(listener!=null) listener.onFinished();
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    protected void drawTexture(Batch batch, Texture t){
        batch.draw(t, 0, 0, 0, 0, t.getWidth(), t.getHeight(), 1, 1, 0, 0,
                0, t.getWidth(), t.getHeight(), false, true);
    }

    public void render(Batch batch, Texture current, Texture next, float dt){
        float percent = time / duration;
        if(!isComplete){
            draw(batch, current, next, percent);
        }
        time += dt;
        if(time>duration) setComplete(true);
    }

    public abstract void draw(Batch batch, Texture current, Texture next, float percent);
}
