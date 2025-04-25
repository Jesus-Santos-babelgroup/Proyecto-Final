package com.helloworld.renting.entities;

public enum PreResultType {
    PREAPPROVED,
    PREDENIED,
    NEEDS_REVIEW;

    public static PreResultType fromString(String type) {
        for (PreResultType preresultType : PreResultType.values()) {
            if (preresultType.name().equalsIgnoreCase(type)) {
                return preresultType;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }
}
