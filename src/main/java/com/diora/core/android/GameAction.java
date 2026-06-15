package com.diora.core.android;

public interface GameAction {

    //nrml
    void onBackPressed();
    void onDestroy();

    // rtmp
    void startGameForTow();
    void reciveMsg(final byte[] msg);
    void reciveInvi(String url);
    void cancelGame();

    //wifi

    //billing
    void onPurchase(String sku, boolean isAcknowledged);
    void onConsume(String sku);
    //

    void restoreGame();

}
