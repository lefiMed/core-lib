package com.diora.core.factory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Serialise {

    public Serialise(String name, Object object) {
        try {
            FileOutputStream file = new FileOutputStream(name);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(object);
            out.close();
            file.close();
            System.out.println("Object has been serialized");
        } catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("IOException is caught");
        }
    }
}
