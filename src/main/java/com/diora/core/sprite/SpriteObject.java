package com.diora.core.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.diora.core.animation.tweens.BaseTween;
import com.diora.core.factory.B2Event;
import com.diora.core.factory.B2FixtureEvent;
import com.diora.core.factory.def.DefBody;
import com.diora.core.factory.def.DefFixture;
import com.diora.core.factory.def.DefSprite;
import com.diora.core.factory.map.CircleObject;
import com.diora.core.factory.map.PolygonObject;
import com.diora.core.factory.map.PolylineObject;
import com.diora.core.factory.map.RectangleObject;
import com.diora.core.factory.map.ShapeObject;
import com.diora.core.factory.model.Model;
import com.diora.core.utils.Global;
import com.diora.core.utils.PropGetter;
import com.diora.core.view.screens.Play;
import com.diora.core.world.RectWorld;

public abstract class SpriteObject {

    public Body body;
    public Sprite sprite;
    public RectWorld rect;
    protected Play play;
    protected MapObject object;
    protected World world;
    protected Fixture fixture;
    protected boolean isDestroyed;
    private PropGetter props;

    public SpriteObject(Play play, MapObject object) {
        this.object = object;
        this.play = play;
        this.world = play.world;
        this.rect = new RectWorld(object);
    }

    protected void onCreate(){
        createBodys();
        createSprites();
        play.spriteManager.add(this);
    }


    /**
     * bodys
     */
    protected DefBody defBody(){
        DefBody defb = DefBody.obtain();
        defb.position.set(rect.getCenter());
        if(has("rotation")){
            float angle = PropGetter.instance(object).getF("rotation");
            Polygon poly = new Polygon();;
            if (object instanceof PolygonMapObject){
                Polygon pol = ((PolygonMapObject) object).getPolygon();
                poly.setVertices(pol.getTransformedVertices());
                poly.setOrigin(pol.getX(), pol.getY());
            }else {
                poly.setVertices(rect.getVertices());
                poly.setOrigin(rect.x,rect.y+rect.height);
                //poly.setVertices(rect.getlocalVertices());
                //poly.setPosition(rect.x, rect.y);
                //poly.setOrigin(0, rect.height);
            }

            poly.setRotation(-angle);
            Rectangle r = poly.getBoundingRectangle();
            defb.position.set(r.getCenter(new Vector2()));
            defb.angle = -angle * MathUtils.degRad;
        }
        return defb;
    }

    protected DefFixture defFixture(){
        return DefFixture.obtain();
    }

    private ShapeObject objectToShape() {
        if (object instanceof RectangleMapObject)
            return new RectangleObject(((RectangleMapObject) object));
         else if (object instanceof EllipseMapObject)
            return new CircleObject(((EllipseMapObject) object));
         else if (object instanceof PolylineMapObject)
            return new PolylineObject(((PolylineMapObject) object));
         else if (object instanceof PolygonMapObject) {
            return new PolygonObject(((PolygonMapObject) object), rect);
        }
         return null;
    }

    protected void createBodys(){
        // body
        DefBody defb = defBody();
        body = world.createBody(defb.def());
        //ToDo check
       // updateRect();
        addBody();
        defb.free();
        // fixtures
        DefFixture deff = defFixture();
        deff.shapeObject = objectToShape();
        deff.create(new B2Event() {
            @Override
            public void OnFixtureDef(FixtureDef fDef) {
                fixture = body.createFixture(fDef);
            }
        });
        fixture.setUserData(this);
        deff.free();
    }

    protected Model createModel (String name){
        Model model = play.game.worldFactory.models.getModel(name);
        model.create(rect, world);
        body = model.getBody("body");
        return model;
    }

    public void activateBody(){
        if(body!=null && !body.isActive())
            body.setActive(true);
    }

    protected void addBody(){
        if(isSelect("add"))
            play.worldCreator.addBody(object, body);
    }


    /**
     * sprites
     */
    protected DefSprite defSprite(){
        DefSprite defs = DefSprite.obtain();
        defs.name = "";
        updateRect();
        defs.bounds = rect;
        defs.body = body;
        return defs;
    }

    protected void createSprites(){
        sprite = createSprite(defSprite());
    }

    protected Sprite createSprite(String nameRegion){
        return createSprite(DefSprite.obtain().setName(nameRegion));
    }

    protected Sprite createSprite(DefSprite defs){
        Sprite sprite = new Sprite();
        sprite.setRegion(play.game.asset.getRegionSprite(defs.name));
        sprite.setBounds(defs.bounds.x, defs.bounds.y, defs.bounds.width, defs.bounds.height);
        sprite.setOriginCenter();
        sprite.setRotation(defs.bounds.angle);
        return sprite;
    }


    /**
     * update
     */
    public void update(float dt){
        updateRect();
        sprite.setPosition(rect.x, rect.y);
        sprite.setRotation(rect.angle);
    }

    public void updateRect(RectWorld rect, Body body){
        Vector2 bodyPos = body.getPosition();
        rect.x = bodyPos.x - rect.halfWidth();
        rect.y = bodyPos.y - rect.halfHeight();
        rect.setRotationFromBody(body);
    }

    public void updateRect(){
        Vector2 bodyPos = body.getPosition();
        rect.x = bodyPos.x - rect.halfWidth();
        rect.y = bodyPos.y - rect.halfHeight();
        rect.setRotationFromBody(body);
    }

    protected void updatePos(){
        Vector2 bodyPos = body.getPosition();
        rect.x = bodyPos.x - rect.halfWidth();
        rect.y = bodyPos.y - rect.halfHeight();
    }

    public void updateSpriteToBody(Sprite sprite){
        sprite.setPosition(rect.x, rect.y);
        sprite.setRotation(rect.angle);
    }

    public void updateSprite(Sprite sprite, Body body){
        Vector2 pos = body.getPosition();
        sprite.setPosition(pos.x-sprite.getWidth()/2, pos.y-sprite.getHeight()/2);
        sprite.setRotation(body.getAngle() * MathUtils.radDeg);
    }

    protected void updateRotation(){
        rect.setRotationFromBody(body);
    }


    /**
     * draw Sprite and other drawable
     */
    public void render(Batch batch){
        sprite.draw(batch);
    };

    public void onHit(){
        Global.log("onHit : ");
    };

    public void onDestroy(){

    };

    public void destroy() {
        isDestroyed = true;
        play.spriteManager.setChanged(true);
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }


    /**
     * others
     */
    public void quereFixtures(Body body ,B2FixtureEvent event){
        for (Fixture fixture : body.getFixtureList()) {
            event.onFixture(fixture);
        }
    }

    public void quereFixtures(B2FixtureEvent event){
        quereFixtures(body, event);
    }

    public void setDensity(float d){
        for (Fixture fixture : body.getFixtureList()) {
            fixture.setDensity(d);
        }
        body.resetMassData();
    }

    protected void setFilters(short bit) {
        setFilters(bit, -1);
    }

    protected void setFilters(short bit , int mask) {
        Filter filter = new Filter();
        filter.categoryBits = bit;
        filter.maskBits = ((short) mask);
        quereFixtures((Fixture f)->{
            f.setUserData(SpriteObject.this);
            f.setFilterData(filter);
        });
    }

    protected  void setFilter(Fixture fixture , short bit) {
        setFilter(fixture, bit, -1);
    }

    protected  void setFilter(short bit) {
        setFilter(bit, -1);
    }

    protected void setFilter(short bit , int mask) {
        setFilter(fixture, bit, mask);
    }

    protected void setFilter(Fixture fixture,short bit , int mask) {
        if(fixture != null) {
            Filter filter = new Filter();
            filter.categoryBits = bit;
            filter.maskBits = ((short) mask);
            fixture.setFilterData(filter);
            fixture.setUserData(this);
        }else {
            Gdx.app.error("setFiltre", "error fixture is null");
        }
    }

    protected float[] getVertices(){
        if(object instanceof PolylineMapObject)
            return ((PolylineMapObject) object).getPolyline().getTransformedVertices();
        else if(object instanceof PolygonMapObject) {
            return ((PolygonMapObject) object).getPolygon().getTransformedVertices();
        }
        else if(object instanceof RectangleMapObject) {
            RectWorld rectWorld = new RectWorld(((RectangleMapObject) object).getRectangle());
            rectWorld.setPostition(0,0);
            rectWorld.translate(-rectWorld.halfWidth(),-rectWorld.halfHeight());
            return rectWorld.getVertices();
        }
        return null;
    }

    public Fixture getFixture() {
        return fixture;
    }

    protected float bodyToSpriteRotation(){
        return body.getAngle() * MathUtils.radDeg;
    }

    protected float bodyToSpriteRotation(Body body){
        return body.getAngle() * MathUtils.radDeg;
    }

    protected  float getEllipseDensity( int mass){
        float r = rect.halfWidth();
        return mass / (3.14f * r * r) ;
    }

    protected  float getRectDensity( int mass){
        return mass / rect.width*rect.height ;
    }

    protected boolean isThere(int[] tab,int num){
        boolean there = false;
        for (int i : tab) {
            if (i == num){
                there = true;
                break;
            }
        }
        return there;
    }

    protected boolean isSelect(String key){
        return object.getProperties().containsKey(key) &&
                object.getProperties().get(key,Boolean.class);
    }

    protected boolean has(String prop){
        return object.getProperties().containsKey(prop);
    }

    protected PropGetter getProps(){
        if(props!=null){
            return props;
        }else {
            props = new PropGetter(object.getProperties());
            return props;
        }
    }


    /**
     * clean
     */

    protected  TiledMapTileLayer.Cell getCell (String index){
        TiledMapTileLayer Layer = (TiledMapTileLayer) play.sc.map.getLayers().get(index);
        return Layer.getCell((int)(body.getPosition().x *play.game.ppm /64),
                (int)(body.getPosition().y*play.game.ppm /64));

    }

    public Vector2 posBodyToSprite(){
        //TODO
        return null;
    }

    public Vector2 posSpriteToBody(){
        //TODO
        return null;
    }

    Animation<TextureRegion> getAnim(int num, String name){
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.clear();
        for (int i = 1; i <= num; i++)
            frames.add(play.game.asset.getRegion("sprite",name+i));
        return new Animation<TextureRegion>(0.1f, frames);
    }

    protected void killAnim(BaseTween baseTween){
        if(baseTween != null) {
            baseTween.kill();
        }
    }

    public void destroyBody(){
        if(body!=null){
            world.destroyBody(body);
            body = null;
        }
    }

}

