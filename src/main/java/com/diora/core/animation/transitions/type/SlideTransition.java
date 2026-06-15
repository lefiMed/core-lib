package com.diora.core.animation.transitions.type;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;

public class SlideTransition extends AnimTransition {

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }
    public enum Slide {
        IN, OUT, TOW
    }

    private SlideTransition.Direction direction;
    private SlideTransition.Slide slide;
    private float x0,y0,x1,y1;

    public SlideTransition(Direction direction, Slide slide, Interpolation interpolation, float duration, TransitionListener listener) {
        super(interpolation, duration, listener);
        this.direction = direction;
        this.slide = slide;
        x0 = x1 = y0 = y1 = 0;
    }

    private void setPos(Texture t, float p){
        float width = t.getWidth();
        float height = t.getHeight();
        switch (direction) {
            case LEFT:
                x0 = -width * p;
                x1 = width - width * p;
                //if (!slideOut) x += width;
                break;
            case RIGHT:
                x0 = width * p;
                x1 = -width + width * p;
                //if (!slideOut) x -= width;
                break;
            case UP:
                y0 = height * p;
                y1 = -height + height * p;
                //if (!slideOut) y -= height;
                break;
            case DOWN:
                y0 = -height * p;
                y1 = height - height * p;
                //if (!slideOut) y += height;
                break;
        }
    }

    @Override
    public void draw(Batch batch, Texture current, Texture next, float percent) {
        if (interpolation != null) percent = interpolation.apply(percent);
        setPos(current, percent);
        batch.begin();
        switch (slide) {
            case IN:
                super.drawTexture(batch,current);
                this.drawTexture(batch,next,x1, y1);
                break;
            case OUT:
                super.drawTexture(batch,next);
                this.drawTexture(batch,current,x0, y0);
                break;
            case TOW:
                this.drawTexture(batch,current,x0, y0);
                this.drawTexture(batch,next,x1, y1);
                break;
        }
        batch.end();
    }

    private void drawTexture(Batch batch, Texture t,float x, float y) {
        batch.draw(t, x, y, 0, 0, t.getWidth(), t.getHeight(), 1, 1, 0, 0,
                0, t.getWidth(), t.getHeight(), false, true);
    }
}
