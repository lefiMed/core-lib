package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;


public class TileSlider extends TileActor {

    public TileSlider(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        float min = has("min")? getF("min") : 0.0f;
        float max = has("max")? getF("max") : 100.0f;
        float step = has("step")? getF("step") : 1.0f;
        boolean isVertical = isSelect("isV");

        Slider slider;
        try {
             slider = new Slider(min,max,step,isVertical, sc.game.asset.skin, getStyleName());
        }catch (Exception e) {
            Gdx.app.error("Slider : " + getActorName(), "style not correct in skin : " + getStyleName());
            Slider.SliderStyle sliderStyle = new Slider.SliderStyle(sc.getUtils().drawable, sc.getUtils().drawable);
            slider = new Slider(min, max, step, isVertical, sliderStyle);
        }
        if(has("value"))
            slider.setValue(getF("value"));
        setStyle(slider);
/*
        slider.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                sc.game.pref.ps.putFloat(object.getName(),slider.getValue());
                sc.game.pref.ps.flush();
            }
        });
        */
    }
}
