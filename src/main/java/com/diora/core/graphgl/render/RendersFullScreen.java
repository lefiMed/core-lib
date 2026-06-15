package com.diora.core.graphgl.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import com.diora.core.Game;
import com.diora.core.graphgl.FullScreen;

public abstract class RendersFullScreen extends FullScreen implements Disposable {

    public ShaderProgram shader;

    public RendersFullScreen(Batch batch, String vert, String frag) {
        super(batch);
        this.shader = Game.GAME.shaders.getShader(vert, frag);

    }

    public RendersFullScreen(Batch batch, ShaderProgram shader) {
        super(batch);
        this.shader = shader;
    }

    @Override
    public void dispose() {
        shader.dispose();
    }
}
