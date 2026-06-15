package com.diora.core.stage.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.diora.core.stage.StageCreator;
import com.diora.core.ui.Skin;

import java.util.Locale;

public class ImageTimer extends Image {

    private float time;
    public int duration,counter;
    private boolean isFinished, isStart;
    private CallBack callBack;
    private RadialSprite radialSprite;
    private Drawable back , front;
    public Label number;

    public ImageTimer(StageCreator sc,String name,String back,String front,String styleNum) {
        super();
        Skin skin = sc.game.asset.skin;
        radialSprite = new RadialSprite(skin.getRegion(name));
        radialSprite.setOriginCenter();
        setDrawable(radialSprite);
        this.back = skin.getDrawable(back);
        this.front = skin.getDrawable(front);
        number = new Label("0", skin, styleNum);
        number.setAlignment(Align.center);
    }

    public Label getNumber() {
        return number;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        number.setText(format(duration));
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void reset(){
        time = 0.0f;
        counter = 0;
        isFinished = false;
        isStart = false;
    }

    public void start(){
        reset();
        isStart = true;
    }

    @Override
    public void setOrigin(float originX,float originY) {
        super.setOriginX(originX);
        if(number!=null){
            number.setOrigin(originX, originY);
        }
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        if(number!=null){
            number.setBounds(x, y, width, height);
        }
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if(number!=null)
            number.setPosition(x,y);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        if(number!=null)
            number.setSize(width, height);
    }

    private String format(Integer integer){
        return String.format(Locale.ENGLISH,"%02d",integer);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        update(Gdx.graphics.getDeltaTime());
        back.draw(batch,getX(),getY(),getWidth(),getHeight());
        super.draw(batch, parentAlpha);
        number.draw(batch,parentAlpha);
        front.draw(batch,getX(),getY(),getWidth(),getHeight());
    }

    public void update(float dt){
        if(isStart&&!isFinished){
            time+=dt;
            if(time>=1){
                counter++;
                int left = duration - counter;
                number.setText(format(left));
                time = 0.0f;
                if(callBack!=null)
                    callBack.onCount(left);
            }
            radialSprite.setAngle(360.0f/duration*(counter+time));
            if(counter>=duration){
                isFinished = true;
                if(callBack!=null)
                    callBack.onFinished();
            }
        }
    }

    public void render(Batch batch){
       // draw(batch, getX(), getY(), getHeight(), bounds.height);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        number.setVisible(visible);
    }

    public interface CallBack{
        void onFinished();
        void onCount(int count);
    }
}
