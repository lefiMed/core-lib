package com.diora.core.animation.transitions.type;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;

import static com.badlogic.gdx.Gdx.gl;

public class ColorTransition extends AnimTransition {

    private final Color color;

    public ColorTransition(Color color, Interpolation interpolation, float duration, TransitionListener listener) {
        super(interpolation, duration, listener);
        this.color = color;
    }

    @Override
    public void draw(Batch batch, Texture current, Texture next, float alpha) {
        if (interpolation != null) alpha = interpolation.apply(alpha);
        gl.glClearColor(color.r,color.g,color.b,color.a);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        alpha = Interpolation.fade.apply(alpha);
        batch.begin();
        alpha *= 2;
        if(alpha < 1){
            batch.setColor(1, 1, 1,1 - alpha);
            drawTexture(batch,current);
        }else  {
            batch.setColor(1, 1, 1,Math.abs(1 - alpha));
            drawTexture(batch,next);
        }
        batch.end();
    }
}