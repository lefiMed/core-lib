package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileCheckBox extends TileActor {

    public TileCheckBox(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        CheckBox checkBox;
        try {
            checkBox = new CheckBox(getText(), skin, getStyleName());
        }catch (Exception e) {
            Gdx.app.error("CheckBox : " + getActorName(), "style not correct in skin : " + getStyleName());
            CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle(sc.getUtils().drawable,sc.getUtils().drawable,sc.getUtils().bitmapFont,sc.getUtils().color);
            checkBox = new CheckBox(getText(), checkBoxStyle);
        }
        setStyle(checkBox);
    }
}
