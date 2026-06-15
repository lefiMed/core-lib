package com.diora.core.view.window;

import com.diora.core.Game;
import com.diora.core.view.screens.GameScreen;

public class WinSetting extends Window {

    public WinSetting(final GameScreen gameScreen) {
        super(gameScreen, "w_setting");

        addListener("close", new Runnable() {
            @Override
            public void run() {
                hide();
            }
        });

        addListener("sound", new Runnable() {
            @Override
            public void run() {
                Game.GAME.dj.updatePrefs();
                Game.GAME.dj.updateWithPrefs();
            }
        });

        addListener("music", new Runnable() {
            @Override
            public void run() {
                Game.GAME.dj.updatePrefs();
                Game.GAME.dj.updateWithPrefs();
            }
        });
    }
}
