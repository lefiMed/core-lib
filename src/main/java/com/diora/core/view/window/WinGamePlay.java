package com.diora.core.view.window;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.diora.core.view.screens.GameScreen;

public class WinGamePlay extends Window {

    public WinGamePlay(final GameScreen gameScreen) {
        super(gameScreen,"w_gameplay");

        addListener("close", new Runnable() {
            @Override
            public void run() {
                hide();
            }
        });

        addListener("singin", new Runnable() {
            @Override
            public void run() {
                if (Gdx.app.getType().equals(Application.ApplicationType.Android))
                    sc.game.android.multiPlayer.signIn();
            }
        });

    }
}
