package net.pevori.tameablearachnereborn.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ArachneMediumVariant {
    STRAWBERRY(0),
    DANDELION(1);

    private static final ArachneMediumVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(ArachneMediumVariant::getId)).toArray(ArachneMediumVariant[]::new);

    private final int id;

    ArachneMediumVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ArachneMediumVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public static ArachneMediumVariant nextVariant(int id){
        if(id == 2){
            return ArachneMediumVariant.STRAWBERRY;
        }
        return byId(++id);
    }
}
