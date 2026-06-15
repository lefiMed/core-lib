package com.diora.core.graphgl.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Interpolation;
import com.diora.core.Game;
import com.diora.core.tool.Percent;
import com.diora.core.utils.Global;

public class BlurRender extends RendersFullScreen{

    private TextureRegion fboRegion;
    protected FrameBuffer fboA, fboB;
    private DrawCall drawCall;
    private Percent percent;
    private boolean isResized, isBluring, blured;
    protected int fboWidth, fboHeight;
    private float density;

    public BlurRender(DrawCall drawCall) {
        super(Game.GAME.batch, "blur", "blur");
        this.drawCall = drawCall;
        percent = new Percent(1);

        fboWidth = screenWidth /6;
        fboHeight = screenHeight /6;
        fboA = new FrameBuffer(Pixmap.Format.RGBA8888, fboWidth, fboHeight, false);
        fboB = new FrameBuffer(Pixmap.Format.RGBA8888, fboWidth, fboHeight, false);
        fboRegion = new TextureRegion(fboA.getColorBufferTexture());
        fboRegion.flip(false, true);

        shader.bind();
        shader.setUniformf("radius", density);
        shader.setUniformf("resolution", fboWidth, fboHeight);
        shader.setUniformf("dir", 1f, 0f);
       // shader.end();

        density = Gdx.graphics.getDensity();
        if(!Game.GAME.isAndroid)
            density *=3f;
    }

    public void begin(){
        if(!isResized) {
            drawCall.resize(fboWidth, fboHeight);
            isResized = true;
        }
        isBluring = true;
    }

    public void end(){
        drawCall.resize(screenWidth, screenHeight);
        isResized = false;
        isBluring = false;
    }

    public void captureRender(float dt){
        if(isBluring){
            blur(dt);
        }
    }

    @Override
    public void draw(float dt) {
        if(isBluring){
            if(!blured) blur(dt);
            resizeBatch(screenWidth, screenHeight);
            batch.begin();
            fboRegion.setTexture(fboA.getColorBufferTexture());
            batch.draw(fboRegion, 0, 0, screenWidth, screenHeight);
            batch.end();
            blured = false;
        }else {
            drawCall.draw(dt);
        }
    }

    //TODO old shader
    private void blur(float dt){
        percent.upadte(dt);
        float radiusA = density * Interpolation.exp5Out.apply(percent.getPercent());
        float radiusB = radiusA * 1.5f;

        fboA.begin();
        drawCall.draw(dt);
        fboA.end();

        ShaderProgram oldShader = batch.getShader();
        batch.setShader(shader);
        resizeBatch(fboWidth, fboHeight);
        fboB.begin();
        batch.begin();
        shader.setUniformf("radius", radiusA);
        shader.setUniformf("dir", 1f, 0f);
        fboRegion.setTexture(fboA.getColorBufferTexture());
        batch.draw(fboRegion, 0, 0);
        batch.flush();
        fboB.end();

        fboA.begin();
        shader.setUniformf("dir", 0f, 1f);
        fboRegion.setTexture(fboB.getColorBufferTexture());
        batch.draw(fboRegion, 0, 0);
        batch.flush();
        fboA.end();

        fboB.begin();
        shader.setUniformf("radius", radiusB);
        shader.setUniformf("dir", 1f, 0f);
        fboRegion.setTexture(fboA.getColorBufferTexture());
        batch.draw(fboRegion, 0, 0);
        batch.flush();
        fboB.end();

        fboA.begin();
        shader.setUniformf("dir", 0f, 1f);
        fboRegion.setTexture(fboB.getColorBufferTexture());
        batch.draw(fboRegion, 0, 0);
        batch.end();
        fboA.end();

        batch.setShader(oldShader);
        blured = true;
    }

    private void resizeBatch(int width, int height) {
        Gdx.gl20.glViewport(0, 0, width, height);
        combined.setToOrtho2D(0,0,width, height);
        batch.setProjectionMatrix(combined);

    }

    public void resize(int width, int height) {
        super.resize(width, height);
        fboWidth = screenWidth /6;
        fboHeight = screenHeight / 6;
        fboA.dispose();
        fboB.dispose();
        fboA = new FrameBuffer(Pixmap.Format.RGBA8888, fboWidth, fboHeight, false);
        fboB = new FrameBuffer(Pixmap.Format.RGBA8888, fboWidth, fboHeight, false);
        fboRegion = new TextureRegion(fboA.getColorBufferTexture());
        fboRegion.flip(false, true);
        shader.bind();
        shader.setUniformf("resolution", fboWidth, fboHeight);
        if(isBluring)
            drawCall.resize(fboWidth, fboHeight);
    }

    @Override
    public void dispose() {
        fboA.dispose();
        fboB.dispose();
        super.dispose();
        Global.log("dispose blur");
    }
}
