package com.diora.core.factory.joint;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.utils.ArrayMap;
import com.diora.core.factory.model.Model;
import com.diora.core.utils.PropGetter;

import java.io.Serializable;

import static com.diora.core.Game.GAME;

public abstract class DefJoint implements Serializable {

    static final long serialVersionUID = 1L;
    public String keyBodyA, keyBodyB;
    public boolean collideConnected ;
    protected transient PropGetter prop;

    public DefJoint(MapProperties properties) {
        this.prop = new PropGetter(properties);
        keyBodyA = prop.getS("bodyA");
        keyBodyB = prop.getS("bodyB");
        collideConnected = prop.getB("collideConnected");
    }

    public void applyLocal(Model model){

    }

    public abstract JointDef def(ArrayMap<String, Body> bodies);

    public abstract JointDef def(ArrayMap<String, Body> bodies, float scale);

    protected float getDampingRatio(){
        return prop.getF("dampingRatio");
    }

    protected float getFrequencyHz(){
        return prop.getF("frequencyHz");
    }

    protected boolean isEnableLimit(){
        return prop.getB("enableLimit");
    }

    protected boolean isEnableMotor(){
        return prop.getB("enableMotor");
    }

    protected float getLowerAngle(){
        return prop.getF("lowerAngle") * MathUtils.degRad;
    }

    protected float getUpperAngle(){
        return prop.getF("upperAngle") * MathUtils.degRad;
    }

    protected float getMotorSpeed(){
        return prop.getF("motorSpeed");
    }

    protected float getMaxMotorTorque(){
        return prop.getF("maxMotorTorque") * GAME.ppm;
    }

    protected float getMaxForce(){ return prop.getF("maxForce"); }

    protected float getMaxTorque(){
        return prop.getF("maxTorque") * GAME.ppm;
    }

    protected float getCorrectionFactor(){
        return prop.getF("correctionFactor",0.3f);
    }

    protected float getMaxMotorForce(){ return prop.getF("maxMotorForce") * GAME.ppm; }

    protected float getLowerTranslation(){
        return prop.getF("lowerTranslation") / GAME.ppm;
    }

    protected float getUpperTranslation(){
        return prop.getF("upperTranslation") / GAME.ppm;
    }

    protected float getRatio(){
        return prop.getF("ratio");
    }
}
