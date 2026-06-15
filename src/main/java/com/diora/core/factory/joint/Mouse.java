package com.diora.core.factory.joint;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.ArrayMap;

public class Mouse extends DefJoint {

    private Vector2 target;
    private float dampingRatio;
    private float frequencyHz;
    private float maxForce;

    public Mouse(Vector2[] points, MapProperties properties) {
        super(properties);
        target = points[0];
        dampingRatio = getDampingRatio();
        frequencyHz = getFrequencyHz();
        maxForce = getMaxForce();
    }

    @Override
    public JointDef def(ArrayMap<String, Body> bodies) {
        MouseJointDef jDef = new MouseJointDef();
        jDef.bodyA = bodies.get(keyBodyA);
        jDef.bodyB = bodies.get(keyBodyB);
        jDef.target.set(target);
        jDef.collideConnected = collideConnected;
        jDef.maxForce = maxForce;
        jDef.dampingRatio = dampingRatio;
        jDef.frequencyHz = frequencyHz;
        return jDef;
    }

    public JointDef def(ArrayMap<String, Body> bodies, float scale) {
        MouseJointDef jDef = new MouseJointDef();
        jDef.bodyA = bodies.get(keyBodyA);
        jDef.bodyB = bodies.get(keyBodyB);
        jDef.target.set(jDef.bodyA.getWorldPoint(target.cpy().scl(scale)));
        jDef.collideConnected = collideConnected;
        jDef.maxForce = maxForce;
        jDef.dampingRatio = dampingRatio;
        jDef.frequencyHz = frequencyHz;
        return jDef;
    }

}