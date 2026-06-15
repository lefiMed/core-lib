package com.diora.core.factory.map;

import java.io.Serializable;

public abstract class ShapeObject implements Serializable {

    private static final long serialVersionUID = 1L;
    transient CallBack callBack;

    public abstract void toShape();

    public abstract void toShape(float scale);

    public float[] scale(float[] vts, float scl){
        float[] tmp = new float[vts.length];
        int i = -1;
        for (float vt : vts) {
            tmp[++i] = vt * scl;
        }
        return tmp;
    }

    public ShapeObject setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }
}


