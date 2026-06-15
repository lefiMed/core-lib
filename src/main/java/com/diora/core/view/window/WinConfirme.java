package com.diora.core.view.window;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.diora.core.view.screens.GameScreen;

public class WinConfirme extends Window{

    public WinConfirme(GameScreen gameScreen, String txt ,Runnable runnable) {
        super(gameScreen, "w_confirme");
        get(Label.class, "txt").setText(txt);
        addListener("ok", () -> {
            hide(runnable);
        });

        addListener("cancel", () -> hide());

    }

    @Override
    protected void setupTween() {
        duration = 0.6f;
        super.setupTween();
    }
}
