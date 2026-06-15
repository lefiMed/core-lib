package com.diora.core.factory.joint;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.utils.ArrayMap;
import com.diora.core.factory.def.DefBody;
import com.diora.core.factory.model.Model;
import com.diora.core.utils.Global;

public class Prismatic  extends DefJoint {

    private Vector2 anchorA, anchorB, axisA;
    private boolean enableLimit;
    private float lowerTranslation, upperTranslation;
    private boolean enableMotor;
    private float motorSpeed, maxMotorForce;

    public Prismatic(Vector2[] points, MapProperties properties) {
        super(properties);
        anchorA = points[0]; anchorB = points[points.length-1];
        axisA = points[1];
        enableLimit = isEnableLimit();
        lowerTranslation = getLowerTranslation();
        upperTranslation = getUpperTranslation();
        enableMotor = isEnableMotor();
        motorSpeed = getMotorSpeed();
        maxMotorForce = getMaxMotorForce();
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
        PrismaticJointDef jDef = new PrismaticJointDef();
        jDef.bodyA = bodies.get(keyBodyA);
        jDef.bodyB = bodies.get(keyBodyB);
        jDef.localAnchorA.set(jDef.bodyA.getLocalPoint(anchorA));
        jDef.localAnchorB.set(jDef.bodyB.getLocalPoint(anchorB));
        jDef.localAxisA.set(Global.localVector(jDef.bodyA.getLocalPoint(axisA)));
        jDef.referenceAngle = jDef.bodyB.getAngle() -  jDef.bodyA.getAngle();
        jDef.collideConnected = collideConnected;
        jDef.enableLimit = enableLimit;
        jDef.lowerTranslation = lowerTranslation;
        jDef.upperTranslation = upperTranslation;
        jDef.enableMotor = enableMotor;
        jDef.motorSpeed = motorSpeed;
        jDef.maxMotorForce = maxMotorForce;
        return jDef;
    }

    @Override
    public JointDef def(ArrayMap<String, Body> bodies, float scale) {
        PrismaticJointDef jDef = new PrismaticJointDef();
        jDef.bodyA = bodies.get(keyBodyA);
        jDef.bodyB = bodies.get(keyBodyB);
        jDef.localAnchorA.set(anchorA).scl(scale);
        jDef.localAnchorB.set(anchorB).scl(scale);
        jDef.localAxisA.set(Global.localVector(axisA));
        jDef.referenceAngle = jDef.bodyB.getAngle() -  jDef.bodyA.getAngle();
        jDef.collideConnected = collideConnected;
        jDef.enableLimit = enableLimit;
        jDef.lowerTranslation = lowerTranslation;
        jDef.upperTranslation = upperTranslation;
        jDef.enableMotor = enableMotor;
        jDef.motorSpeed = motorSpeed;
        jDef.maxMotorForce = maxMotorForce;
        return jDef;
    }



}
