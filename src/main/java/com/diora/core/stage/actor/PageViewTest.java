package com.diora.core.stage.actor;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Iterator;

public class PageViewTest extends Table implements Disposable {

	private HashMap<Integer, String> cacheIdxName = new HashMap<Integer, String>();

	private int regIdxTmp = 0;

	private HashMap<String, Group> cacheNameActor = new HashMap<String, Group>();

	private int curIdx = 0;

	public void setPageIdx(int idx) {
		int moveIdx = curIdx - idx;
		curIdx = idx;
		Iterator<String> it = cacheNameActor.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Group group = cacheNameActor.get(key);
			group.setVisible(false);
			group.setPosition(group.getX() + getWidth() * moveIdx, group.getY());
		}
		Group panel = cacheNameActor.get(cacheIdxName.get(idx));
		panel.setVisible(true);

	}

	@Override
	public void setSize(float width, float height) {
		// this.setClip(true);
		// this.setCullingArea(new Rectangle(0, 0, width, height));
		super.setSize(width, height);
	}

	public void regPanel(Group panel, String panelName) {
		cacheIdxName.put(regIdxTmp++, panelName);
		cacheNameActor.put(panelName, panel);
	}
	
	@Override
	public void dispose() {
		cacheIdxName.clear();
		cacheNameActor.clear();
	}
}
