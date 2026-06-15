package com.diora.core.tool;

import java.security.SecureRandom;

public class Tools {

    private final SecureRandom random = new SecureRandom();
    public  <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
