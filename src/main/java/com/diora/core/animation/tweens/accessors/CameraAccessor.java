package com.diora.core.animation.tweens.accessors;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.diora.core.animation.tweens.TweenAccessor;

public class CameraAccessor implements TweenAccessor<OrthographicCamera> {

    public static final int ZOOM = 1;
    public static final int POS_X = 2;
    public static final int POS_Y = 3;

    @Override
    public int getValues(OrthographicCamera target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ZOOM:
                returnValues[0] = target.zoom;
                return 1;
            case POS_X:
                returnValues[0] = target.position.x;
                return 1;
            case POS_Y:
                returnValues[0] = target.position.y;
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(OrthographicCamera target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ZOOM:
                target.zoom = newValues[0];
                target.update();
                break;
            case POS_X:
                target.position.x = newValues[0];
                target.update();
                break;
            case POS_Y:
                target.position.y = newValues[0];
                target.update();
                break;
        }
    }
}
