package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileTextFiled extends TileActor {

    public TileTextFiled(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        TextField textField;
        try {
            textField = new TextField(getText(),skin, getStyleName());
        }catch (Exception e){
            Gdx.app.error("TextField : "+getActorName(),"style not correct in skin : "+getStyleName());
            TextField.TextFieldStyle fieldStyle = new TextField.TextFieldStyle();
            fieldStyle.font = sc.getUtils().bitmapFont;
            fieldStyle.fontColor = sc.getUtils().color;
            textField = new TextField(getText(), fieldStyle);
        }

        setStyle(textField);
    }
}
