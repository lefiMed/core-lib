package com.diora.core.stage.object.costum;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.actor.ChangeValue;
import com.diora.core.stage.object.TileActor;

public class TileChangeValue extends TileActor {

    public TileChangeValue(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        ChangeValue changeValue = new ChangeValue(sc, getActorName());
        setStyle(changeValue);

        if(has("step")) changeValue.setStep(getF("step"));
        if(has("value")) changeValue.setValue(getF("value"));
        if(has("max")) changeValue.setMax(getF("max"));
        if(has("min")) changeValue.setMin(getF("min"));
    }
}
