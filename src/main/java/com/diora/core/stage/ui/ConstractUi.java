package com.diora.core.stage.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.actor.ButtonImage;

public class ConstractUi {

    protected StageCreator sc;

    public ConstractUi(StageCreator scr) {
        sc = scr;
    }
    //actors

    public ConstractUi rate(){
        sc.addListener("rate", () -> sc.game.android.action.rateGame());
        return this;
    }

    public ConstractUi share(){
        sc.addListener("share", () -> sc.game.android.action.shareGame());
        return this;
    }

    public ConstractUi updateUi(){
        sc.game.android.multiPlayer.updateUi((name, url, nbr_invi) -> {
            sc.get(Label.class,"name").setText(name);
            new TextureDownload(url, region -> sc.get(ButtonImage.class,"profile").setDrawable(new TextureRegionDrawable(region)));
        });
        return this;
    }
}
