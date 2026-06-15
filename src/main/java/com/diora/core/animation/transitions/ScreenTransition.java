package com.diora.core.animation.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public interface ScreenTransition {
	/** Renders two textures to the given myBatch
	 * @param batch the {@link Batch}
	 * @param currentScreenTexture {@link Texture} from a {@link FrameBuffer}
	 * @param nextScreenTexture {@link Texture} from a {@link FrameBuffer}
	 * @param percent the current progress 0.0 - 1.0 */
	void render(Batch batch, Texture currentScreenTexture, Texture nextScreenTexture, float percent);

}
