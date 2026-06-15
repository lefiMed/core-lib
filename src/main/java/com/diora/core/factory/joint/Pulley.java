package com.diora.core.factory.joint;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
import com.badlogic.gdx.utils.ArrayMap;
import com.diora.core.factory.def.DefBody;
import com.diora.core.factory.model.Model;

public class Pulley extends DefJoint {

    private Vector2 anchorA, anchorB, axisA, axisB;
    private float ratio, lengthA, lengthB;

    public Pulley(Vector2[] points, MapProperties properties) {
        super(properties);
        anchorA = points[0];
        anchorB = points[points.length - 1];
        axisA = points[1];
        axisB = points[2];
        ratio = getRatio();
        lengthA = anchorA.dst(axisA);
        lengthB = anchorB.dst(axisB);
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
        PulleyJointDef jDef = new PulleyJointDef();
        jDef.bodyA = bodies.get(keyBodyA);
        jDef.bodyB = bodies.get(keyBodyB);
        jDef.localAnchorA.set(jDef.bodyA.getLocalPoint(anchorA));
        jDef.localAnchorB.set(jDef.bodyB.getLocalPoint(anchorB));
        jDef.groundAnchorA.set(axisA);
        jDef.groundAnchorB.set(axisB);
        jDef.collideConnected = collideConnected;
        jDef.lengthA = lengthA;
        jDef.lengthB = lengthB;
        jDef.ratio = ratio;
        return jDef;
    }

    @Override
    public JointDef def(ArrayMap<String, Body> bodies, float scale) {
        PulleyJointDef jDef = new PulleyJointDef();
        jDef.bodyA = bodies.get(keyBodyA);
        jDef.bodyB = bodies.get(keyBodyB);
        jDef.localAnchorA.set(anchorA).scl(scale);
        jDef.localAnchorB.set(anchorB).scl(scale);
        jDef.groundAnchorA.set(jDef.bodyA.getWorldPoint(axisA.cpy().scl(scale)));
        jDef.groundAnchorB.set(jDef.bodyB.getWorldPoint(axisB.cpy().scl(scale)));
        jDef.collideConnected = collideConnected;
        jDef.lengthA = lengthA * scale;
        jDef.lengthB = lengthB * scale;
        jDef.ratio = ratio;
        return jDef;
    }
}