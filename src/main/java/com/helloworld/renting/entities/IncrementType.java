package com.helloworld.renting.entities;

public enum IncrementType {
    FIXED,
    PERCENTAGE;


    public static IncrementType fromString(String type) {
        for (IncrementType incrementType : IncrementType.values()) {
            if (incrementType.name().equalsIgnoreCase(type)) {
                return incrementType;
            }
        }
        throw new IllegalArgumentException("Invalid IncrementType: " + type);
    }
}
