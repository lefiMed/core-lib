package com.diora.core.tool.exception;

import com.badlogic.gdx.utils.GdxRuntimeException;

public class ErrorException  extends GdxRuntimeException {

    public ErrorException(String msg) {
        super("Error : "+msg);
    }
}
