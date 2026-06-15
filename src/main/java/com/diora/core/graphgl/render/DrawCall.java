package com.diora.core.graphgl.render;

public interface DrawCall {

    void draw(float dt);

    void resize(int width, int height);
}
