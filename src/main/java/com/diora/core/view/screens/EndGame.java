package com.diora.core.view.screens;

import com.diora.core.Game;
import com.diora.core.stage.ui.ConstractUi;

public class EndGame extends GameScreen {

    public EndGame(Game game) {
        super(game,"endGame");

        sc.addListener("apropos", () -> game.setScreen(new Apropos(game)));

        new ConstractUi(sc).rate().share();

        sc.pop("replay");
    }
}
