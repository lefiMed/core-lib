package com.diora.core.android;

public interface AndroidAds {

    void showBannerAd(boolean show);

    void loadInterstitialAd();

    void showInterstitialAd(Runnable doifnotShow);

    void loadRewordedAd();

    void showRewordedAd(Runnable doifReword);

    boolean isRewordedLoded();

}
