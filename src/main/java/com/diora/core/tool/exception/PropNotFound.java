package com.diora.core.tool.exception;

import com.badlogic.gdx.utils.GdxRuntimeException;

public class PropNotFound extends GdxRuntimeException {

    public PropNotFound(String name) {
        super("Properties : "+name+" not found in map");
    }
}
