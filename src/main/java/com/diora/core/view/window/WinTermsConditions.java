package com.diora.core.view.window;

import com.diora.core.view.screens.GameScreen;

public class WinTermsConditions extends Window {
    public WinTermsConditions(GameScreen gameScreen) {
        super(gameScreen, "w_terms_conditions");

        addListener("close", () -> hide());
    }
}
