package com.diora.core.stage.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.diora.core.stage.StageCreator;

import java.util.Locale;

public class ChangeValue extends Group {

    StageCreator sc;
    private float value = 0.0f;
    private float step = 0.10f;
    private float max = 1.0f;
    private float min = 0.0f;
    TextField valueTxt ;
    Change change;

    public ChangeValue(StageCreator sc, String name) {
        this.sc = sc;
        Label nameLabel = sc.createActor(Label.class,"nameChange",null, sc.uiMap);
        nameLabel.setText(name+" :");
        Button up = sc.createActor(Button.class,"up",null, sc.uiMap);
        Button down = sc.createActor(Button.class,"down",null, sc.uiMap);
        valueTxt = sc.createActor(TextField.class,"value",null, sc.uiMap);
        valueTxt.setAlignment(Align.center);
        valueTxt.addListener(new InputListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                valueTxt.getStage().setScrollFocus(valueTxt);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                valueTxt.getStage().setScrollFocus(null);
                try{
                    value = Float.parseFloat(valueTxt.getText());
                }catch (Exception e){
                    Gdx.app.error("Error","parseFloat");
                }
                finally {
                    setValue(value);
                }
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                addValue(step*amountX*-1);
                return true;
            }

        });
        sc.addListener(up, new Runnable() {
            @Override
            public void run() {
                addValue(+step);
            }
        });
        sc.addListener(down, new Runnable() {
            @Override
            public void run() {
                addValue(-step);
            }
        });
        addActor(nameLabel);
        addActor(up);
        addActor(down);
        addActor(valueTxt);
    }

    private void addValue(float v){
        float temp = value+v;
        if(temp>max){
            value = max;
        }else if(temp<min){
            value = min;
        }else {
            value = temp;
        }
        valueTxt.setText(String.format(Locale.US,"%.2f",this.value));
        if(change!=null) change.onChange(value);
    }

    public void setValue(Float value) {
        if (value == null) {
            this.value = 0.0f;
        }else if(value>max){
            this.value = max;
        }else if(value<min){
            this.value = min;
        }else {
            this.value = value;
        }
        valueTxt.setText(String.format(Locale.US,"%.2f",this.value));
        if(change!=null) change.onChange(this.value);
    }

    public TextField getValueTxt() {
        return valueTxt;
    }

    public void setStep(float step) {
        this.step = step;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getValue() {
        return value;
    }

    public float getStep() {
        return step;
    }

    public float getMax() {
        return max;
    }

    public float getMin() {
        return min;
    }

    public void setChange(Change change) {
        this.change = change;
    }

    public interface Change{
        void onChange(float f);
    }
}
