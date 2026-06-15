package com.diora.core.stage.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.diora.core.stage.StageCreator;
import com.diora.core.tool.exception.PropNotFound;

public abstract class TileActor {

    protected StageCreator sc;
    protected MapObject object;
    protected MapProperties props;
    protected Skin skin;
    private String actorId;
    private Actor actor;
    private boolean screenView;
    private Group parent;

    public TileActor(StageCreator stageCreator,MapObject object, String actorId, Group parent) {
        this.skin = stageCreator.game.asset.skin;
        this.actorId = actorId;
        this.sc = stageCreator;
        this.object = object;
        this.props = object.getProperties();
        this.actor = null;
        this.parent = parent;
        defActor();
    }

    protected abstract void defActor();

    public void setStyle(Actor actor){
        this.actor = actor;
        this.actor.setName(getActorName());
        designIt(this.actor);
        boundsIt(this.actor);
        addToStage(this.actor);
    }

    public void designIt(Actor actor){
        if(has("color")) actor.setColor(getC("color"));
        if(has("alpha")) actor.getColor().a = getF("alpha");
        if(has("angle")) actor.setRotation(-getF("angle"));
        if(has("scale")) actor.setScale(getF("scale"));
    }

    public void boundsIt(Actor actor){
        Rectangle bounds = ((RectangleMapObject) object).getRectangle();
        screenView = isSelect("screen");
        if(screenView){
            sc.screenStage.setBounds(actor, bounds, getfontScale(), isSelect("relative"));
            return;
        }
        actor.setPosition(bounds.getX(),bounds.getY());
        actor.setSize(bounds.width, bounds.height);
        actor.setOrigin(bounds.width/2,bounds.height/2);
    }

    private void addToStage(Actor actor){
        if(isSelect("notInStage")) return;
        if(has("outStage")){
            sc.initOutStage();
            sc.outStage.add(actor);
            return;
        }
        if(has("group")){
            Group group = sc.get(Group.class,props.get("group",String.class));
            actor.moveBy(-group.getX(), -group.getY());
            group.addActor(actor);
            return;
        }
        if(has("table")){
            Table table = sc.get(Table.class,props.get("table",String.class));
            actor.moveBy(-table.getX(), -table.getY());
            table.addActor(actor);
            return;
        }
        if(parent!=null){
            parent.addActor(actor);
        }else {
            (screenView ? sc.screenStage : sc.stage).addActor(actor);
        }
    }

    protected String getActorName(){
       return actorId != null ? actorId :
                (object.getName()!=null ? object.getName() : ""+object.getProperties().get("id",Integer.class));
    }

    public Actor getActor() {
        return actor;
    }

    protected String getStyleName(){
        boolean isDefault = !object.getProperties().containsKey("style");
        if(isDefault) return "default";
        else {
            String style = object.getProperties().get("style", String.class);
            if (style.isEmpty()) return "default";
            return style;
        }
    }

    protected String getText() {
        boolean isEmpty = !object.getProperties().containsKey("text");
        return isEmpty ? "" : object.getProperties().get("text", String.class);
    }

    protected float getfontScale(){
        if(has("fontScale")){
            return getF("fontScale");
        }else{
            return 1.0F;
        }
    }


    protected void propMustfound(String name){
        if(!object.getProperties().containsKey(name))
            throw new PropNotFound(name);
    }

    protected boolean isSelect(String key){
        return props.containsKey(key) &&
                props.get(key,Boolean.class);
    }

    protected boolean has(String prop){
        return object.getProperties().containsKey(prop);
    }

    protected float getF(String s){
        return props.get(s, Float.class);
    }
    protected String getS(String s) {
        return props.get(s, String.class);
    }
    protected boolean getB(String s){
        return props.get(s, Boolean.class);
    }
    protected Color getC(String s){
        return props.get(s, Color.class);
    }

}
