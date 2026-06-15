package com.diora.core.stage.object.costum;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.actor.ImageTimer;
import com.diora.core.stage.object.TileActor;

public class TileImageTimer extends TileActor {

    public TileImageTimer(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        ImageTimer imageTimer = new ImageTimer(sc,getS("name"),getS("back"),getS("front"),getS("styleNum"));
        if(has("fontScale"))
            imageTimer.number.setFontScale(getF("fontScale"));
        setStyle(imageTimer);
    }
}
