package com.diora.core.world;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.diora.core.factory.joint.DefJoint;
import com.diora.core.factory.joint.Distance;
import com.diora.core.factory.joint.Friction;
import com.diora.core.factory.joint.Gear;
import com.diora.core.factory.joint.Motor;
import com.diora.core.factory.joint.Mouse;
import com.diora.core.factory.joint.Prismatic;
import com.diora.core.factory.joint.Pulley;
import com.diora.core.factory.joint.Revolute;
import com.diora.core.factory.joint.Rope;
import com.diora.core.factory.joint.Weld;
import com.diora.core.factory.joint.Wheel;

import static com.diora.core.utils.Global.getMapObjectId;
import static com.diora.core.utils.Global.mapObjectToPoints;

public class JointCreator {

    public ArrayMap<String, Joint> joints;
    private World world;
    private ArrayMap<String, Body> bodies;

    public JointCreator(World world, ArrayMap<String, Body> allBodys) {
        this.world = world;
        this.bodies = allBodys;
    }

    public  void createAllJoint(TiledMap map){
        MapGroupLayer joints = ((MapGroupLayer) map.getLayers().get("joint"));
        if(joints!=null) {
            for (MapLayer layer : joints.getLayers()) {
                if (layer.isVisible()) {
                    String nameJoint = layer.getName();
                    for (MapObject object : layer.getObjects()) {
                        if (object.isVisible()) {
                            DefJoint jointDef = objectToDefJoint(nameJoint, object);
                            if (jointDef != null) {
                                create(getMapObjectId(object), jointDef);
                            }
                        }
                    }
                }
            }
        }
    }

    private DefJoint objectToDefJoint(String name, MapObject object){
        Vector2[] points = mapObjectToPoints(object);
        switch (name) {
            case "distance" : return new Distance(points, object.getProperties());
            case "revolute" : return new Revolute(points, object.getProperties());
            case "prismatic" : return new Prismatic(points, object.getProperties());
            case "wheel" : return new Wheel(points, object.getProperties());
            case "motor" : return new Motor(points, object.getProperties());
            case "weld" : return new Weld(points, object.getProperties());
            case "rope" : return new Rope(points, object.getProperties());
            case "friction" : return new Friction(points, object.getProperties());
            case "pulley" : return new Pulley(points, object.getProperties());
            case "mouse" : return new Mouse(points, object.getProperties());
            case "gear" : return new Gear(points, object.getProperties());
            default: return null;
        }
    }

    public void create(String key, DefJoint defj){
        Joint joint;
        if(defj instanceof Gear){
            joint = world.createJoint(((Gear) defj).def(bodies, joints));
        }else {
            joint = world.createJoint(defj.def(bodies));
        }
        addJoints(key, joint);
    }

    private void addJoints(String key, Joint joint){
        if(joints == null){
            joints = new ArrayMap<>();
        }
        joints.put(key, joint);
    }



}
