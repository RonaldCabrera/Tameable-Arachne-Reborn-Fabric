package net.pevori.tameablearachnereborn.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum HarpyVariant {
    SKY(0),
    PINK(1),
    LIGHTGREEN(2),
    YELLOW(3),
    MAGENTA(4),
    ORANGE(5),
    WHITE(6),
    LIGHTBLUE(7),
    BROWN(8),
    GREEN(9),
    GOLD(10),
    GRAY(11),
    RED(12),
    BLACK(13);

    private static final HarpyVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(HarpyVariant::getId)).toArray(HarpyVariant[]::new);

    private final int id;

    HarpyVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static HarpyVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public static HarpyVariant nextVariant(int id){
        if(id == 13){
            return HarpyVariant.SKY;
        }

        int newId = id + 1;
        return byId(newId);
    }
}
