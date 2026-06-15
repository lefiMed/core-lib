package com.diora.core.android;

import com.badlogic.gdx.utils.ArrayMap;
import com.diora.core.stage.ui.RunnbleUi;

public interface AndroidMultiPlayer {

    void signIn();

    void quickGame();

    void sendInvi();

    void showInvi();

    void sengMsg(byte[] msg);

    void iLeft();

    void updateUi(RunnbleUi runnable);

    ArrayMap<String, String> getInfoMap();

}
