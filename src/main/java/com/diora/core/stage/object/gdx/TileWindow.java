package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;


public class TileWindow extends TileActor {

    public TileWindow(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        String title = has("title")?getS("title"):"";
        Window window;
        try {
            window = new Window(title, skin, getStyleName());
        }catch (Exception e) {
            Gdx.app.error("Window : " + getActorName(), "style not correct in skin : " + getStyleName());
            Window.WindowStyle windowStyle = new Window.WindowStyle(sc.getUtils().bitmapFont,sc.getUtils().color,sc.getUtils().drawable);
            window = new Window(title, windowStyle);
        }

        setStyle(window);
    }
}
