package com.diora.core.world;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.diora.core.utils.Global;

import java.io.Serializable;

import static com.diora.core.Game.GAME;

public class RectWorld implements Serializable {

    private static final long serialVersionUID = 1L;
    public float x, y,width,height;
    public float angle;

    public RectWorld() {
    }

    public RectWorld(RectWorld rectWorld) {
        this.x = rectWorld.x;
        this.y = rectWorld.y;
        this.width = rectWorld.width;
        this.height = rectWorld.height;
        this.angle = rectWorld.angle;
    }

    public RectWorld(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public RectWorld(float x, float y, Vector2 size) {
        this.x = x;
        this.y = y;
        this.width = size.x;
        this.height = size.y;
    }

    public RectWorld(MapObject object) {
        if(object instanceof RectangleMapObject) {
            toWorld(((RectangleMapObject) object).getRectangle());
        }else if( object instanceof EllipseMapObject){
            toWorld(((EllipseMapObject) object).getEllipse());
        }else if(object instanceof PolygonMapObject){
            Polygon poly = ((PolygonMapObject) object).getPolygon();
            poly.setPosition(poly.getX()/GAME.ppm,poly.getY()/GAME.ppm);
            poly.setScale(1/ GAME.ppm, 1/ GAME.ppm);
            Rectangle rect = poly.getBoundingRectangle();
            //**
            //toWorld(poly.getBoundingRectangle());
            this.x = rect.x;
            this.y = rect.y;
            this.width = rect.width;
            this.height = rect.height;
            Global.log(toString());
        }
    }

    public RectWorld(RectangleMapObject rectangleMapObject) {
        this(rectangleMapObject.getRectangle());
    }

    public RectWorld(EllipseMapObject ellipseMapObject) {
        this(ellipseMapObject.getEllipse());
    }

    public RectWorld(PolygonMapObject polyObject) {
        //TODO ifbody is daynamic or need sprite or joint center
        Polygon poly = polyObject.getPolygon();
        Rectangle rect = poly.getBoundingRectangle();
        //rect.setPosition(rect.x-rect.width/2, rect.y-rect.height/2);
        this.toWorld(rect);
    }

    public RectWorld(Rectangle rect) {
        toWorld(rect);
    }

    public RectWorld(Ellipse rect) {
        toWorld(rect);
    }

    public RectWorld copy(Rectangle rect){
        this.x = rect.x;
        this.y = rect.y;
        this.width = rect.width;
        this.height = rect.height;
        return this;
    }

    private void toWorld(Rectangle rect){
        this.x = rect.x / GAME.ppm;
        this.y = rect.y / GAME.ppm;
        this.width = rect.width / GAME.ppm;
        this.height = rect.height / GAME.ppm;
    }

    private void toWorld(Ellipse rect){
        this.x = rect.x / GAME.ppm;
        this.y = rect.y / GAME.ppm;
        this.width = rect.width / GAME.ppm;
        this.height = rect.height / GAME.ppm;
    }

    public float halfWidth(){
        return width/2;
    }

    public float halfHeight(){
        return height/2;
    }

    public float centerX(){
        return x + width/2f;
    }

    public float centerY(){
        return y + height/2f;
    }

    public Vector2 getPosistion(){
        return new Vector2(x, y);
    }

    public Vector2 getCenter(){
        return new Vector2(x + width/2f, y + height/2f);
    }

    public Vector2 getSize(){
        return new Vector2(width, height);
    }

    public float [] getVertices(){
        float [] vertices = new float[4*2];
        vertices[0] = x;
        vertices[1] = y;
        vertices[2] = x;
        vertices[3] = y+height;
        vertices[4] = x+width ;
        vertices[5] = y+height;
        vertices[6] = x+width;
        vertices[7] = y;
        return vertices;
    }

    public float [] getlocalVertices(){
        float [] vertices = new float[4*2];
        vertices[0] = 0;
        vertices[1] = 0;
        vertices[2] = 0;
        vertices[3] = height;
        vertices[4] = width ;
        vertices[5] = height;
        vertices[6] = width;
        vertices[7] = 0;
        return vertices;
    }

    public Rectangle getBoundingRectangle(){
        float[] vts = getVertices();
        float minX = vts[0]; float maxX = vts[0];
        float minY = vts[1]; float maxY = vts[1];
        for (int i = 2; i < vts.length; i += 2) {
            minX = minX > vts[i] ? vts[i] : minX;
            minY = minY > vts[i + 1] ? vts[i + 1] : minY;
            maxX = maxX < vts[i] ? vts[i] : maxX;
            maxY = maxY < vts[i + 1] ? vts[i + 1] : maxY;
        }
/*
        this.x = minX;
        this.y = minY;
        //this.x = polygon.getX() / GAME.ppm;
        //this.y = polygon.getY() / GAME.ppm;
        this.width = (maxX - minX);

       this.height = (maxY - minY);

 */
        return new Rectangle(minX,minY,maxX-minX,maxY-minY);
    }

    public void polyToRect(Polygon polygon){
        float[] vts = polygon.getVertices();
        float minX = vts[0]; float maxX = vts[0];
        float minY = vts[1]; float maxY = vts[1];
        for (int i = 2; i < vts.length; i += 2) {
            minX = minX > vts[i] ? vts[i] : minX;
            minY = minY > vts[i + 1] ? vts[i + 1] : minY;
            maxX = maxX < vts[i] ? vts[i] : maxX;
            maxY = maxY < vts[i + 1] ? vts[i + 1] : maxY;
        }

        //this.x = minX / GAME.ppm;
        //this.y = minY / GAME.ppm;
        this.x = polygon.getX() / GAME.ppm;
        this.y = polygon.getY() / GAME.ppm;
        this.width = (maxX - minX) / GAME.ppm;
        this.height = (maxY - minY) / GAME.ppm;
    }

    public RectWorld scale(float s){
        x*=s;
        y*=s;
        width*=s;
        height*=s;
        return this;
    }

    public RectWorld scaleSize(float s){
        width*=s;
        height*=s;
        return this;
    }

    public RectWorld translate(float tx, float ty){
        x += tx;
        y += ty;
        return this;
    }

    public Vector2 get2Width(){
        return new Vector2(width, width);
    }

    public Vector2 get2Width(float s){
        return new Vector2(width*s, width*s);
    }

    public Vector2 get2Height(){
        return new Vector2(height, height);
    }

    public Vector2 get2Height(float s){
        return new Vector2(height*s, height*s);
    }

    public Vector2 getMaxSize(){
        if(width>height)
            return get2Width();
        else
            return get2Height();
    }

    public Vector2 getMinSize(){
        if(width<height)
            return get2Width();
        else
            return get2Height();
    }

    public void setRotationFromBody(Body body) {
        this.angle = body.getAngle() * MathUtils.radDeg;
    }

    public RectWorld setPostition(Vector2 pos) {
        this.x = pos.x;
        this.y = pos.y;
        return this;
    }

    public RectWorld setPostition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public RectWorld setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public RectWorld setSize(Vector2 size) {
        this.width = size.x;
        this.height = size.y;
        return this;
    }

    public RectWorld copy(){
        return new RectWorld(this);
    }

    public void setCenterPos(Vector2 centerPos){
        setCenterPos(centerPos.x, centerPos.y);
    }

    public void setCenterPos(float cx, float cy){
        x = cx-width/2;
        y = cy-height/2;
    }

    public String toString(){
        String s =
                "x: "+ x + "\n" +
                        "y: "+ y + "\n" +
                        "W: "+ width + "\n" +
                        "H: "+ height + "\n" +
                        "angle: "+ angle + "\n";
        return s;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}

