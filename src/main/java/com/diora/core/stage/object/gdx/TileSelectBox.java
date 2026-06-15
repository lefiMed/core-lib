package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileSelectBox extends TileActor {

    public TileSelectBox(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        //propMustfound("classCast");
        SelectBox selectBox;
        try {
            selectBox = new SelectBox(skin, getStyleName());
        }catch (Exception e){
            Gdx.app.error("SelectBox : "+getActorName(),"style not correct in skin : "+getStyleName());
            ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
            List.ListStyle listStyle = new List.ListStyle(sc.getUtils().bitmapFont, sc.getUtils().color,sc.getUtils().color,sc.getUtils().drawable);
            SelectBox.SelectBoxStyle boxStyle = new SelectBox.SelectBoxStyle(sc.getUtils().bitmapFont,sc.getUtils().color,sc.getUtils().drawable,paneStyle,listStyle);
            selectBox = new SelectBox(boxStyle);
        }

        setStyle(selectBox);
    }
}
