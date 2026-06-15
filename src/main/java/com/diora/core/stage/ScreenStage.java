package com.diora.core.stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.diora.core.stage.actor.ImageTimer;
import com.diora.core.view.window.Window;

public class ScreenStage extends Stage {

    //TODO fullx par appor worldx
    public float worldWidth, worldHeight;
    public int screenWidth, screenHeight;
    public Array<Window> windows;
    float unit;

    public ScreenStage(float worldWidth, float worldHeight, Batch batch) {
        super(new ScreenViewport(), batch);
        this.screenWidth = getViewport().getScreenWidth();
        this.screenHeight = getViewport().getScreenHeight();
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        windows = new Array<>();
        updateUnit();
    }

    void updateUnit(){
        unit = screenWidth / worldWidth < screenHeight / worldHeight ?
                screenWidth / worldWidth :
                screenHeight / worldHeight;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (Window window : windows) {
            window.update(delta);
        }
    }

    @Override
    public void draw() {
        super.draw();
        for (Window window : windows) {
            window.draw(getBatch());
        }
    }

    public void render(){
        super.draw();
    }

    public void drawWin(){
        for (Window window : windows) {
            window.draw(getBatch());
        }
    }

    public void resize(int width, int height){
        if(width!=screenWidth || height!=screenHeight) {
            getViewport().update(width, height, true);
            screenWidth = width;
            screenHeight = height;
            updateUnit();
            layoutChildren(getRoot());
            for (Window window : windows) {
                window.resize();
            }
        }
    }

    @Override
    public void addActor(Actor actor) {
        layoutActor(actor);
        super.addActor(actor);
    }

    private void layoutActor(Actor actor, Bounds bounds){
        if(bounds!=null) {
            if (actor instanceof Label) {
                Label label = ((Label) actor);
                label.setFontScale(getscale(bounds.fScale));
            } else if (actor instanceof TextButton) {
                TextButton textButton = ((TextButton) actor);
                textButton.getLabel().setFontScale(getscale(bounds.fScale));
            } else if (actor instanceof ImageTimer) {
                ImageTimer imageTimer = ((ImageTimer) actor);
                imageTimer.getNumber().setFontScale(getscale(bounds.fScale));
            }
            if (actor instanceof Group) {
                Group group = ((Group) actor);
                layoutChildren(group);
            }
        }
    }

    private void layoutActor(Actor actor){
        Bounds bounds = ((Bounds) actor.getUserObject());
        layoutActor(actor, bounds);
    }

    private float getscale(float defaultScale){
        return defaultScale * unit;
    }

    private void layoutChildren(Group group){
        SnapshotArray<Actor> children = group.getChildren();
        Actor[] actors = children.begin();
        for (int i = 0, n = children.size; i < n; i++){
            Actor child = actors[i];
            updateBounds(child);
            layoutActor(child);
        }
        children.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        for (Window window : windows) {
            window.dispose();
        }
    }

    public void updateBounds(Actor actor) {
        Bounds bounds = ((Bounds) actor.getUserObject());
        if(bounds!=null){
            bounds.resize();
            actor.setBounds(bounds.screenDesign.x,bounds.screenDesign.y,bounds.screenDesign.width,bounds.screenDesign.height);
            actor.setOrigin(bounds.screenDesign.width / 2.0F, bounds.screenDesign.height / 2.0F);
            if(actor.getParent()!=null){
                actor.moveBy(-actor.getParent().getX(), -actor.getParent().getY());
            }
        }
    }

    public void setBounds(Actor actor, Rectangle rect){
        Bounds bounds = new Bounds(rect, 1.0F, false);
        actor.setUserObject(bounds);
        bounds.resize();
        actor.setBounds(bounds.screenDesign.x,bounds.screenDesign.y,bounds.screenDesign.width,bounds.screenDesign.height);
        actor.setOrigin(bounds.screenDesign.width / 2.0F, bounds.screenDesign.height / 2.0F);
    }

    public void setBounds(Actor actor, Rectangle rect, float fScale, boolean isRelative){
     Bounds bounds = new Bounds(rect, fScale, isRelative);
     actor.setUserObject(bounds);
     bounds.resize();
     actor.setBounds(bounds.screenDesign.x,bounds.screenDesign.y,bounds.screenDesign.width,bounds.screenDesign.height);
     actor.setOrigin(bounds.screenDesign.width / 2.0F, bounds.screenDesign.height / 2.0F);
     layoutActor(actor, bounds);
    }

    public class Bounds{
        static final int none = 0;
        static final int down = 2;
        static final int up = 4;
        static final int left = 8;
        static final int right = 16;
        static final int centerH = 32;
        static final int centerV = 64;
        static final int fullX = 128;
        static final int fullY = 256;
        Rectangle mapDesign, screenDesign;
        Rectangle world ,screen;
        Pad pad;
        float fScale;
        boolean isRelative;
        int align = none;
        int inlign = none;

        Bounds(Rectangle mapDesign, float fScale, boolean isRelative) {
            this.mapDesign = mapDesign;
            this.fScale = fScale;
            this.isRelative = isRelative;
            this.screenDesign = new Rectangle(mapDesign);
            world = new Rectangle(0,0,worldWidth/2F,worldHeight/2F);
            screen = new Rectangle(0,0,screenWidth/2F,screenHeight/2F);
            align = updateFlag(0, 0, worldWidth, worldHeight);
            Vector2 designCenter = new Vector2();
            mapDesign.getCenter(designCenter);
            if(mapDesign.width == worldWidth || mapDesign.height ==  worldHeight){
                this.isRelative = true;
            }
            if(designCenter.x == worldWidth/2f || designCenter.y == worldHeight/2F){
                this.isRelative = true;
            }
            if(this.isRelative){
                world.setSize(worldWidth, worldHeight);
                screen.setSize(screenWidth, screenHeight);
            }
            if((align & right) == right){
                world.setX(world.width);
            }
            if((align & up) == up){
                world.setY(world.height);
            }
            pad = new Pad();
            inlign = updateFlag(world);
            inlign = inlign | (designCenter.x == world.x+world.width/2F ? centerH : none);
            inlign = inlign | (designCenter.y == world.y+world.height/2F ? centerV : none);
            inlign = inlign | (mapDesign.width == world.width ? fullX : none);
            inlign = inlign | (mapDesign.height == world.height ? fullY : none);
        }

        public void setCenterH(){
            inlign = inlign | centerH;
        }

        public void setRelative(boolean relative) {
            isRelative = relative;
        }

        int updateFlag(Rectangle rect){
            return updateFlag(rect.x, rect.y, rect.width, rect.height);
        }

        int updateFlag(float x, float y, float width, float height){
            int alignment = 0;
            if(mapDesign.x < x + width / 2F){
                alignment = alignment | left;
            }else {
                alignment = alignment | right;
            }
            if(mapDesign.y < y + height / 2F){
                alignment = alignment | down;
            }else {
                alignment = alignment | up;
            }
            return alignment;
        }

        void resize(){
            if(isRelative){
                screen.setSize(screenWidth, screenHeight);
            }else {
                screen.setSize(screenWidth/2F,screenHeight/2F);
            }
            if((align & right) == right){
                screen.setX(screen.width);
            }
            if((align & up) == up){
                screen.setY(screen.height);
            }

            if((inlign&fullX) == fullX){
                screenDesign.width = screen.width;
            }else {
                screenDesign.width = mapDesign.width * unit;
            }
            if((inlign&fullY) == fullY) {
                screenDesign.height = screen.height;
            }else {
                screenDesign.height = mapDesign.height * unit;
            }

            if((inlign&centerH) == centerH){
                screenDesign.x = screen.x+(screen.width-screenDesign.width)/2F;
            }else {
                if((inlign &left) == left){
                    screenDesign.x = screen.x + pad.left*unit;
                }else if((inlign &right) == right){
                    screenDesign.x = (screen.x+screen.width-screenDesign.width) - pad.right*unit;
                }
            }
            if((inlign&centerV) == centerV){
                screenDesign.y = screen.y+(screen.height-screenDesign.height)/2F;
            }else {
                if((inlign &down) == down){
                    screenDesign.y = screen.y + pad.down*unit;
                }else if((inlign &up) == up){
                    screenDesign.y = (screen.y+screen.height-screenDesign.height) - pad.top*unit;
                }
            }
        }

        class Pad{
            float left, down, right, top;
            Pad() {
                left = mapDesign.x - world.x;
                down = mapDesign.y - world.y;
                right = world.x+(world.width-mapDesign.x-mapDesign.width);
                top = world.y+(world.height-mapDesign.y-mapDesign.height);
            }
        }
    }
}
