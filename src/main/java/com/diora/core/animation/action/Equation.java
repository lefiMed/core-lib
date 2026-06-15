package com.diora.core.animation.action;

import com.badlogic.gdx.math.Interpolation;
import com.diora.core.animation.tweens.TweenEquation;

public abstract class Equation extends Interpolation{

    // Interpolition Equation
    public static final SwingOut swingOut = new SwingOut(1.5f);

    // TweenEquation Equation

    private Equation() {
    }

    public static Interpolation e(final TweenEquation tweenEquation){
        return new Interpolation() {
            @Override
            public float apply(float a) {
                return tweenEquation.compute(a);
            }
        };
    }

    public static TweenEquation e(final Interpolation interpolation){
        return new TweenEquation() {
            @Override
            public float compute(float t) {
                return interpolation.apply(t);
            }
        };
    }

}