package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileList extends TileActor {

    public TileList(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        //propMustfound("classCast");
        List<String> list;
        try {
            list = new List<String>(skin, getStyleName());
        }catch (Exception e){
            Gdx.app.error("List : "+getActorName(),"style not correct in skin : "+getStyleName());
            List.ListStyle listStyle = new List.ListStyle(sc.getUtils().bitmapFont, sc.getUtils().color,sc.getUtils().color,sc.getUtils().drawable);
            list = new List<String>(listStyle);
        }

        setStyle(list);
    }
}
