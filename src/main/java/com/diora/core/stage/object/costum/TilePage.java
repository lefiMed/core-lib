package com.diora.core.stage.object.costum;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.actor.PageView;
import com.diora.core.stage.object.TileActor;

public class TilePage extends TileActor {

    public TilePage(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        PageView pageView = new PageView(sc);

        setStyle(pageView);
        if (object.getProperties().containsKey("back")) {
            Pixmap pmap = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
            pmap.setColor(object.getProperties().get("back", Color.class));
            pmap.fill();
            TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pmap)));
            pmap.dispose();
          //  pageView.setBackground(drawable);
        }
        //if (object.getProperties().containsKey("isTransform"))
          //  pageView.setTransform(object.getProperties().get("isTransform", Boolean.class));
    }
}
