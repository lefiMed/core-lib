package com.diora.core.view.window;

import com.diora.core.view.screens.GameScreen;

public class WinRewardedVideo extends Window {

    private Runnable rewarded;

    public WinRewardedVideo(final GameScreen gameScreen,Runnable rewarded) {
        super(gameScreen, "w_rewardedvideo");
        this.rewarded = rewarded;

        addListener("cancel", new Runnable() {
            @Override
            public void run() {
                hide();
            }
        });

        addListener("watch", new Runnable() {
            @Override
            public void run() {
                sc.game.android.showRewordedAd(() -> hide(rewarded));
            }
        });
    }
}
