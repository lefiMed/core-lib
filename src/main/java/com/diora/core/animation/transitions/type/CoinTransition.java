package com.diora.core.animation.transitions.type;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;

public class CoinTransition extends AnimTransition {

    private Side side;
    private float ox, oy, angle, rotation;
    private boolean isFade;

    public enum Side {
        TOP_LEFT, TOP_RIGHT, BUTTOM_LEFT, BUTTOM_RIGHT
    }

    public CoinTransition(Side side, boolean isFade, Interpolation interpolation, float duration, TransitionListener listener) {
        super(interpolation, duration, listener);
        this.side = side;
        this.isFade = isFade;
        rotation = 1;
    }

    @Override
    public void draw(Batch batch, Texture current, Texture next, float percent) {
        if (interpolation != null) rotation = interpolation.apply(percent);
        batch.begin();
        if(isFade) batch.setColor(1, 1, 1, percent);
        super.drawTexture(batch, next);
        if(isFade) batch.setColor(1, 1, 1, 1.0f - percent);
        this.drawTexture(batch, current);
        batch.end();
    }

    @Override
    protected void drawTexture(Batch batch, Texture t) {
        switch (side) {
            case TOP_LEFT: ox = 0; oy = t.getHeight(); angle = 90.0f;break;
            case BUTTOM_LEFT: ox = 0; oy = 0; angle = 90.0f;break;
            case TOP_RIGHT: ox = t.getWidth(); oy = t.getHeight(); angle = -90.0f;break;
            case BUTTOM_RIGHT: ox = t.getWidth(); oy = 0; angle = -90.0f;break;
        }
        batch.draw(t, 0, 0, ox, oy, t.getWidth(), t.getHeight(),
                1, 1, rotation * angle, 0,
                0, t.getWidth(), t.getHeight(), false, true);

    }
}