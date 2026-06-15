package com.diora.core.tool;

import com.diora.core.animation.tweens.BaseTween;
import com.diora.core.animation.tweens.Tween;
import com.diora.core.animation.tweens.TweenCallback;
import com.diora.core.animation.tweens.TweenManager;

public class Task {

    public static void delay(float time, TweenManager tweenManager, final Runnable runnable){
        Tween.call(new TweenCallback() {
            @Override
            public void onEvent(int i, BaseTween<?> baseTween) {
                if(i == COMPLETE){
                    runnable.run();
                }
            }
        }).setCallbackTriggers(TweenCallback.COMPLETE).delay(time).start(tweenManager);
    }
}
