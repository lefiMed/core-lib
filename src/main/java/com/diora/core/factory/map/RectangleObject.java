package com.diora.core.factory.map;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.diora.core.world.RectWorld;

public class RectangleObject extends ShapeObject{

    private static final long serialVersionUID = 1L;
    public RectWorld rect;

    public RectangleObject(RectangleMapObject object) {
        this.rect = new RectWorld(object.getRectangle());
    }

    public RectangleObject(RectWorld rect) {
        this.rect = rect;
    }

    public void toShape(){
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(rect.halfWidth(), rect.halfHeight());
        callBack.onShapeIt(poly);
    }

    public void toShape(float scale) {
        PolygonShape poly = new PolygonShape();
        poly.set(rect.copy().scale(scale).getVertices());
        callBack.onShapeIt(poly);
    }
}
