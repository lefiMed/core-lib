package com.diora.core.factory;

import com.badlogic.gdx.physics.box2d.FixtureDef;

public interface B2Event {

    //void onFixtureCreated(Fixture fixture);
    void OnFixtureDef(FixtureDef fDef);
}
