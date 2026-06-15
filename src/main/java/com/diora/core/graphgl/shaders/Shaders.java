package com.diora.core.graphgl.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.diora.core.Game;
import com.diora.core.factory.Serialise;
import com.diora.core.security.Security;

import java.io.Serializable;
import java.util.HashMap;

public class Shaders implements Serializable {

  private HashMap<String, String> glslVertx;
  private HashMap<String, String> glslFragment;

  public Shaders(HashMap<String, String> glslVertx, HashMap<String, String> glslFragment) {
    this.glslVertx = glslVertx;
    this.glslFragment = glslFragment;
  }

  public ShaderProgram getShader(String vertx, String frag){
    ShaderProgram shader;
    if(Game.IS_DEBUG){
      shader = new ShaderProgram(Gdx.files.internal("shader/"+vertx+".vert"),
              Gdx.files.internal("shader/"+frag+".frag"));
    }else{
      shader = new ShaderProgram(glslVertx.get(vertx), glslFragment.get(frag));
    }
    ShaderProgram.pedantic = false;
    if (!shader.isCompiled())
      System.err.println(shader.getLog());
    if (shader.getLog().length() != 0)
      System.out.println(shader.getLog());
    return shader;
  }

  public ShaderProgram getShader(String name){
    return getShader(name, name);
  }

  public String getVert(String nameVert) {
    return glslVertx.get(nameVert);
  }

  public String getFrag(String namefrag) {
    return glslFragment.get(namefrag);
  }

  public void serialise(String path){
    new Serialise(path, this);
  }

  public void decrypte() {
    for (String key : glslVertx.keySet()) {
      String value = glslVertx.get(key);
      glslVertx.put(key, Security.xorMessage(value));
    }

    for (String key : glslFragment.keySet()) {
      String value = glslFragment.get(key);
      glslFragment.put(key, Security.xorMessage(value));
    }
  }

}
