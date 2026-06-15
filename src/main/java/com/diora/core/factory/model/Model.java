package com.diora.core.factory.model;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.diora.core.Game;
import com.diora.core.factory.B2Event;
import com.diora.core.factory.def.DefBody;
import com.diora.core.factory.def.DefFixture;
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
import com.diora.core.factory.map.CircleObject;
import com.diora.core.factory.map.PolygonObject;
import com.diora.core.factory.map.PolylineObject;
import com.diora.core.factory.map.RectangleObject;
import com.diora.core.tool.exception.ErrorException;
import com.diora.core.utils.Global;
import com.diora.core.utils.PropGetter;
import com.diora.core.utils.maths.Clipper;
import com.diora.core.world.RectWorld;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.diora.core.utils.Global.getMapLayerId;
import static com.diora.core.utils.Global.getMapObjectId;
import static com.diora.core.utils.Global.mapObjectToPoints;
import static com.diora.core.utils.Global.pointssToVtss;
import static com.diora.core.utils.Global.vtsToPoints;

public class Model implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private Vector2 size;
    private RectWorld rectModel;
    private HashMap<String, DefBody> defBodies;
    private HashMap<String, HashMap<String, DefFixture>> defFixtures;
    private LinkedHashMap<String, DefJoint> defJoints;
    private transient World world;
    private transient ArrayMap<String, Body> bodies;
    private transient ArrayMap<String, Fixture> fixtures;
    private transient ArrayMap<String, Joint> joints;

    public Model(String name, TiledMap map) {
        this.name = name;
        size = new Vector2(Global.boundMap(map)).scl(1/ Game.GAME.ppm);
        rectModel = new RectWorld(0,0, size);
        defBodies = new HashMap<>();
        defFixtures = new HashMap<>();
        defJoints = new LinkedHashMap<>();
        // init bodys and fixturex
        for (MapLayer layer : map.getLayers()) {
            if(layer instanceof MapGroupLayer || layer instanceof TiledMapImageLayer || !layer.isVisible()) continue;
            String bodyId = getMapLayerId(layer);
            DefBody defBody = layerToDefBody(layer);
            defBodies.put(bodyId, defBody);

            HashMap<String, DefFixture> defFixture = new HashMap<>();
            for (MapObject object : layer.getObjects()) {
                if(object.isVisible()){
                    DefFixture defFix = objectToDefFixture(object, defBody);
                    defFixture.put(getMapObjectId(object), defFix);
                }
            }
            defFixtures.put(bodyId, defFixture);
        }
        // init joints def
        MapGroupLayer joints = ((MapGroupLayer) map.getLayers().get("joint"));
        if(joints!=null) {
            for (MapLayer layer : joints.getLayers()) {
                if (layer.isVisible()) {
                    String nameJoint = layer.getName();
                    for (MapObject object : layer.getObjects()) {
                        if (object.isVisible()) {
                            DefJoint jointDef = objectToDefJoint(nameJoint, object);
                            if (jointDef != null) {
                                jointDef.applyLocal(this);
                                defJoints.put(getMapObjectId(object), jointDef);
                            }
                        }
                    }
                }
            }
        }
    }

    private Vector2 getCenter(MapObject object){
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        return new Vector2(rect.x/ Game.GAME.ppm - size.x/2f, rect.y/ Game.GAME.ppm - size.y/2f);
    }

    private DefBody layerToDefBody(MapLayer layer){
        PropGetter prop = new PropGetter(layer.getProperties());
        DefBody defb = new DefBody();
        MapObject firstObject = null;
        try {
            firstObject  = layer.getObjects().get(0);
            if(firstObject.getName()!=null && firstObject.getName().equals("center")){
                defb.position.set(getCenter(firstObject));
                firstObject.setVisible(false);
            }
        }catch (Exception e){
            e.getCause().printStackTrace();
        }
        defb.type = BodyDef.BodyType.values()[prop.getI("type",0)];
        defb.angle = prop.getF("angle",0);
        defb.linearVelocity.set(prop.getF("linearVelocityX",0), prop.getF("linearVelocityY",0));
        defb.angularVelocity = prop.getF("angularVelocity",0);
        defb.linearDamping = prop.getF("linearDamping",0);
        defb.angularDamping = prop.getF("angularDamping",0);
        defb.allowSleep = prop.getB("allowSleep",true);
        defb.awake = prop.getB("awake",true);
        defb.fixedRotation = prop.getB("fixedRotation",false);
        defb.bullet = prop.getB("bullet",false);
        defb.active = prop.getB("active",true);
        defb.gravityScale = prop.getF("gravityScale",1);
        return defb;
    }

    private DefFixture objectToDefFixture(MapObject object, DefBody defBody){
        PropGetter prop = new PropGetter(object.getProperties());
        DefFixture deff = new DefFixture();
        deff.friction = prop.getF("friction");
        deff.restitution = prop.getF("restitution");
        deff.density = prop.getF("density");
        deff.isSensor = prop.getB("isSensor",false);
        deff.categoryBits = prop.getShort("categoryBits",1);
        deff.maskBits = prop.getShort("maskBits",-1);
        deff.groupIndex = prop.getShort("groupIndex");
        float tx = - size.x / 2f - defBody.position.x;
        float ty = -size.y / 2f - defBody.position.y;
        if (object instanceof RectangleMapObject) {
            RectangleObject rectangleObject = new RectangleObject((RectangleMapObject) object);
            rectangleObject.rect.translate(tx, ty);
            deff.shapeObject = rectangleObject;
        } else if (object instanceof EllipseMapObject) {
            CircleObject circleObject = new CircleObject((EllipseMapObject) object);
            circleObject.rect.translate(tx, ty);
            deff.shapeObject = circleObject;
        } else if (object instanceof PolylineMapObject) {
            PolylineObject line = new PolylineObject((PolylineMapObject) object);
            line.polyline.setPosition(line.polyline.getX() / Game.GAME.ppm, line.polyline.getY() / Game.GAME.ppm);
            line.polyline.setScale(1/Game.GAME.ppm, 1/Game.GAME.ppm);
            line.polyline.translate(tx, ty);
            line.localVertices = line.polyline.getTransformedVertices();
            deff.shapeObject = line;
        } else if (object instanceof PolygonMapObject) {
            PolygonObject poly = new PolygonObject((PolygonMapObject) object);
            poly.polygon.setPosition(poly.polygon.getX() / Game.GAME.ppm, poly.polygon.getY() / Game.GAME.ppm);
            poly.polygon.setScale(1/Game.GAME.ppm, 1/Game.GAME.ppm);
            poly.polygon.translate(tx, ty);
            Vector2[][] polygons = Clipper.polygonize(Clipper.Polygonizer.BAYAZIT, vtsToPoints(poly.polygon.getTransformedVertices()));
            poly.polygons = pointssToVtss(polygons);
            deff.shapeObject = poly;
        }
        return deff;
    }

    private DefJoint objectToDefJoint(String name, MapObject object) {
        Vector2[] points = mapObjectToPoints(object, rectModel);
        switch (name) {
            case "distance":
                return new Distance(points, object.getProperties());
            case "revolute":
                return new Revolute(points, object.getProperties());
            case "prismatic":
                return new Prismatic(points, object.getProperties());
            case "wheel":
                return new Wheel(points, object.getProperties());
            case "motor":
                return new Motor(points, object.getProperties());
            case "weld":
                return new Weld(points, object.getProperties());
            case "rope":
                return new Rope(points, object.getProperties());
            case "friction":
                return new Friction(points, object.getProperties());
            case "pulley":
                return new Pulley(points, object.getProperties());
            case "mouse":
                return new Mouse(points, object.getProperties());
            case "gear":
                return new Gear(points, object.getProperties());
            default:
                return null;
        }
    }

    public float getScale(RectWorld model){
        return model.width / size.x;
    }

    public void create(RectWorld model, World world){
        setWorld(world);
        create(model);
    }

    public void create(RectWorld model){
        float scale = model.width / size.x;
        if(scale <= 0)
            throw  new ErrorException("scale <= 0 :"+scale);
        //create bodys
        for (Map.Entry<String, DefBody> bodyEntry : defBodies.entrySet()) {
            DefBody  defb = bodyEntry.getValue();
            BodyDef bDef = defb.def();
            bDef.position.set(model.getCenter().add(defb.position.cpy().scl(scale)));
            Body body = world.createBody(bDef);
            addBody(bodyEntry.getKey(), body);
            //create fixtures for every body
            for (Map.Entry<String, DefFixture> fixtureEntry : defFixtures.get(bodyEntry.getKey()).entrySet()) {
                DefFixture deff = fixtureEntry.getValue();
                deff.create(scale, fDef -> addFixture(fixtureEntry.getKey(), body.createFixture(fDef)));
            }
        }
        //create joints
        for (Map.Entry<String, DefJoint> jointEntry : defJoints.entrySet()) {
            DefJoint defj =  jointEntry.getValue();
            Joint joint;
            if(defj instanceof Gear){
                joint = world.createJoint(((Gear) defj).def(bodies, joints));
            }else {
                joint = world.createJoint(defj.def(bodies, scale));
            }
            addJoints(jointEntry.getKey(), joint);
        }
    }

    public void createFixture(String bKey, String fKey, float width, B2Event event){
        float scale = width / size.x;
        DefFixture deff = defFixtures.get(bKey).get(fKey);
        deff.create(scale, event);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Body getBody(String key){
        return bodies.get(key);
    }

    public Fixture getFixture(String key){
        return fixtures.get(key);
    }

    public <T> T getJoint(Class<T> type, String key){
        return type.cast(joints.get(key));
    }

    void addBody(String key, Body body){
        if(bodies== null){
            bodies = new ArrayMap<>();
        }
        bodies.put(key, body);
    }

    void addFixture(String key, Fixture fixture){
        if(fixtures== null){
            fixtures = new ArrayMap<>();
        }
        fixtures.put(key, fixture);
    }

    void addJoints(String key, Joint joint){
        if(joints == null){
            joints = new ArrayMap<>();
        }
        joints.put(key, joint);
    }

    public DefBody getDefBody(String key){
        return defBodies.get(key);
    }
}
