package com.diora.core.stage.object.costum;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileExtern  extends TileActor {

    public TileExtern(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        propMustfound("name");
        String name = object.getProperties().get("name", String.class);

        TiledMap map = new TmxMapLoader().load("tmx/externs/"+name+".tmx");
        sc.mapToActors(map);

        Rectangle bounds =((RectangleMapObject) object).getRectangle();
        for (MapLayer mapLayer : map.getLayers()) {
            for (MapObject mapObject : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
                Actor actor = sc.get(Actor.class,getActorName());
                actor.setPosition(bounds.getX()+actor.getX(), bounds.getY()+actor.getY());
            }
        }

    }
}
