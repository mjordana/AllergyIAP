package com.allergyiap.entities;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Base class for entities
 */
public abstract class Entity implements Serializable{

    /**
     * Show all attributes of entities
     */
    public String toString() {
        String output = "";
        output += "[";
        for (Field f : getClass().getFields()) {
            String name = f.getName();
            String value = "null";
            try {
                value = f.get(this).toString();
            } catch (Exception e) {

            }
            output += name + ":\"" + value + "\",";
        }
        output += "]";
        return output;
    }
}
