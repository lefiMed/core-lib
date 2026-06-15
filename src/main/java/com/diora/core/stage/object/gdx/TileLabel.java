package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;


public class TileLabel extends TileActor {

    public TileLabel(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        Label label = null;
        try {
            label = new Label(getText(), skin, getStyleName());
        }catch (Exception e){
            Gdx.app.error("Label : "+getActorName(),"style not correct in skin : "+getStyleName());
            label = new Label(getText(), new Label.LabelStyle(sc.getUtils().bitmapFont, sc.getUtils().color));
        }
        label.setAlignment(isSelect("left") ? Align.left : Align.center);
        if(isSelect("warp")) label.setWrap(true);
        if(has("fontScale"))
            label.setFontScale(getF("fontScale"));
        setStyle(label);
    }
}
