package com.diora.core.tool;

import com.badlogic.gdx.Gdx;
import com.diora.core.utils.Global;

public class Percent {
    private float time,duration,percent;
    private boolean isComplete;
    private boolean repeat;

    public Percent(float duration) {
        this.duration = duration;
    }

    public void upadte(float dt){
        if(dt>0.1) return;
        Global.log("dt : "+dt);
        if(isComplete){
            if(repeat)
                reset();
            else
                return;
        }
        percent = time / duration;
        time += dt;
        Gdx.app.log("On update percent time"+time,"percent "+percent);
        if(time>duration){
            isComplete = true;
            time = duration;
            percent = 1.0f;
            Gdx.app.log("On complet percent time"+time,"percent "+percent);
        }
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public float getPercent() {
        return percent;
    }

    public void reset(){
        isComplete = false;
        time = 0.0f;
        percent = 0.0f;
    }

    public float getTime() {
        return time;
    }

    public float getDuration() {
        return duration;
    }

    public boolean isComplete() {
        return isComplete;
    }
}
