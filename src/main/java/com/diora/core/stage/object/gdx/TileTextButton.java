package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileTextButton extends TileActor {

    public TileTextButton(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        TextButton textButton = null;
        try {
            textButton = new TextButton(getText(), skin, getStyleName());
        }catch (Exception e){
            Gdx.app.error("TextButton : "+getActorName(),"style not correct in skin : "+getStyleName());
            TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
            style.font = sc.getUtils().bitmapFont;
            style.up = sc.getUtils().drawable;
            textButton = new TextButton(getText(), style);
        }
        if(has("isTransform"))
            textButton.setTransform(props.get("isTransform",Boolean.class));
        if(has("fontScale")){
            textButton.getLabel().setFontScale(props.get("fontScale",Float.class));
        }
        if(has("fontColor")){
            textButton.getLabel().setColor(props.get("fontColor", Color.class));
        }
        setStyle(textButton);
    }
}
