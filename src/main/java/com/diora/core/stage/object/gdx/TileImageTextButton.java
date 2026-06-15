package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileImageTextButton extends TileActor {

    public TileImageTextButton(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        ImageTextButton imageTextButton;
        try {
            imageTextButton = new ImageTextButton(getText(), skin, getStyleName());
        } catch (Exception e) {
            Gdx.app.error("ImageTextButton : " + getActorName(), "style not correct in skin : " + getStyleName());
            ImageTextButton.ImageTextButtonStyle imageTextButtonStyle = new ImageTextButton.ImageTextButtonStyle();
            imageTextButtonStyle.font = sc.getUtils().bitmapFont;
            imageTextButtonStyle.imageUp = sc.getUtils().drawable;
            imageTextButtonStyle.up = sc.getUtils().drawable;
            imageTextButton = new ImageTextButton(getText(), imageTextButtonStyle);
        }

        setStyle(imageTextButton);
    }
}