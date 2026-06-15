package com.diora.core.view.window;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.diora.core.view.screens.GameScreen;

public class WinPrivacyPolicy extends Window {

    public WinPrivacyPolicy(GameScreen gameScreen) {
        super(gameScreen, "w_privacy_policy");
        get(ScrollPane.class,"scroll").setVelocityY(-1.0f);
        addListener("close", () -> hide());
    }
}
