package com.diora.core.factory.joint;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;
import com.badlogic.gdx.utils.ArrayMap;

public class Gear extends DefJoint {

    public String keyJointA, keyJointB;
    public float ratio;

    public Gear(Vector2[] points, MapProperties properties) {
        super(properties);
        keyJointA = prop.getS("joint1");
        keyJointB = prop.getS("joint2");
        ratio = getRatio();
    }

    @Override
    public JointDef def(ArrayMap<String, Body> bodies) {
        return null;
    }

    @Override
    public JointDef def(ArrayMap<String, Body> bodies, float scale) {
        return null;
    }

    public JointDef def(ArrayMap<String, Body> bodies, ArrayMap<String, Joint> joints) {
        GearJointDef jDef = new GearJointDef();
        jDef.bodyA = bodies.get(keyBodyA);
        jDef.bodyB = bodies.get(keyBodyB);
        jDef.joint1 = joints.get(keyJointA);
        jDef.joint2 = joints.get(keyJointB);
        jDef.collideConnected = collideConnected;
        jDef.ratio = ratio;
        return jDef;
    }
}
