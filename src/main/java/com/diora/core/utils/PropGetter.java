package com.diora.core.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

public class PropGetter {

    private static PropGetter instance;

    public MapProperties properties;

    public PropGetter(MapProperties properties) {
        this.properties = properties;
    }

    public float getF(String name){
        float f = 0.0f;
        if(properties.containsKey(name))
            f = properties.get(name, Float.class);
        return f;
    }

    public float getF(String name, float def){
        float f = def;
        if(properties.containsKey(name))
            f = properties.get(name, Float.class);
        return f;
    }

    public int getI(String name){
        int i = 0;
        if(properties.containsKey(name))
            i = properties.get(name, Integer.class);
        return i;
    }

    public int getI(String name, int def){
        int i = def;
        if(properties.containsKey(name))
            i = properties.get(name, Integer.class);
        return i;
    }

    public short getShort(String name){
        int i = 0;
        if(properties.containsKey(name))
            i = properties.get(name, Integer.class);
        return ((short) i);
    }

    public short getShort(String name, int defaultV){
        int i = defaultV;
        if(properties.containsKey(name))
            i = properties.get(name, Integer.class);
        return ((short) i);
    }

    public String getS(String name) {
        String s = "";
        if(properties.containsKey(name))
            s = properties.get(name, String.class);
        return s;
    }

    public String getS(String name, String def) {
        String s = def;
        if(properties.containsKey(name))
            s = properties.get(name, String.class);
        return s;
    }

    public boolean getB(String name, boolean def){
        boolean b = def;
        if(properties.containsKey(name))
            b = properties.get(name, Boolean.class);
        return b;
    }

    public boolean getB(String name){
        return getB(name, false);
    }

    public Color getC(String name){
        Color c = Color.BLACK;
        if(properties.containsKey(name))
            c = properties.get(name, Color.class);
        return c;
    }

    public static PropGetter instance(MapProperties properties){
        if(instance == null){
            instance = new PropGetter(properties);
        }else {
            instance.setProperties(properties);
        }
        return instance;
    }

    public static PropGetter instance(MapObject object){
        if(instance == null){
            instance = new PropGetter(object.getProperties());
        }else {
            instance.setProperties(object.getProperties());
        }
        return instance;
    }

    public void setProperties(MapProperties properties) {
        this.properties = properties;
    }
}
