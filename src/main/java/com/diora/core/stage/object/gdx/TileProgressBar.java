package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileProgressBar extends TileActor {

    public TileProgressBar(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        float min = has("min")? getF("min") : 0.0f;
        float max = has("max")? getF("max") : 100.0f;
        float step = has("step")? getF("step") : 1.0f;
        boolean isVertical = isSelect("isV");

        ProgressBar bar;
        try {
            bar = new ProgressBar(min,max,step,isVertical, sc.game.asset.skin, getStyleName());
        }catch (Exception e){
            Gdx.app.error("ProgressBar : "+getActorName(),"style not correct in skin : "+getStyleName());
            ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle(sc.getUtils().drawable, sc.getUtils().drawable);
            bar = new ProgressBar(min,max,step,isVertical, progressBarStyle);
        }
        if(has("value"))
            bar.setValue(getF("value"));

        setStyle(bar);
    }
}
