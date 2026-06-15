package com.diora.core.factory;

import com.badlogic.gdx.files.FileHandle;

import java.io.InputStream;
import java.io.ObjectInputStream;

public class Deserialise {

    private Object object = null;
    private Listener listener;

    public Deserialise(FileHandle file, Listener listener) {
        this(file.read(), listener);

    }

    public Deserialise(InputStream inputStream, Listener listener) {
        this.listener = listener;
        try {
            ObjectInputStream in = new ObjectInputStream(inputStream);
            object = in.readObject();
            in.close();
            inputStream.close();
            System.out.println("Object has been deserialized ");
            if(listener!=null)
                listener.onSuccess(object);
        } catch(Exception e) {
            if(listener!=null)
                listener.onFailed(e.getMessage());
            e.printStackTrace();
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public  interface Listener{
        void onSuccess(Object object);
        void onFailed(String s);
    }

    public <T> T getObj(Class<T> type){
        return type.cast(object);
    }
}
