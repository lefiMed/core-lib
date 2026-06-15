package com.diora.core.stage.object.costum;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.actor.CheckBoxImage;
import com.diora.core.stage.object.TileActor;

public class TileCheckBoxImage extends TileActor {


    public TileCheckBoxImage(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        final String name = object.getName();
        final CheckBoxImage checkBoxImage = new CheckBoxImage(sc, name);

        setStyle(checkBoxImage);
    }
}
