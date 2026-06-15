package com.diora.core.factory.map;

import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.diora.core.world.RectWorld;

public class CircleObject extends ShapeObject{

    private static final long serialVersionUID = 1L;
    public RectWorld rect;

    public CircleObject(EllipseMapObject object) {
        this.rect = new RectWorld(object);
    }

    public CircleObject(RectWorld rect) {
        this.rect = rect;
    }

    public void toShape(){
        CircleShape circle = new CircleShape();
        circle.setRadius(rect.halfWidth());
        callBack.onShapeIt(circle);
    }

    @Override
    public void toShape(float scale) {
        CircleShape circle = new CircleShape();
        RectWorld r = rect.copy().scale(scale);
        circle.setPosition(r.getCenter());
        circle.setRadius(r.halfWidth());
        callBack.onShapeIt(circle);
    }
}
