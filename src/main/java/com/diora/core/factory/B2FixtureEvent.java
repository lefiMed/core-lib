package com.diora.core.factory;

import com.badlogic.gdx.physics.box2d.Fixture;

public interface B2FixtureEvent {

    void onFixture(Fixture fixture);
}
