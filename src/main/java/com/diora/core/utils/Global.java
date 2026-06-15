package com.diora.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.diora.core.Game;
import com.diora.core.world.RectWorld;

import java.util.ArrayList;
import java.util.Locale;

public class Global {

    public static String format2(Integer integer){
        return String.format(Locale.ENGLISH,"%02d",integer);
    }

    public static String format3(Integer integer){
        return String.format(Locale.ENGLISH,"%03d",integer);
    }

    public static void log(String msg){
        System.out.println(msg);
    }

    public static void error(String msg){
        Gdx.app.error("fix it", msg);
    }

    public static Vector2 boundMap(TiledMap map){
        float w = map.getProperties().get("width",Integer.class) * map.getProperties().get("tilewidth",Integer.class);
        float h = map.getProperties().get("height",Integer.class) * map.getProperties().get("tileheight",Integer.class);
        return new Vector2(w, h);
    }

    public static String getMapObjectId(MapObject object){
        return object.getName()!=null ? object.getName() : ""+object.getProperties().get("id",Integer.class);
    }

    public static String getMapLayerId(MapLayer layer){
        return layer.getName()!=null ? layer.getName() : ""+layer.getProperties().get("id",Integer.class);
    }

    public static boolean isSelect(MapProperties properties , String key) {
        return properties.containsKey(key) && properties.get(key, Boolean.class);
    }

    public static FileHandle file(String path){
        return Gdx.files.external(path);
    }

    // Maths

    public static Vector2[] mapObjectToPoints(MapObject object, RectWorld rectModel){
        Vector2[] points = null;
        if((object instanceof PolylineMapObject)){
            Polyline line = ((PolylineMapObject) object).getPolyline();
            line.setPosition(line.getX() / Game.GAME.ppm, line.getY() / Game.GAME.ppm);
            line.setScale(1/Game.GAME.ppm, 1/Game.GAME.ppm);
            line.translate(-rectModel.halfWidth(), -rectModel.halfHeight());
            points = vtsToPoints(line.getTransformedVertices());
        }else if(object instanceof RectangleMapObject){
            RectWorld rect = new RectWorld((RectangleMapObject) object);
            rect.translate(-rectModel.halfWidth(), -rectModel.halfHeight());
            points = new Vector2[]{rect.getPosistion()};
        }
        return points;
    }

    public static Vector2[] mapObjectToPoints(MapObject object){
        Vector2[] points = null;
        if((object instanceof PolylineMapObject)){
            Polyline line = ((PolylineMapObject) object).getPolyline();
            line.setPosition(line.getX() / Game.GAME.ppm, line.getY() / Game.GAME.ppm);
            line.setScale(1/Game.GAME.ppm, 1/Game.GAME.ppm);
            // line.translate(-rectModel.halfWidth(), -rectModel.halfHeight());
            points = vtsToPoints(line.getTransformedVertices());
        }else if(object instanceof RectangleMapObject){
            RectWorld rect = new RectWorld((RectangleMapObject) object);
            //rect.translate(-rectModel.halfWidth(), -rectModel.halfHeight());
            points = new Vector2[]{rect.getPosistion()};
        }
        return points;
    }

    public static Vector2[] vtsToPoints(float[] vts){
        Vector2[] points = new Vector2[vts.length/2];
        int ii = -1;
        for (int i = 0; i < points.length; i++) {
            points[i] = new Vector2(vts[++ii], vts[++ii]);
        }
        return points;
    }

    public static ArrayList<float[]> pointssToVtss(Vector2[][] polygons){
        ArrayList<float[]> polyg = new ArrayList<>(polygons.length);
        for (Vector2[] polygon : polygons) {
            polyg.add(pointsToVts(polygon));
        }
        return polyg;
    }

    public static float[] pointsToVts(Vector2[] points){
        float[] vts = new float[points.length*2];
        int ii = -1;
        for (int i = 0; i < points.length; i++) {
            vts[++ii] = points[i].x; vts[++ii] = points[i].y;
        }
        return vts;
    }

    public static Vector2 localVector(Vector2 vect){
        return vect.set(MathUtils.clamp(vect.x,-1,1), MathUtils.clamp(vect.y,-1,1));
    }

}
