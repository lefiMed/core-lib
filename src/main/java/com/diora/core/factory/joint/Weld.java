package com.diora.core.factory.joint;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.ArrayMap;
import com.diora.core.factory.def.DefBody;
import com.diora.core.factory.model.Model;

public class Weld extends DefJoint {

    private Vector2 anchorA, anchorB;
    private float referenceAngle;
    private float dampingRatio;
    private float frequencyHz;

    public Weld(Vector2[] points, MapProperties properties) {
        super(properties);
        anchorA = points[0]; anchorB = points[points.length-1];
        referenceAngle = 0;
        dampingRatio = getDampingRatio();
        frequencyHz = getFrequencyHz();
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
        WeldJointDef jDef = new WeldJointDef();
        jDef.bodyA = bodies.get(keyBodyA);
        jDef.bodyB = bodies.get(keyBodyB);
        jDef.localAnchorA.set(jDef.bodyA.getLocalPoint(anchorA));
        jDef.localAnchorB.set(jDef.bodyB.getLocalPoint(anchorB));
        jDef.referenceAngle = jDef.bodyB.getAngle() -  jDef.bodyA.getAngle();
        jDef.collideConnected = collideConnected;
        jDef.dampingRatio = dampingRatio;
        jDef.frequencyHz = frequencyHz;
        return jDef;
    }

    @Override
    public JointDef def(ArrayMap<String, Body> bodies, float scale) {
        WeldJointDef jDef = new WeldJointDef();
        jDef.bodyA = bodies.get(keyBodyA);
        jDef.bodyB = bodies.get(keyBodyB);
        jDef.localAnchorA.set(anchorA).scl(scale);
        jDef.localAnchorB.set(anchorB).scl(scale);
        jDef.referenceAngle = jDef.bodyB.getAngle() -  jDef.bodyA.getAngle();
        jDef.collideConnected = collideConnected;
        jDef.dampingRatio = dampingRatio;
        jDef.frequencyHz = frequencyHz;
        return jDef;
    }
}
