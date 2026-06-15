package com.diora.core.stage.object.costum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.actor.ButtonImage;
import com.diora.core.stage.object.TileActor;


public class TileButtonImage extends TileActor {

    public TileButtonImage(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        ButtonImage button = new ButtonImage();
        if(has("name")){
            String name = object.getProperties().get("name", String.class);
            TextureRegion region;
            region = sc.game.asset.getAtlas("ui").findRegion(name);
            if(region == null)
                region = sc.game.asset.getAtlas("sprite").findRegion(name);
            if(region == null){
                Texture texture;
                try {
                     texture = new Texture("ui/texture/" + name + ".png");
                }catch (Exception e){
                    texture = sc.getUtils().texture;
                    Gdx.app.error("TileButtonImage: "+name,"texture or region not found");
                }
                region = new TextureRegion(texture);
            }
            button.setRegion(region);
        }else if(has("pix"))
            button.setPix(object.getProperties().get("pix", Color.class));
        setStyle(button);
    }

}
