package com.diora.core.animation.tweens.accessors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.diora.core.animation.tweens.TweenAccessor;

public class ActorAccessor implements TweenAccessor<Actor> {

    public static final int POS_XY = 1;
    public static final int CPOS_XY = 2;
    public static final int SCALE_XY = 3;
    public static final int OPACITY = 4;
    public static final int ROTATE = 5;
    public static final int POS_Y = 6;
    public static final int WIDTH = 7;
    public static final int POS_X = 8;
    public static final int HEIGHT = 9;
    public static final int FONT_SCALE_XY = 10;
    public static final int SCALE_X = 11;
    public static final int SCALE_Y = 12;
    public static final int WIDTH_HEIGHT = 13;
    public static final int COLOR = 14;

    @Override
    public int getValues(Actor target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case POS_XY:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2;
            case CPOS_XY:
                returnValues[0] = target.getX() + target.getWidth() / 2;
                returnValues[1] = target.getY() + target.getHeight() / 2;
                return 2;
            case SCALE_XY:
                returnValues[0] = target.getScaleX();
                returnValues[1] = target.getScaleY();
                return 2;
            case SCALE_X:
                returnValues[0] = target.getScaleX();
                return 1;
            case SCALE_Y:
                returnValues[0] = target.getScaleY();
                return 1;
            case OPACITY:
                returnValues[0] = target.getColor().a;
                return 1;
            case ROTATE:
                returnValues[0] = target.getRotation();
                return 1;
            case POS_Y:
                returnValues[0] = target.getY();
                return 1;
            case WIDTH:
                returnValues[0] = target.getWidth();
                return 1;
            case POS_X:
                returnValues[0] = target.getX();
                return 1;
            case HEIGHT:
                returnValues[0] = target.getHeight();
                return 1;
            case FONT_SCALE_XY:
                returnValues[0] = ((Label) target).getFontScaleX();
                returnValues[1] = ((Label) target).getFontScaleX();
                return 2;
            case WIDTH_HEIGHT:
                returnValues[0] = target.getWidth();
                returnValues[1] = target.getHeight();
                return 2;
            case COLOR :
                returnValues[0] = target.getColor().r;
                returnValues[1] = target.getColor().g;
                returnValues[2] = target.getColor().b;
                return 3;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Actor target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case POS_XY:
                target.setPosition(newValues[0], newValues[1]);
                break;
            case CPOS_XY:
                target.setPosition(newValues[0] - target.getWidth() / 2,
                        newValues[1] - target.getHeight() / 2);
                break;
            case SCALE_XY:
                target.setScale(newValues[0], newValues[1]);
                break;
            case SCALE_X:
                target.setScale(newValues[0], target.getScaleY());
                break;
            case SCALE_Y:
                target.setScale(target.getScaleX(), newValues[0]);
                break;
            case ROTATE:
                target.setRotation(newValues[0]);
                break;
            case OPACITY:
                Color c = target.getColor();
                c.a = newValues[0];
                c.clamp();
                break;
            case POS_Y:
                target.setY(newValues[0]);
                break;
            case WIDTH:
                target.setWidth(newValues[0]);
                break;
            case POS_X:
                target.setX(newValues[0]);
                break;
            case HEIGHT:
                target.setHeight(newValues[0]);
                break;
            case FONT_SCALE_XY:
                ((Label) target).setFontScale(newValues[0], newValues[1]);
                break;
            case WIDTH_HEIGHT:
                target.setSize(newValues[0], newValues[1]);
                break;
            case COLOR:
                Color color = target.getColor();
                color.r = newValues[0];
                color.g = newValues[1];
                color.b = newValues[2];
                color.clamp();
                break;
        }
    }
}