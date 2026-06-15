package com.diora.core.view.screens;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.diora.core.Game;
import com.diora.core.animation.particale.Particle;
import com.diora.core.view.fragment.ShowMsg;
import com.diora.core.view.window.WinConfirme;
import com.diora.core.view.window.WinPrivacyPolicy;
import com.diora.core.view.window.WinTermsConditions;

public class Apropos extends GameScreen {

    private Particle logoParticle;
    private Image logo;

    public Apropos(Game game) {
        super(game,"about");
        Vector2 pos = sc.getPoint("effect_pos");
        logoParticle = new Particle("logo",pos.x,pos.y,sc.stage.getViewport());

        sc.addListener("logo", () -> {
            if(logoParticle.isComplete()){
                logoParticle.start();
            }
        });

        sc.addListener("condition", () -> {
            logoParticle.stop();
            new WinTermsConditions(this);

        });
        sc.addListener("privacy", () -> {
            logoParticle.stop();
            new WinPrivacyPolicy(this);
        });

        sc.addListener("restoreGame", () -> {
            logoParticle.stop();
            new WinConfirme(this, " Restore the game setting and levels", ()->{
                game.gameAction.restoreGame();
                ShowMsg.show("You just restored game settings and levels");
            });
        });

        sc.addListener("restorePurchase", () -> {
            logoParticle.stop();
            new WinConfirme(this, "Restore the ads and lock forest pack",()->{
                game.android.billing.consumeAll();
                ShowMsg.show("You just restored all purchases");
            });
        });

        sc.addListener("insta", () -> game.android.action.falloqInstag());

        sc.addListener("teleg", () -> game.android.action.jointTeleg());

        sc.addListener("home", () -> game.gameAction.onBackPressed());

    }

    @Override
    public void show() {
        super.show();
        logoParticle.start();
    }

    @Override
    public void pause() {
        super.pause();
        logoParticle.stop();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        logoParticle.update(delta);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        logoParticle.draw(sc.stage.getBatch());
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
        logoParticle.dispose();
    }
}
