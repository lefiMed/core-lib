package com.diora.core.view.window;

import com.badlogic.gdx.Gdx;
import com.diora.core.view.screens.GameScreen;

public class WinExit extends Window {

    public WinExit(GameScreen gameScreen) {
        super(gameScreen, "w_exit");
        addListener("ok", () -> {
            Gdx.app.exit();
        });

        addListener("cancel", () -> hide());
    }

    @Override
    protected void setupTween() {
        duration = 0.6f;
        super.setupTween();
    }

}
