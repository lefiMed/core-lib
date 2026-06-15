package com.diora.core.animation.tweens.accessors;

import com.badlogic.gdx.physics.box2d.Body;
import com.diora.core.animation.tweens.TweenAccessor;

public class BodyAccessor implements TweenAccessor<Body> {

    public static final int POS_XY = 1;

    @Override
    public int getValues(Body target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case POS_XY:
                returnValues[0] = target.getPosition().x;
                returnValues[1] = target.getPosition().y;
                return 2;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Body target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case POS_XY:
                target.setTransform(newValues[0], newValues[1], 0);
                break;
        }
    }
}
