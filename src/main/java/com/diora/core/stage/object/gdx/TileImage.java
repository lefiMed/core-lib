package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileImage extends TileActor {

    public TileImage(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        Image image = new Image();
        if(has("name")){
            String name = object.getProperties().get("name", String.class);
            TextureRegion region;
            region = skin.getAtlas().findRegion(name);
            if(region != null){
                image.setDrawable(skin.getDrawable(name));
            }else {
                region = sc.game.asset.getAtlas("sprite").findRegion(name);
                if(region!=null){
                    image.setDrawable(new TextureRegionDrawable(region));
                }else {
                    Texture texture;
                    try {
                        texture = new Texture("ui/texture/" + name + ".png");
                    }catch (Exception e){
                        texture = sc.getUtils().texture;
                        Gdx.app.error("TileImage: "+name,"texture or region not found");
                    }
                    region = new TextureRegion(texture);
                    image.setDrawable(new TextureRegionDrawable(region));
                }
            }
        }else if(has("pix")) {
            Pixmap pmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
            pmap.setColor(object.getProperties().get("pix", Color.class));
            pmap.fill();
            image.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(pmap))));
            pmap.dispose();
        }
        setStyle(image);
    }
}