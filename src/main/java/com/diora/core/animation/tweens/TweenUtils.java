package com.diora.core.animation.tweens;

import com.diora.core.animation.tweens.equations.Back;
import com.diora.core.animation.tweens.equations.Bounce;
import com.diora.core.animation.tweens.equations.Circ;
import com.diora.core.animation.tweens.equations.Cubic;
import com.diora.core.animation.tweens.equations.Elastic;
import com.diora.core.animation.tweens.equations.Expo;
import com.diora.core.animation.tweens.equations.Linear;
import com.diora.core.animation.tweens.equations.Quad;
import com.diora.core.animation.tweens.equations.Quart;
import com.diora.core.animation.tweens.equations.Quint;
import com.diora.core.animation.tweens.equations.Sine;

/**
 * Collection of miscellaneous utilities.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class TweenUtils {
	private static TweenEquation[] easings;

	/**
	 * Takes an easing name and gives you the corresponding TweenEquation.
	 * You probably won't need this, but tools will love that.
	 *
	 * @param easingName The name of an easing, like "Quad.INOUT".
	 * @return The parsed equation, or null if there is no match.
	 */
	public static TweenEquation parseEasing(String easingName) {
		if (easings == null) {
			easings = new TweenEquation[] {Linear.INOUT,
				Quad.IN, Quad.OUT, Quad.INOUT,
				Cubic.IN, Cubic.OUT, Cubic.INOUT,
				Quart.IN, Quart.OUT, Quart.INOUT,
				Quint.IN, Quint.OUT, Quint.INOUT,
				Circ.IN, Circ.OUT, Circ.INOUT,
				Sine.IN, Sine.OUT, Sine.INOUT,
				Expo.IN, Expo.OUT, Expo.INOUT,
				Back.IN, Back.OUT, Back.INOUT,
				Bounce.IN, Bounce.OUT, Bounce.INOUT,
				Elastic.IN, Elastic.OUT, Elastic.INOUT
			};
		}

		for (int i=0; i<easings.length; i++) {
			if (easingName.equals(easings[i].toString()))
				return easings[i];
		}

		return null;
	}
}
