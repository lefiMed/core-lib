package com.diora.core.view.screens;

import com.diora.core.Game;
import com.diora.core.view.window.WinExit;

public abstract class Menu extends GameScreen {

    public Menu(Game game){
        super(game, "menu");

        sc.addListener("close", () -> new WinExit(this));

        sc.addListener("apropos", () -> game.setScreen(new Apropos(game)));

        game.android.action.lightOff();

        updateGlobalPrefs();
    }

    protected abstract void updateGlobalPrefs();

    @Override
    public void show() {
        super.show();
        game.dj.updateWithPrefs();

    }

}
