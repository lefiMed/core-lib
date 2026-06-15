package com.diora.core.factory.joint;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef;
import com.badlogic.gdx.utils.ArrayMap;
import com.diora.core.factory.def.DefBody;
import com.diora.core.factory.model.Model;

public class Motor extends DefJoint {

    private Vector2 linearOffset;
    public float maxForce;
    public float maxTorque;
    public float correctionFactor;

    public Motor(Vector2[] points, MapProperties properties) {
        super(properties);
        linearOffset = points[0];
        maxForce = getMaxForce();
        maxTorque = getMaxTorque();
        correctionFactor = getCorrectionFactor();
    }

    @Override
    public void applyLocal(Model model) {
        DefBody defBody1 = model.getDefBody(keyBodyA);
        if(!defBody1.position.isZero()){
            linearOffset.sub(defBody1.position);
        }
    }

    @Override
    public JointDef def(ArrayMap<String, Body> bodies) {
        MotorJointDef jDef = new MotorJointDef();
        jDef.bodyA = bodies.get(keyBodyA);
        jDef.bodyB = bodies.get(keyBodyB);
        jDef.collideConnected = collideConnected;
        //jDef.linearOffset.set(jDef.bodyA.getLocalPoint(jDef.bodyB.getPosition()));
        jDef.linearOffset.set(jDef.bodyA.getLocalPoint(linearOffset));
        jDef.angularOffset = jDef.bodyB.getAngle() - jDef.bodyA.getAngle();
        jDef.maxForce = maxForce;
        jDef.maxTorque = maxTorque;
        jDef.correctionFactor = correctionFactor;
        return jDef;
    }

    @Override
    public JointDef def(ArrayMap<String, Body> bodies, float scale) {
        MotorJointDef jDef = new MotorJointDef();
        jDef.bodyA = bodies.get(keyBodyA);
        jDef.bodyB = bodies.get(keyBodyB);
        jDef.collideConnected = collideConnected;
        jDef.linearOffset.set(linearOffset).scl(scale);
        jDef.angularOffset = jDef.bodyB.getAngle() - jDef.bodyA.getAngle();
        jDef.maxForce = maxForce;
        jDef.maxTorque = maxTorque;
        jDef.correctionFactor = correctionFactor;
        return jDef;
    }
}

