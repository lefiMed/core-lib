package com.diora.core.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.diora.core.stage.object.TileActor;

public class GroupCreator {

    TiledMap map;

    public GroupCreator(TiledMap map) {
        this.map = map;
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            createActors(map.getLayers().get(i).getName());
        }
    }

    public void createActors(String nameLayer){
        createActors(nameLayer,map);
    }

    public void createActors(String nameLayer ,TiledMap map){
        ActorRef.ActorDef actorDef = ActorRef.layerToTileActor(nameLayer);
        for (MapObject object : map.getLayers().get(nameLayer).getObjects().getByType(RectangleMapObject.class)) {
            if (object.isVisible()) newActorInstance(actorDef.classDef, object, null);
        }
    }

    private Actor newActorInstance(Class<? extends TileActor> tileActorClass, MapObject object, String actorId){
        if(tileActorClass == null) return null;
        TileActor tileActor = null;
        try {tileActor =  tileActorClass
                .getConstructor (StageCreator.class,MapObject.class,String.class)
                .newInstance (this,object,actorId);
        }catch (Exception e) {
            e.getCause().printStackTrace();
            Gdx.app.error("StageCreator fix it",""+object.getName()+" "+e.getCause().getMessage());
        }
        return tileActor!=null?tileActor.getActor():null;
    }
}
