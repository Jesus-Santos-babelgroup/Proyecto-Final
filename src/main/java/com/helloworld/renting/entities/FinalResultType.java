package com.helloworld.renting.entities;

public enum FinalResultType {
    APPROVED,
    DENIED,
    APPROVED_WITH_WARRANTY;

    public static FinalResultType fromString(String result) {
        for (FinalResultType type : FinalResultType.values()) {
            if (type.name().equalsIgnoreCase(result)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown result type: " + result);
    }
}
