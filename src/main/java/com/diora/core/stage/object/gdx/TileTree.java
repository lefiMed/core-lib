package com.diora.core.stage.object.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.diora.core.stage.StageCreator;
import com.diora.core.stage.object.TileActor;

public class TileTree extends TileActor {

    public TileTree(StageCreator stageCreator, MapObject object, String actorId, Group parent) {
        super(stageCreator, object, actorId, parent);
    }

    @Override
    protected void defActor() {
        Tree tree;
        try {
            tree = new Tree(skin, getStyleName());
        }catch (Exception e){
            Gdx.app.error("Tree : " + getActorName(), "style not correct in skin : " + getStyleName());
            Tree.TreeStyle treeStyle = new Tree.TreeStyle(sc.getUtils().drawable, sc.getUtils().drawable,sc.getUtils().drawable);
            tree = new Tree(treeStyle);

        }

        setStyle(tree);
    }
}
