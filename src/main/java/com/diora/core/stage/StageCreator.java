package com.diora.core.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.diora.core.Game;
import com.diora.core.animation.tweens.BaseTween;
import com.diora.core.animation.tweens.Timeline;
import com.diora.core.animation.tweens.Tween;
import com.diora.core.animation.tweens.TweenCallback;
import com.diora.core.animation.tweens.TweenEquations;
import com.diora.core.animation.tweens.TweenManager;
import com.diora.core.animation.tweens.accessors.ActorAccessor;
import com.diora.core.stage.actor.Background;
import com.diora.core.stage.actor.CheckBoxImage;
import com.diora.core.stage.object.TileActor;
import com.diora.core.stage.ui.UiUtils;
import com.diora.core.utils.Global;

public class StageCreator implements Disposable{

    public Game game;
    public OrthogonalTiledMapRenderer renderer;
    public TiledMap map,uiMap;
    public Stage stage;
    public ScreenStage screenStage;
    public TweenManager tweenManager;
    public Background background;
    public Array<Actor> outStage;

    public InputProcessor lastInputProcessor;
    public ArrayMap<String, TextTooltip> tooltips;
    private UiUtils uiUtils;
    public float worldWidth, worldHeight;
    public boolean isCenter;
    static {
        Tween.registerAccessor(Actor.class, new ActorAccessor());
    }

    public StageCreator(Game game, String mapPath) {
        this(game, new TmxMapLoader().load(mapPath+".tmx"));
        createActors();
    }

    public StageCreator(Game game, TiledMap map) {
        this.game = game;
        this.map = map;
        Vector2 size = Global.boundMap(map);
        worldWidth = size.x; worldHeight = size.y;
        stage = new Stage(new FitViewport(worldWidth, worldHeight), game.batch);
        screenStage = new ScreenStage(worldWidth, worldHeight ,game.batch);
        background = getBackground(map);
        tweenManager = new TweenManager();
    }

    public StageCreator(Game game, TiledMap map, float ppm) {
        this.game = game;
        this.map = map;
        Vector2 size = Global.boundMap(map);
        worldWidth = size.x; worldHeight = size.y;
        stage = new Stage(new FitViewport(worldWidth/ppm, worldHeight/ppm), game.batch);
        screenStage = new ScreenStage(worldWidth, worldHeight ,game.batch);
        background = getBackground(map);
        tweenManager = new TweenManager();
    }

    public UiUtils getUtils(){
        if(uiUtils == null)
            uiUtils = new UiUtils();
        return uiUtils;
    }

    public void intiUiMap(){
        if(uiMap == null)
            uiMap = new TmxMapLoader().load("tmx/ui.tmx");
    }

    public void initMapRender(){
        renderer = new OrthogonalTiledMapRenderer(map,1f, game.batch);
    }

    public void initMapRender(float scale){
        renderer = new OrthogonalTiledMapRenderer(map,1f/scale, game.batch);
    }

    public void createActors(){
        mapToActors(map);
    }

    public void initOutStage(){
        if(outStage == null){
            outStage = new Array<>();
        }
    }

    public void initToolTipMap(){
        if(tooltips == null){
            tooltips = new ArrayMap<>();
        }
    }

    public TextTooltip getToolTip(String s){
        return tooltips.get(s);
    }

    public Background getBackground(TiledMap map){
        Background background = null;
        if(map.getProperties().containsKey("color")) {
            background = new Background(game.batch, map.getProperties().get("color", Color.class));
        } else if(map.getProperties().containsKey("texture"))
            background = new Background(game.batch, new Texture("ui/texture/"+map.getProperties().get("texture", String.class)));
        else if (map.getProperties().containsKey("region")) {
            TextureRegion region = game.asset.getRegion("ui",map.getProperties().get("region", String.class));
            background = new Background(game.batch, region);
        }
        return background;
    }

    private Actor newActorInstance(Class<? extends TileActor> tileActorClass, MapObject object, String actorId, Group parent){
        if(tileActorClass == null) return null;
        TileActor tileActor = null;
        try {tileActor =  tileActorClass
                .getConstructor (StageCreator.class,MapObject.class,String.class,Group.class)
                .newInstance (this,object,actorId,parent);
        }catch (Exception e) {
            e.getCause().printStackTrace();
            Gdx.app.error("StageCreator fix it",""+object.getName()+" "+e.getCause().getMessage());
        }
        return tileActor!=null?tileActor.getActor():null;
    }

    public void mapToActors(String mapPath){
        TiledMap map = new TmxMapLoader().load(mapPath+".tmx");
        mapToActors(map);
    }

    public void mapToActors(TiledMap map){
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            layerToActors(map.getLayers().get(i));
        }
    }

    public void layerToActors(MapLayer layer){
        ActorRef.ActorDef actorDef = ActorRef.layerToTileActor(layer.getName());
        for (MapObject object : layer.getObjects()) {
            if(object.isVisible() && object instanceof RectangleMapObject){
                newActorInstance(actorDef.classDef, object, null, null);
            }
        }
    }

    public void mapToGroup(TiledMap map, Group group){
        for (int i = 0; i < map.getLayers().getCount(); i++) {
            MapLayer layer = map.getLayers().get(i);
            String nameLayer = layer.getName();
            ActorRef.ActorDef actorDef = ActorRef.layerToTileActor(nameLayer);
            for (MapObject object : layer.getObjects()) {
                if(object.isVisible() && object instanceof RectangleMapObject){
                    Actor actor = newActorInstance(actorDef.classDef, object, null, group);
                }
            }
        }
    }

    public Rectangle getRectangle(String layer, String object){
        return ((RectangleMapObject) map.getLayers().get(layer).getObjects().get(object)).getRectangle();
    }

    public Rectangle toRectangle(TiledMap map){
        int w = map.getProperties().get("width",Integer.class) * map.getProperties().get("tilewidth",Integer.class);
        int h = map.getProperties().get("height",Integer.class) * map.getProperties().get("tileheight",Integer.class);
        return new Rectangle(0,0,w,h);
    }

    public void bound(Actor actor, Rectangle rect){
        actor.setBounds(rect.x, rect.y, rect.width, rect.height);
        actor.setOrigin(rect.width/2, rect.height/2);
    }

    public void bound(Actor actor, TiledMap map){
        bound(actor, toRectangle(map));
    }

    public <T> T createActor(Class<T> actorClass, String nameObject){
        return createActor(actorClass, nameObject, null, map);
    }

    public <T> T createActor(Class<T> actorClass, String nameObject, String actorId){
        return createActor(actorClass, nameObject ,actorId, map);
    }

    public <T> T createActor(Class<T> actorClass, String nameObject, String actorId, String pathMap){
        TiledMap map = new TmxMapLoader().load(pathMap+".tmx");
        T actor = createActor(actorClass, nameObject , actorId, map);
        map.dispose();
        return actor;
    }

    public <T> T createActor(Class<T> actorClass, String nameObject, String actorId, TiledMap map){
        ActorRef.ActorDef def = ActorRef.classToTileActor(actorClass);
        if(def != null) {
            Actor actor = newActorInstance(def.classDef, map.getLayers().get(def.name).getObjects().get(nameObject), actorId, null);
            return actorClass.cast(actor);
        }else {
            return null;
        }
    }


    public void addActorDef(ActorRef.ActorDef actorDef){
        if(ActorRef.actorRefs == null)
            ActorRef.actorRefs = new Array<>();
        ActorRef.actorRefs.add(actorDef);
    }

    public Vector2 getPoint(String name){
        MapLayer layer = map.getLayers().get("point");
        if(layer == null) return null;
        MapObject mapObject = map.getLayers().get("point").getObjects().get(name);
        if(mapObject == null){
            Gdx.app.error("sc.getPoint","point "+name+" not found");
            return null;
        }
        return ((RectangleMapObject) mapObject).getRectangle().getPosition(new Vector2());
    }

    public Vector2 getPoint(String name, TiledMap map){
        Vector2 point = new Vector2(0,0);
        MapLayer layer = map.getLayers().get("point");
        if(layer == null){
            Gdx.app.error("sc.getPoint","point "+name+" not found");
            return point;
        }
        MapObject mapObject = map.getLayers().get("point").getObjects().get(name);
        if(mapObject == null){
            Gdx.app.error("sc.getPoint","point "+name+" not found");
            return point;
        }
        ((RectangleMapObject) mapObject).getRectangle().getPosition(point);
        return point;
    }

    public OrthographicCamera getCam(){
        return ((OrthographicCamera) stage.getCamera());
    }

    public Color hex4Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 )/255.0f,
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 )/255.0f,
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 )/255.0f,1.0f );
    }

    public void clickOn(String name){
        final Actor actor = get(Actor.class,name);
        InputEvent inputEvent = new InputEvent();
        inputEvent.setType(InputEvent.Type.enter);
        actor.fire(inputEvent);
    }

    public <T> T getProp(String name,Class<T> tClass){
        return map.getProperties().containsKey(name)?map.getProperties().get(name,tClass):null;
    }

    public <T> T getIn(Class<T> type,String name, Group group){
        return type.cast(group.findActor(name));
    }

    public <T> T get(Class<T> type,String name){
        return type.cast(findActor(name));
    }

    public <T> T getOut(Class<T> type,String name){
        return type.cast(findOutStage(name));
    }

    public Actor get(String name){
        return findActor(name);
    }

    public void onShow(){
        // updatePrefs Listener and start animation
        Gdx.input.setInputProcessor(getInput());
    }

    public InputProcessor getInput(){
        return new InputMultiplexer(screenStage, stage);
    }

    public void update(float dt){
        tweenManager.update(dt);
        stage.act(dt);
        screenStage.act(dt);
        //if(renderer != null) renderer.setView(camera);
    }

    public void drawWithScreenFirst(float dt){
        if(background!=null) background.draw(dt);
        screenStage.getViewport().apply(true);
        screenStage.render();
        stage.getViewport().apply(isCenter);
        if(renderer != null){
            renderer.setView(getCam());
            renderer.render();
        }
        stage.draw();

        screenStage.getViewport().apply(true);
        screenStage.drawWin();
    }

    public void draw(float dt){
        if(background!=null) background.draw(dt);

        stage.getViewport().apply(isCenter);
        if(renderer != null){
            renderer.setView(getCam());
            renderer.render();
        }
        stage.draw();
        screenStage.getViewport().apply(true);
        screenStage.draw();
    }

    public void drawBack(float dt){
        if(background!=null) background.draw(dt);
    }

    public void drawRender(){
        stage.getViewport().apply(isCenter);
        if(renderer != null){
            renderer.setView(getCam());
            renderer.render();
        }
    }

    public void drawRender(OrthographicCamera cam){
        if(renderer != null){
            renderer.setView(cam);
            renderer.render();
        }
    }

    public void drawStage(){
        stage.getViewport().apply(isCenter);
        stage.draw();
        screenStage.getViewport().apply(true);
        screenStage.draw();
    }

    public void resize(int width, int height){
        if(background != null) background.resize(width, height);
        stage.getViewport().update(width, height, isCenter);
        screenStage.resize(width, height);
    }

    public void disposeActor(Actor actor){
        if(actor != null){
            actor.clear();
            actor.remove();
        }
        else {
            Gdx.app.log("actor not disposed ","actor == null");
        }
    }

    private Actor findActor(String name){
        Actor actor = stage.getRoot().findActor(name);
        if(screenStage !=null && actor == null)
             actor = screenStage.getRoot().findActor(name);
        if(outStage!=null && actor == null)
            actor = findOutStage(name);
        if(actor == null)
            Gdx.app.error("Fix it ","actor not found : "+name);
        return actor;
    }

    public Actor findOutStage(String name){
        for (int i = 0; i < outStage.size; i++) {
            Actor actor = outStage.get(i);
            if(actor.getName().equals(name)){
                return actor;
            }
        }
        for (int i = 0; i < outStage.size; i++) {
            Actor child = outStage.get(i);
            if(child instanceof  Group){
                Actor actor = ((Group) child).findActor(name);
                if(actor!=null) return actor;
            }
        }
        return null;
    }

    public void disposeActor(String name){
        Actor actor = findActor(name);
        if(actor == null){
            Gdx.app.log("actor not disposed ","actor not found "+name);
        }else {
            disposeActor(actor);
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        screenStage.dispose();
        map.dispose();
        tweenManager.killAll();
        if(renderer!=null) renderer.dispose();
        if(background!=null) background.dispose();
        if(outStage!=null){
            for (Actor actor : outStage) {
                actor.clear();
                actor.remove();
            }
        }
        if(uiUtils!=null)
            uiUtils.dispose();
    }

    // Animaation

    public void clicked(Actor actor, TweenManager tweenManager, final Runnable runnable){
        int tweenType = ActorAccessor.SCALE_XY;
        if(actor instanceof Label) tweenType = ActorAccessor.FONT_SCALE_XY;
        Timeline.createSequence()
                .push(Tween.to(actor, tweenType,0.05f).target(1.1f,1.1f).ease(TweenEquations.easeOutExpo))
                .push(Tween.to(actor, tweenType,0.05f).target(1.0f,1.0f).ease(TweenEquations.easeOutElastic))
                .pushPause(0.01f)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        if(type == COMPLETE){
                            runnable.run();
                        }
                    }
                }).start(tweenManager);
    }

    public void pop(String name){
        pop(get(name));
    }
    public void pop(Actor actor){
        Timeline.createParallel()
                .repeatYoyo(Tween.INFINITY,0.0f)
                .push(Tween.to(actor, ActorAccessor.SCALE_XY,1.4f).target(1.1f,1.1f).ease(TweenEquations.easeOutCirc))
                .start(tweenManager);
    }

    // Listener

    public void addListenerIn(Group group, String name, final Runnable runnable){
        Actor actor = getIn(Actor.class,name,group);
        if (actor!=null)
            addListener(actor, runnable);
    }

    public void addListener(String name, final Runnable runnable){
        Actor actor = get(Actor.class,name);
        if (actor!=null)
            addListener(actor, runnable);
    }

    public void addListener(final Actor actor, final Runnable rightClick, final Runnable leftClick){
        actor.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(button == 0)
                    rightClick.run();
                else if(button == 1)
                    leftClick.run();
            }
        });
    }

    public void addListener(final Actor actor, final Runnable runnable){
        if(actor == null){
            Gdx.app.error("Fix it ","addListener to null Actor !");
            return;
        }
        actor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.dj.playClickedActorSound();
                runnable.run();
            }
        });
    }

    public Label getLabel(String name){
        return get(Label.class, name);
    }

    public Button getButton(String name){
        return get(Button.class, name);
    }

    public TextButton getTextButton(String name){
        return get(TextButton.class, name);
    }

    public Image getImage(String name){
        return get(Image.class, name);
    }

    public CheckBoxImage getCheckBoxImage(String name){
        return get(CheckBoxImage.class, name);
    }

    public Group getGroup(String name){
        return get(Group.class, name);
    }
}
