package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileTextTooltip extends TileActor {

    public TileTextTooltip(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        TextTooltip textTooltip;
        try {
            textTooltip = new TextTooltip(getText(), skin, getStyleName());
        }catch (Exception e){
            Gdx.app.error("TextTooltip : "+getActorName(),"style not correct in skin : "+getStyleName());
            Label.LabelStyle labelStyle =  new Label.LabelStyle(sc.getUtils().bitmapFont, sc.getUtils().color);
            TextTooltip.TextTooltipStyle tooltipStyle = new TextTooltip.TextTooltipStyle(labelStyle,sc.getUtils().drawable);
            textTooltip = new TextTooltip(getText(), tooltipStyle);
        }

        textTooltip.setAlways(true);
        textTooltip.setInstant(true);

        if(has("fontScale"))
            textTooltip.getActor().setFontScale(getF("fontScale"));
        designIt(textTooltip.getContainer());
        designIt(textTooltip.getActor());

        Actor actor=null;
        if(has("actor"))
             actor = sc.get(getS("actor"));
        if(actor!=null)
            actor.addListener(textTooltip);

        sc.initToolTipMap();
        sc.tooltips.put(getActorName(), textTooltip);
    }
}
