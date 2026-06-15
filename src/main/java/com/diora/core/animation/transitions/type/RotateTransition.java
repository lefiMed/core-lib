package com.diora.core.animation.transitions.type;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;

public class RotateTransition extends AnimTransition {

    private float angle, scalefactor, rotation;
    private Scaling scaling;

    public enum Scaling {
        IN, OUT
    }

    public RotateTransition(float angle, Scaling scaling, Interpolation interpolation, float duration, TransitionListener listener) {
        super(interpolation, duration, listener);
        this.interpolation = interpolation;
        this.angle = angle;
        this.scaling = scaling;
        rotation = 1;
    }

    @Override
    public void draw(Batch batch, Texture current, Texture next, float percent) {
        if (interpolation != null) rotation = interpolation.apply(percent);
        batch.begin();
        switch (scaling) {
            case IN:
                scalefactor = percent;
                batch.setColor(1, 1, 1,1 - percent/2);
                super.drawTexture(batch, current);
                batch.setColor(1, 1, 1,percent);
                this.drawTexture(batch, next);
                break;
            case OUT:
                scalefactor = 1.0f - percent;
                batch.setColor(1, 1, 1,percent);
                super.drawTexture(batch, next);
                batch.setColor(1, 1, 1,1 - percent/2);
                this.drawTexture(batch, current);
                break;
        }
        batch.end();
    }

    @Override
    protected void drawTexture(Batch batch, Texture t) {
        batch.draw(t, 0, 0,t.getWidth()/2, t.getHeight()/2, t.getWidth(), t.getHeight(),
                scalefactor, scalefactor, rotation*angle, 0,
                0, t.getWidth(), t.getHeight(), false, true);
    }
}
