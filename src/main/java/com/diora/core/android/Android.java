package com.diora.core.android;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ArrayMap;
import com.diora.core.Game;
import com.diora.core.stage.ui.RunnbleUi;

import java.util.ArrayList;
import java.util.List;

public class Android {

    public AndroidLoad load;
    public AndroidAction action;
    public AndroidAds ads;
    public AndroidMultiPlayer multiPlayer;
    public AndroidWifi wifi;
    public AndroidBilling billing;
    public boolean isFreeAds, isRewordedAvialable;
    private Game game;
    public List<String> skuList;
    private boolean isLoaded;

    public Android(AndroidLoad load) {
        this.load = load;
        this.skuList = new ArrayList<>();
    }

    public Android(Game game) {
        this.game = game;
        this.load = new AndroidLoad() {
            @Override
            public void load() {
                Gdx.app.log("AndroidLoad", "load");
                setLoaded(true);
            }
        };
        this.action = new AndroidAction() {
            @Override
            public void rateGame() {
                Gdx.app.log("AndroidAction", "rateGame");
            }

            @Override
            public void jointTeleg() {
                Gdx.app.log("AndroidAction", "rateFbPage");
            }

            @Override
            public void falloqInstag() {
                Gdx.app.log("AndroidAction", "rateInsta");
            }

            @Override
            public void shareGame() {
                Gdx.app.log("AndroidAction", "shareGame");
            }

            @Override
            public void privacyPolicy() {
                Gdx.app.log("AndroidAction", "privacyPolicy");
            }

            @Override
            public void condition() {
                Gdx.app.log("AndroidAction", "condition");
            }

            @Override
            public void lightOn() {
                Gdx.app.log("AndroidAction", "lightOn");
            }

            @Override
            public void lightOff() {
                Gdx.app.log("AndroidAction", "lightOff");
            }

            @Override
            public void showMsg(String s) {
                Gdx.app.log("AndroidAction", "showMsg : " + s);
            }
        };
        this.ads = new AndroidAds() {
            @Override
            public void showBannerAd(boolean show) {
                if (!isFreeAds)
                    Gdx.app.log("AndroidAds", "showBannerAd " + show);
                else
                    Gdx.app.log("AndroidAds{freeAds}", "showBannerAd " + show);
            }

            @Override
            public void showInterstitialAd(Runnable then) {
                if (!isFreeAds)
                    Gdx.app.log("AndroidAds", "showInterstitialAd");
                else
                    Gdx.app.log("AndroidAds{freeAds}", "showInterstitialAd ");
                if (then != null) then.run();
            }

            @Override
            public void showRewordedAd(Runnable then) {
                Gdx.app.log("AndroidAds", "showRewordedAd");
                if (then != null) then.run();
            }

            @Override
            public void loadInterstitialAd() {
                Gdx.app.log("AndroidAds", "loadInterstitialAd");
            }

            @Override
            public void loadRewordedAd() {
                Gdx.app.log("AndroidAds", "loadRewordedAd");
            }

            @Override
            public boolean isRewordedLoded() {
                return true;
            }
        };
        this.multiPlayer = new AndroidMultiPlayer() {
            @Override
            public void signIn() {
                Gdx.app.log("AndroidMultiPlayer", "signIn");
            }

            @Override
            public void quickGame() {
                Gdx.app.log("AndroidMultiPlayer", "quickGame");
            }

            @Override
            public void sendInvi() {
                Gdx.app.log("AndroidMultiPlayer", "sendInvi");
            }

            @Override
            public void showInvi() {
                Gdx.app.log("AndroidMultiPlayer", "showInvi");
            }

            @Override
            public void sengMsg(byte[] msg) {
                Gdx.app.log("AndroidMultiPlayer", "sengMsg");
            }

            @Override
            public void iLeft() {
                Gdx.app.log("AndroidMultiPlayer", "iLeft");
            }

            @Override
            public void updateUi(RunnbleUi runnable) {
                Gdx.app.log("AndroidMultiPlayer", "updateUi");
            }

            @Override
            public ArrayMap<String, String> getInfoMap() {
                Gdx.app.log("AndroidMultiPlayer", "getInfoMap");
                return null;
            }
        };
        this.wifi = new AndroidWifi() {
            @Override
            public void advertiseMe(String role) {
                Gdx.app.log("AndroidWifi", "advertiseMe");
            }

            @Override
            public void discoverPeer(String role) {
                Gdx.app.log("AndroidWifi", "discoverPeer");
            }

            @Override
            public void connectWithPeer(String address) {
                Gdx.app.log("AndroidWifi", "connectWithPeer");
            }

            @Override
            public void sendMsg(byte[] msg) {
                Gdx.app.log("AndroidWifi", "sendMsg");
            }
        };
        this.billing = new AndroidBilling() {
            @Override
            public void buy(String skuId) {
                Gdx.app.log("AndroidBilling", "buy " + skuId);
                if (Gdx.app.getType().equals(Application.ApplicationType.Desktop)) {
                    game.gameAction.onPurchase(skuId, false);
                }
            }

            @Override
            public void consume(String skuId) {
                Gdx.app.log("AndroidBilling", "consume " + skuId);
            }

            @Override
            public void consumeAll() {
                Gdx.app.log("AndroidBilling", "consumeAll ");
            }
        };
        this.skuList = new ArrayList<>();
        this.skuList.add("free_ads");
    }

    public void freeAds() {
        isFreeAds = true;
        ads.showBannerAd(false);
    }

    public void showBannerAd(boolean show) {
        if (isFreeAds) {
            return;
        }
        ads.showBannerAd(show);
    }

    public void showInterstitialAd(Runnable then) {
        if (isFreeAds) {
            if (then != null) {
                then.run();
            }
            return;
        }
        ads.showInterstitialAd(then);
    }

    public void showRewordedAd(Runnable then) {
        ads.showRewordedAd(then);
    }

    public void setLoad(AndroidLoad load) {
        this.load = load;
    }

    public void setAction(AndroidAction action) {
        this.action = action;
    }

    public void setAds(AndroidAds ads) {
        this.ads = ads;
    }

    public void setMultiPlayer(AndroidMultiPlayer multiPlayer) {
        this.multiPlayer = multiPlayer;
    }

    public void setWifi(AndroidWifi wifi) {
        this.wifi = wifi;
    }

    public void setBilling(AndroidBilling billing) {
        this.billing = billing;
    }

    public boolean isRewordedAvialable() {
        return isRewordedAvialable;
    }

    public void setRewordedAvialable(boolean rewordedAvialable) {
        isRewordedAvialable = rewordedAvialable;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }
}
