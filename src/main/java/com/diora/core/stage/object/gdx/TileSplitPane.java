package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileSplitPane  extends TileActor {

    public TileSplitPane(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        Actor firstWidget =null, secondWidget=null;
        if(has("actor1"))
            firstWidget = sc.get(getS("actor1"));
        if(has("actor2"))
            secondWidget = sc.get(getS("actor2"));
        boolean isVertical = isSelect("isV");
        SplitPane splitPane;
        try {
            splitPane = new SplitPane(firstWidget, secondWidget, isVertical, skin, getStyleName());
        }catch (Exception e) {
            Gdx.app.error("SplitPane : " + getActorName(), "style not correct in skin : " + getStyleName());
            SplitPane.SplitPaneStyle splitPaneStyle = new SplitPane.SplitPaneStyle(sc.getUtils().drawable);
            splitPane = new SplitPane(firstWidget, secondWidget, isVertical,splitPaneStyle);
        }
        setStyle(splitPane);
    }
}
