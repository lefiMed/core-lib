package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileGroup extends TileActor {

    public TileGroup(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        Group group = new Group();

        setStyle(group);
        group.setTransform(isSelect("isTransform"));
    }
}
