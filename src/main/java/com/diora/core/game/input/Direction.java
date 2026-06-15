package com.diora.core.game.input;

import com.badlogic.gdx.input.GestureDetector;

public class Direction extends GestureDetector {

    public interface DirectionListener {
        void onLeft(float speed);

        void onRight(float speed);

        void onUp(float speed);

        void onDown(float speed);

    }

    public Direction(DirectionListener directionListener) {
        super(new DirectionGestureListener(directionListener));
    }

    private static class DirectionGestureListener extends GestureAdapter{
        DirectionListener directionListener;

        public DirectionGestureListener(DirectionListener directionListener){
            this.directionListener = directionListener;
        }



        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            if(Math.abs(velocityX)>Math.abs(velocityY)){
                if(velocityX>0){
                    directionListener.onRight(velocityX);
                }else{
                    directionListener.onLeft(velocityX);
                }
            }else{
                if(velocityY>0){
                    directionListener.onDown(velocityY);
                }else{
                    directionListener.onUp(velocityY);
                }
            }
            return super.fling(velocityX, velocityY, button);
        }

    }

}