package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileTouchpad extends TileActor {

    public TileTouchpad(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        float deadzoneRadius = has("zone")? getF("zone") : 10.0f;

        Touchpad touchpad;
        try {
            touchpad = new Touchpad(deadzoneRadius, skin, getStyleName());
        }catch (Exception e) {
            Gdx.app.error("Touchpad : " + getActorName(), "style not correct in skin : " + getStyleName());
            Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle(sc.getUtils().drawable,sc.getUtils().drawable);
            touchpad = new Touchpad(deadzoneRadius, touchpadStyle);
        }

        setStyle(touchpad);
    }
}
