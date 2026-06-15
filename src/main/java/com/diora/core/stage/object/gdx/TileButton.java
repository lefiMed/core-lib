package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileButton  extends TileActor {

    public TileButton(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        Button button;
        try {
            button = new Button(skin, getStyleName());
        }catch (Exception e){
            Gdx.app.error("Button : "+getActorName(),"style not correct in skin : "+getStyleName());
            Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
            buttonStyle.up = sc.getUtils().drawable;
            button = new Button(buttonStyle);
        }

        if(has("isTransform"))
            button.setTransform(props.get("isTransform",Boolean.class));
        setStyle(button);
    }
}
