package com.diora.core.factory.map;

import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.diora.core.utils.maths.Clipper;
import com.diora.core.world.RectWorld;

import java.util.ArrayList;

public class PolygonObject extends ShapeObject{

    private static final long serialVersionUID = 1L;
    public transient Polygon polygon;
    public ArrayList<float[]> polygons;
    private RectWorld rect;

    public PolygonObject(PolygonMapObject object) {
        this.polygon = object.getPolygon();
    }

    public PolygonObject(PolygonMapObject object, RectWorld rect) {
        this.polygon = object.getPolygon();
        this.rect = rect;
    }

    public PolygonObject(Polygon polygon) {
        this.polygon = polygon;
    }

    @Override
    public void toShape() {
        polygon.setPosition(polygon.getX()-rect.x,polygon.getY()-rect.y);
        polygon.translate(-rect.halfWidth(), -rect.halfHeight());

        Vector2[][] polygons = Clipper.polygonize(Clipper.Polygonizer.EWJORDAN, vtsToPoints(polygon.getTransformedVertices()));
        PolygonShape poly = new PolygonShape();
        for (Vector2[] polys : polygons) {
            poly.set(polys);
            callBack.onShapeIt(poly);
        }
    }

    @Override
    public void toShape(float scale) {
        PolygonShape poly = new PolygonShape();
        for (float[] floats : polygons) {
            poly.set(scale(floats,scale));
            callBack.onShapeIt(poly);
        }
    }

    Vector2[] vtsToPoints(float[] vts){
        Vector2[] points = new Vector2[vts.length/2];
        int ii = -1;
        for (int i = 0; i < points.length; i++) {
            points[i] = new Vector2(vts[++ii], vts[++ii]);
        }
        return points;
    }

    public void setRect(RectWorld rect) {
        this.rect = rect;
    }
}
