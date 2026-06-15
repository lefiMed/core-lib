package com.diora.core.stage.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.SnapshotArray;
import com.diora.core.animation.tweens.Timeline;
import com.diora.core.animation.tweens.Tween;
import com.diora.core.animation.tweens.TweenEquations;
import com.diora.core.animation.tweens.accessors.ActorAccessor;
import com.diora.core.stage.StageCreator;
import com.diora.core.tool.exception.ErrorException;

public class PageView extends Group {

    private Timeline timeline;
    private final byte NOTTHING = 0x00;
    private final byte DRAG_LEFT = 0x01;
    private final byte DRAG_RIGHT = 0x02;
    private byte move = NOTTHING;
    private ButtonImage[] buttons ;
    private Vector2 pointSize = new Vector2(10.0f,10.0f);
    private int currentPoint = 0, pageAdd = 0;
    private StageCreator sc;
    public int allPage;
    private float demiWidh, posX = 0.0f;
    private boolean isAnim,clip;
    private PageListener pageListener;
    public float space = 20.0f;
    private float pointsY = 32f;

    public PageView(StageCreator sc) {
        this.sc = sc;
       // sc.stage.addListener(getFling());
       // sc.stage.addListener(getDragListener());
        sc.stage.addListener(getFling());
        sc.stage.addListener(getDragListener());
    }

    public ActorGestureListener getFling(){
        return new ActorGestureListener() {
            @Override
            public void fling(InputEvent event, float velocityX, float velocityY, int button) {
                super.fling(event, velocityX, velocityY, button);
                if(velocityX > 150){
                    move = DRAG_RIGHT;
                }else if(velocityX < -150){
                    move = DRAG_LEFT;
                }
            }
        };
    }

    public DragListener getDragListener(){
        return new DragListener(){
            float startX, lastDrag;

            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                super.dragStart(event, x, y, pointer);
               // if(isAnim) return;
                startX = x;
                move = NOTTHING;
                lastDrag = x;
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.drag(event, x, y, pointer);
               // if(isAnim) return;
                if(Math.abs(startX-x)<=getWidth()) {
                    float xd = -(lastDrag - x);
                    lastDrag = x;
                    SnapshotArray<Actor> children = getChildren();
                    Actor[] actors = children.begin();
                    for (int i = 0, n = children.size; i < n; i++) {
                        Actor child = actors[i];
                        child.moveBy(xd, 0.0f);
                    }
                    children.end();
                }
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);
               // if(isAnim) return;
                float distance = startX-x;
                boolean isLeft = x < startX;
                boolean drag = Math.abs(distance) >= demiWidh;
                if(((drag && isLeft) || move == DRAG_LEFT) && isAllowDragLeft()){
                    //LeftDrag
                    posX-=getWidth();
                    dragPageLeft();
                }else if(((drag && !isLeft) || move == DRAG_RIGHT) && isAllowDragRight()){
                    //RightDrag
                    posX+=getWidth();
                    dragPageRight();
                }

                animToX(posX);
            }
        };
    }

    private float padH(Actor actor){
        return (getWidth()-actor.getWidth())/2;
    }

    private float padV(Actor actor){
        return (getHeight()-actor.getHeight())/2;
    }

    public void setAllPage(int nbr){
        allPage = nbr;
        updatePoints();
    }

    public void addPage(Actor actor) {
        float x = padH(actor) + getWidth()*pageAdd ;
        float y = padV(actor);
        actor.setPosition(x, y);
        addActor(actor);
        pageAdd++;
    }

    public void setPage(int index){
        if(index > allPage) index = allPage;
        if(index < 1) index = 1;
        if(index>pageAdd) throw new ErrorException("page "+index+" not add");
        index-=1;
        changeSmall(buttons[currentPoint]);
        currentPoint = index;
        changeBig(buttons[currentPoint]);

        float toX = getWidth()*(index);
        posX = -toX;
        SnapshotArray<Actor> children = getChildren();
        Actor[] actors = children.begin();
        for (int i = 0, n = children.size; i < n; i++) {
            Actor child = actors[i];
            child.moveBy(-toX,0.0f);
        }
        children.end();
    }

    private void animToX(float x){
        timeline = Timeline.createParallel();
        SnapshotArray<Actor> children = getChildren();
        Actor[] actors = children.begin();
        for (int i = 0, n = children.size; i < n; i++) {
            Actor child = actors[i];
            float newX = i*getWidth()+x;
            newX+= padH(child);
            timeline.push(Tween.to(child, ActorAccessor.POS_X,0.6f)
                    .ease(TweenEquations.easeOutCubic)
                    .target(newX));
        }
        children.end();
        timeline.start(sc.tweenManager);
    }

    public void setPointsY(float pointsY) {
        this.pointsY = pointsY;
    }

    private void updatePoints(){
        Group group = new Group();
        float space = pointSize.x*1.2f;
        float gWidh = (pointSize.x+(space-1))*allPage;
        group.setPosition(getX()+(getWidth()-gWidh)/2,getY()-pointsY);
        buttons = new ButtonImage[allPage];
        Drawable region = sc.game.asset.skin.getDrawable("small_point");
        for (int i = 0; i < allPage; i++){
            ButtonImage button = new ButtonImage(region);
            button.setBounds((pointSize.x+space)*i,0,pointSize.x, pointSize.y);
            button.setOrigin(pointSize.x/2,pointSize.y/2);
            buttons[i] = button;
            group.addActor(button);
        }
        changeBig(buttons[0]);
        sc.stage.addActor(group);
    }

    private void changeSmall(ButtonImage b){
        b.setScale(1.0f);
        b.setDrawable(sc.game.asset.skin,"small_point");
    }

    private void changeBig(ButtonImage b){
        b.setScale(1.8f);
        b.setDrawable(sc.game.asset.skin,"big_point");
    }

    private boolean isAllowDragLeft(){
        return (currentPoint < buttons.length-1);
    }

    private boolean isAllowDragRight(){
        return (currentPoint > 0);
    }

    private void dragPageLeft(){
        changeSmall(buttons[currentPoint]);
        changeBig(buttons[++currentPoint]);
        if(pageListener!=null)
            pageListener.pageLeft(currentPoint+1);
    }

    private void dragPageRight(){
        changeSmall(buttons[currentPoint]);
        changeBig(buttons[--currentPoint]);
        if(pageListener!=null)
            pageListener.pageRight(currentPoint+1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        applyTransform(batch, computeTransform());
        //drawBackground(batch, parentAlpha, 0, 0);

        if(clip){
            batch.flush();
        if (clipBegin(0,0,getWidth(),getHeight())){
            drawChildren(batch, parentAlpha);
            batch.flush();
            clipEnd();
        }}else {
            drawChildren(batch, parentAlpha);
        }
        resetTransform(batch);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        setCullingArea(new Rectangle(0,0,width,height));
        demiWidh = getWidth()/2.0f;
    }

    public void setPageListener(PageListener pageListener) {
        this.pageListener = pageListener;
    }

    public void setPointSize(Vector2 pointSize) {
        this.pointSize = pointSize;
    }

    public void setClip(boolean clip) {
        this.clip = clip;
    }

    public interface PageListener{
        void pageLeft(int page);
        void pageRight(int page);
    }
}
