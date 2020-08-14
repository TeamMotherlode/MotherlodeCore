package motherlode.uncategorized.block;

import net.minecraft.util.StringIdentifiable;

import java.util.Random;

public enum PotColor implements StringIdentifiable {
    RED("red",0xFF0000),
    BLUE("blue",0x0000FF),
    GREEN("green", 0x00FF00),
    YELLOW("yellow",0xFFFF00),
    BLACK("black",0x000000),
    WHITE("white",0xFFFFFF);

    private static final PotColor[] VALUES = values();
    private static final int SIZE = VALUES.length;

    String str;
    int color;
    PotColor(String str, int color) {
        this.str = str;
        this.color = color;
    }

    @Override
    public String asString() {
        return str;
    }

    public int getColor() {
        return color;
    }
    public static PotColor getRandom(Random random) {
        return VALUES[random.nextInt(SIZE)];
    }

    public static PotColor fromString(String str) {
        for (PotColor color : VALUES)
            if (str.equals(color.asString()))
                return color;
        return null;
    }
}
