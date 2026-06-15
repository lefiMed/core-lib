package com.diora.core.graphgl.render;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import com.diora.core.Game;

public abstract class Renders implements Disposable{
    public ShaderProgram shader;

    public Renders() {
    }

    public Renders(String name) {
        this.shader = Game.GAME.shaders.getShader(name);
    }

    public Renders(String vert, String frag) {
        this.shader = Game.GAME.shaders.getShader(vert, frag);
    }

    public Renders(ShaderProgram shader) {
        this.shader = shader;
    }

    public abstract void draw(Batch batch);

    @Override
    public void dispose() {
        shader.dispose();
    }
}
