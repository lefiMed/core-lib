package com.diora.core.factory.joint;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.utils.ArrayMap;
import com.diora.core.factory.def.DefBody;
import com.diora.core.factory.model.Model;

import static com.diora.core.Game.GAME;

public class Rope extends DefJoint {

    //TODO maxlenghth
    private Vector2 anchorA, anchorB;
    private float maxLength;

    public Rope(Vector2[] points, MapProperties properties) {
        super(properties);
        anchorA = points[0];
        anchorB = points[points.length - 1];
        maxLength = getLength();
    }

    @Override
    public void applyLocal(Model model) {
        DefBody defBody1 = model.getDefBody(keyBodyA);
        if(!defBody1.position.isZero()){
            anchorA.sub(defBody1.position);
        }
        DefBody defBody2 = model.getDefBody(keyBodyB);
        if(!defBody2.position.isZero()){
            anchorB.sub(defBody2.position);
        }
    }

    @Override
    public JointDef def(ArrayMap<String, Body> bodies) {
        RopeJointDef jDef = new RopeJointDef();
        jDef.bodyA = bodies.get(keyBodyA);
        jDef.bodyB = bodies.get(keyBodyB);
        jDef.localAnchorA.set(jDef.bodyA.getLocalPoint(anchorA));
        jDef.localAnchorB.set(jDef.bodyB.getLocalPoint(anchorB));
        jDef.maxLength = maxLength;
        jDef.collideConnected = collideConnected;
        return jDef;
    }

    public JointDef def(ArrayMap<String, Body> bodies, float scale) {
        RopeJointDef jDef = new RopeJointDef();
        jDef.bodyA = bodies.get(keyBodyA);
        jDef.bodyB = bodies.get(keyBodyB);
        jDef.localAnchorA.set(anchorA).scl(scale);
        jDef.localAnchorB.set(anchorB).scl(scale);
        jDef.maxLength = maxLength * scale;
        jDef.collideConnected = collideConnected;
        return jDef;
    }

    protected float getLength() {
        float lengh = prop.getF("length") / GAME.ppm;
        if (lengh == 0) lengh = anchorA.dst(anchorB);
        return lengh;
    }
}