package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileImageButton extends TileActor {

    public TileImageButton(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        ImageButton imageButton;
        try {
            imageButton = new ImageButton(skin, getStyleName());
        }catch (Exception e) {
            Gdx.app.error("ImageButton : " + getActorName(), "style not correct in skin : " + getStyleName());
            ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
            imageButtonStyle.imageUp = sc.getUtils().drawable;
            imageButtonStyle.up = sc.getUtils().drawable;
            imageButton = new ImageButton(imageButtonStyle);
        }
        setStyle(imageButton);
    }
}
