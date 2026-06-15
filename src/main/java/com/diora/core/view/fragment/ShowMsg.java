package com.diora.core.view.fragment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.diora.core.Game;
import com.diora.core.animation.tweens.BaseTween;
import com.diora.core.animation.tweens.Timeline;
import com.diora.core.animation.tweens.Tween;
import com.diora.core.animation.tweens.TweenCallback;
import com.diora.core.animation.tweens.TweenEquations;
import com.diora.core.animation.tweens.accessors.ActorAccessor;

public class ShowMsg extends Fragment {

    private float duration = 2.5f;
    private Label label;

    public ShowMsg() {
        super(Game.GAME.getGameScreen(), "show_msg", true);
        float x = (sc.screenStage.screenWidth-group.getWidth())/2;
        group.setTransform(true);
        group.setY(-group.getHeight()*1.2f);
        label = get(Label.class,"msg");
    }

    private void showMsg(String msg){
        Gdx.app.log("","start shwow msg");
        label.setText(msg);
        label.pack();
        label.setX((group.getWidth()-label.getWidth())/2f);
        Timeline.createSequence()
                .push(Tween.to(group, ActorAccessor.POS_Y,0.6f).target(0).ease(TweenEquations.easeOutExpo))
                .pushPause(duration)
                .push(Tween.to(group, ActorAccessor.POS_Y,0.5f).target(-group.getHeight()*1.2f).ease(TweenEquations.easeOutExpo))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int i, BaseTween<?> baseTween) {
                        dispose();
                        Gdx.app.log("","dispose showMsg msg");
                    }

                }).start(sc.tweenManager);
    }

    public static void show(String msg){
        ShowMsg showMsg = new ShowMsg();
        showMsg.showMsg(msg);
    }

    public static void info(String msg){
        ShowMsg showMsg = new ShowMsg();
        showMsg.label.setColor(Color.BLUE);
        showMsg.showMsg(msg);
    }

    public static void msg(String msg){
        ShowMsg showMsg = new ShowMsg();
        showMsg.label.setColor(Color.GREEN);
        showMsg.showMsg(msg);
    }
    public static void error(String msg){
        ShowMsg showMsg = new ShowMsg();
        showMsg.label.setColor(Color.RED);
        showMsg.showMsg(msg);
    }
}
