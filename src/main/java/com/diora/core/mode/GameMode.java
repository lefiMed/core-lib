package com.diora.core.mode;

import com.diora.core.animation.tweens.BaseTween;
import com.diora.core.animation.tweens.Tween;
import com.diora.core.animation.tweens.TweenCallback;
import com.diora.core.view.screens.Play;

import java.nio.ByteBuffer;

public abstract class GameMode {

    protected Play play;
    public String pathTmx,folder;
    public int level;

    public GameMode(String folder, int level) {
        this.level = level;
        this.pathTmx = "tmx/levels/"+folder+"/lvl"+level+".tmx";
        this.folder = folder;
    }

    public GameMode(int level) {
        this.level = level;
        this.pathTmx = "tmx/levels/lvl"+level+".tmx";
        this.folder = "";
    }

    public abstract void doNext(boolean isMyAction);

    public abstract boolean isMyAction();

    public void delay(float time, final Runnable runnable){
        Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int i, BaseTween<?> baseTween) {
                if(i == COMPLETE){
                    runnable.run();
                }
            }
        }).setCallbackTriggers(TweenCallback.COMPLETE).delay(time).start(play.sc.tweenManager);
    }

    protected byte[] toBytes(byte tag, final int i ) {
        return ByteBuffer.allocate(5).put(tag).putInt(i).array();
    }

    protected int toInt(byte[] buff) {
        return ByteBuffer.wrap(buff,1,4).getInt();
    }


}
