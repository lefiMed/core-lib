package com.diora.core.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Disposable;
import com.diora.core.factory.WorldFactory;
import com.diora.core.view.screens.Play;

import static com.diora.core.utils.Global.getMapObjectId;

public class WorldCreator implements Disposable {

    public Play play;
    public TouchFixture touchFixture;
    public JointCreator jointCreator;
    private TiledMap map;
    private WorldContactListener worldContactListener;
    private ArrayMap<String ,Body> allBodys;
    public WorldFactory worldFactory;

    public WorldCreator(Play play, TiledMap map) {
        this.play = play;
        this.map = map;
        touchFixture = new TouchFixture(play.world);
        worldContactListener = new WorldContactListener();
        play.world.setContactListener(worldContactListener);
        if(play.game.worldFactory == null) {
            play.game.worldFactory = new WorldFactory();
        }

    }

    public void createAllSprite(){
        for (MapLayer layer : map.getLayers()) {
            String layerName = layer.getName();
            createSprites(play.layerToSprite(layerName), layerName);
        }
    }

    public void setWorldContactListener(WorldContactListener worldContactListener) {
        this.worldContactListener = worldContactListener;
        play.world.setContactListener(worldContactListener);
    }

    public <T> T createSprite(Class<T> sprite, String layerName, String objectName){
        MapObject object = map.getLayers().get(layerName).getObjects().get(objectName);
        return newSpriteInstance(sprite, object);
    }

    public  <T> void createSprites(Class<T> sprite, String layerName){
        if(sprite == null) return;
        MapObjects objects = map.getLayers().get(layerName).getObjects();
        for (MapObject object : objects) {
            newSpriteInstance(sprite, object);
        }
    }

    private <T> T newSpriteInstance(Class<T> tile, MapObject object){
        T tileSprite = null;
        try {tileSprite = tile
                .getConstructor(Play.class, MapObject.class)
                .newInstance(play, object);
        } catch (Exception e) {
            e.printStackTrace();
            //e.getCause().printStackTrace();
            Gdx.app.error("WorldCreator fix it",""+object.getName()+" "+e.getCause().getMessage());
        }
        return tileSprite;
    }


    public void createAllJoint() {
        jointCreator = new JointCreator(play.world, allBodys);
        jointCreator.createAllJoint(map);
    }

    public void addBody(MapObject object, Body body){
        if(allBodys == null)
             allBodys = new ArrayMap<>();
        allBodys.put(getMapObjectId(object), body);
    }

    public <T> T getJoint(Class<T> type, String key){
        if(jointCreator.joints!=null){
            type.cast(jointCreator.joints.get(key));
        }
        return null;
    }

    @Override
    public void dispose() {
        if(allBodys!=null)
            allBodys.clear();
        allBodys = null;
    }


}
