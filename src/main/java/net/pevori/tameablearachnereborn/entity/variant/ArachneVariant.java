package net.pevori.tameablearachnereborn.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum ArachneVariant {
    NOIRE(0),
    BLANC(1);

    private static final ArachneVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(ArachneVariant::getId)).toArray(ArachneVariant[]::new);

    private final int id;

    ArachneVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ArachneVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public static ArachneVariant nextVariant(int id){
        if(id == 2){
            return ArachneVariant.NOIRE;
        }
        return byId(++id);
    }
}
