package com.helloworld.renting.entities;

public enum WarrantyType {
    AVALISTA,
    FINANCIERO;

    public static WarrantyType fromString(String type) {
        for (WarrantyType warrantyType : WarrantyType.values()) {
            if (warrantyType.name().equalsIgnoreCase(type)) {
                return warrantyType;
            }
        }
        throw new IllegalArgumentException("Invalid WarrantyType: " + type);
    }
}
