package com.diora.core.factory;

import com.badlogic.gdx.utils.Pool;
import com.diora.core.factory.def.DefBody;
import com.diora.core.factory.def.DefFixture;
import com.diora.core.factory.model.Models;
import com.diora.core.utils.Global;

public class WorldFactory {

    public Pool<DefBody>  defBodyPool;
    public Pool<DefFixture>  defFixturePool;
    public Models models;

    public WorldFactory() {
        defBodyPool = new Pool<DefBody>(2) {
            @Override
            protected DefBody newObject() {
                Global.log(" new DefBody");
                return new DefBody();
            }
        };
        defFixturePool = new Pool<DefFixture>(2) {
            @Override
            protected DefFixture newObject() {
                Global.log(" new DefFixture ");
                return new DefFixture();
            }
        };
    }
}
