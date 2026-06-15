package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileScrollPane extends TileActor {

    public TileScrollPane(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        Actor actorToadd = null;
        if(has("actor")){
            String n_actor  = getS("actor");
            if(!n_actor.equals("")){
                actorToadd = sc.get(getS("actor"));
            }
        }

        ScrollPane scrollPane;
        try{
            scrollPane = new ScrollPane(actorToadd, skin, getStyleName());
        }catch (Exception e){
            Gdx.app.error("ScrollPane : "+getActorName(),"style not correct in skin : "+getStyleName());
            ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
            scrollPane = new ScrollPane(actorToadd ,paneStyle);
        }
        scrollPane.setScrollingDisabled(!isSelect("scrollX"), !isSelect("scrollY"));
        setStyle(scrollPane);
    }
}
