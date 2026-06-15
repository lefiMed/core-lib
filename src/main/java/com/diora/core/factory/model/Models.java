package com.diora.core.factory.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.diora.core.Game;
import com.diora.core.factory.Serialise;
import com.diora.core.utils.Global;

import java.io.Serializable;
import java.util.HashMap;

public class Models implements Serializable {

    private static final long serialVersionUID = 1L;
    public HashMap<String, Model> models;

    public Models(ArrayMap<String, TiledMap> tmxMaps) {
        this.models = new HashMap<>();

        for (ObjectMap.Entry<String, TiledMap> tmxMap : tmxMaps) {
            Model model = new Model(tmxMap.key, tmxMap.value);
            models.put(tmxMap.key,  model);
        }
    }

    //TODO check when relese
    public Model getModel(String name){
        Model model;
        if(models == null || Game.IS_DEBUG){
            Global.log("** Model Created ** "+name);
            model = new Model(name,new TmxMapLoader().load("tmx/body/"+name+".tmx"));
        }else {
            model = models.get(name);
        }
        if(model == null){
            Global.error("model :"+name+" not found");
        }
        return model;
    }

    public void serialise(String path){
        new Serialise(path, this);
    }


}
