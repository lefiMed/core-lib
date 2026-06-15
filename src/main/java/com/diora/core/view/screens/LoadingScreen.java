package com.diora.core.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Interpolation;
import com.diora.core.Game;
import com.diora.core.graphgl.FullScreen;
import com.diora.core.tool.Percent;

public class LoadingScreen extends FullScreen implements DioraScreen {

    private ShaderProgram shader;
    private Texture logo, loading;
    private final Game game;
    private float x, y, w, h;
    private float x1,y1, w1, h1, s;
    private Percent alphaLogo, loadAnim;
    private BitmapFont font;
    boolean finished;
    Event event;

    public LoadingScreen(Game game, Event event) {
        this.event = event;
        super.batch = game.batch;
        this.game = game;
        logo = new Texture("ui/texture/diora_logo.png");
        loading = new Texture("ui/texture/loading.png");
        alphaLogo = new Percent(1.0f);
        loadAnim = new Percent(0.35f);
        loadAnim.setRepeat(true);
        shader = new ShaderProgram(Gdx.files.internal("shader/gradient.vert"),
                Gdx.files.internal("shader/gradient.frag"));
        ShaderProgram.pedantic = false;
        if (!shader.isCompiled())
            System.err.println(shader.getLog());
        if (shader.getLog().length() != 0)
            System.out.println(shader.getLog());
        batch.setShader(shader);
    }

    private void bound() {
        w = h = Math.min(screenWidth, screenHeight) / 3.0f;
        x = (screenWidth - w) / 2.0f;
        y = (screenHeight - h) / 2.0f;

        w1 = this.w/2;
        h1 = w1 * loading.getHeight()/loading.getWidth();
        x1 = (screenWidth - w1) / 2.0f;
        y1 = (screenHeight - h1) / 2.0f - this.h/2;

        shader.bind();
        shader.setUniformf("resolution", screenWidth, screenHeight);
    }

    @Override
    public void show() {

    }

    @Override
    public void captureRender(float v) {

    }

    @Override
    public void render(float delta) {
        super.draw(delta);
        alphaLogo.upadte(delta);
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float p = Interpolation.slowFast.apply(alphaLogo.getPercent());
        float e = Interpolation.slowFast.apply(alphaLogo.getPercent());
        game.batch.begin();
        game.batch.setColor(p, p, p, e);
        game.batch.setShader(shader);
        game.batch.draw(logo, 0, 0, screenWidth, screenHeight);
        game.batch.setShader(null);
        game.batch.draw(logo, x, y, w / 2.0f, h / 2.0f, w, h, 1.0f, 1.0f, 0.0f,
                0, 0, logo.getWidth(), logo.getHeight(), false, false);


        if (alphaLogo.isComplete()) {
            if(event!=null)
                event.onLoad();
            loadAnim.upadte(delta);
            if (game.asset.isUpdated() && game.android.isLoaded() && !finished) {
                finished = true;
                game.asset.finishLoading();
                if(event!=null)
                    event.onLoaded();
            }else if(!finished){
                s = 1.0f + 0.1f*Interpolation.smooth.apply(loadAnim.getPercent());
                game.batch.draw(loading, x1, y1, w1/2.0f, h1/2.0f, w1, h1, s, s,0.0f,
                        0, 0, loading.getWidth(), loading.getHeight(), false, false);
            }
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        bound();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        logo.dispose();
    }

   public interface Event{
        void onLoad();
        void onLoaded();
    }

}
