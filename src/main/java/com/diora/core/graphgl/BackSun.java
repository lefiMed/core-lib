package com.diora.core.graphgl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.diora.core.Game;
import com.diora.core.animation.tweens.Tween;
import com.diora.core.animation.tweens.TweenManager;
import com.diora.core.animation.tweens.accessors.ActorAccessor;

public class BackSun {

    private Image sun;

    public BackSun(Game game, String name, float alpha) {
        sun = new Image(game.asset.skin, name);
        sun.getColor().a = alpha;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void rotate(TweenManager tm) {
        Tween.to(sun, ActorAccessor.ROTATE, 30).target(90).repeat(Tween.INFINITY, 0).start(tm);
    }

    public void draw(Batch batch) {
        batch.begin();
        sun.draw(batch, 1.0F);
        batch.end();
    }

    public void resize(int width, int height) {
        float max = ((float) Math.max(width, height));
        max *=1.5f;
        sun.setAlign(Align.center);
        sun.setSize(max, max);
        sun.setPosition((width - max)/2,(height-max)/2);
        sun.setOrigin(max/2f,max/2f);
    }
}
