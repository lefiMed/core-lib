package com.diora.core.animation.transitions.type;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;

public class AlphaTransition extends AnimTransition {

    boolean isVerso;
    public AlphaTransition(boolean isVerso, float duration, TransitionListener listener) {
        super(duration, listener);
        this.isVerso = isVerso;
    }

    @Override
    public void draw(Batch batch, Texture current, Texture next, float alpha) {
        alpha = Interpolation.fade.apply(alpha);
        batch.begin();
        batch.setColor(1, 1, 1, isVerso ? 1 - alpha : 1);
        drawTexture(batch,current);
        batch.setColor(1, 1, 1,alpha);
        drawTexture(batch,next);
        batch.end();
    }
}
