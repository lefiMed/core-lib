package com.diora.core.game.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraStyle {

    private float startX,startY,width,height;
    private Camera camera;

    public CameraStyle(Camera camera, Map map) {
        this.camera = camera;

        MapProperties properties = map.getProperties();
        int levelWidth = properties.get("width",Integer.class);
        int levelHeight = properties.get("height",Integer.class);
        startX = camera.viewportWidth / 2;
        startY = camera.viewportHeight / 2;
    //    width = (levelWidth * Final.T_WIDHT - startX * ppm) / ppm;
      //  height = (levelHeight * Final.T_HEIGHT - startY * ppm) / ppm;
    }

    public void samePosCamera(Vector2 target){
        Vector3 position = camera.position;
        position.set(target,0);
        camera.position.set(position);
        camera.update();
    }

    public void addPosCamera(Vector2 target){
        Vector3 position = camera.position;
        position.add(0,target.y,0);
        camera.position.set(position);
        camera.update();
    }

    public void smouthCamera (Vector2 target){
        Vector3 position = camera.position;
        position.x = (float) (camera.position.x + (target.x - camera.position.x) * .1);
        position.y = (float) (camera.position.y + (target.y - camera.position.y) * .1);
        camera.position.set(position);
        camera.update();
    }

}
