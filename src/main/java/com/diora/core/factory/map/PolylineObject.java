package com.diora.core.factory.map;

import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.physics.box2d.ChainShape;

import static com.diora.core.Game.GAME;

public class PolylineObject extends ShapeObject{

    private static final long serialVersionUID = 1L;
    public transient Polyline polyline;
    public float[] localVertices;

    public PolylineObject(PolylineMapObject object) {
        this.polyline = object.getPolyline();
    }

    public PolylineObject(Polyline polyline) {
        this.polyline = polyline;
    }

    public void toShape(){
        float scl = 1/GAME.ppm;
        polyline.setPosition(polyline.getX()*scl, polyline.getY()*scl);
        polyline.setScale(scl, scl);
        ChainShape chain = new ChainShape();
        chain.createChain(polyline.getTransformedVertices());
        callBack.onShapeIt(chain);
    }

    @Override
    public void toShape(float scale) {
        ChainShape chain = new ChainShape();
        chain.createChain(scale(scale));
        callBack.onShapeIt(chain);
    }

    public float[] scale(float scl) {
        return super.scale(localVertices, scl);
    }
}
