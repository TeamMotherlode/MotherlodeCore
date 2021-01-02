package motherlode.buildingblocks.block.stateproperty;

import net.minecraft.block.MapColor;
import net.minecraft.util.DyeColor;
import net.minecraft.util.StringIdentifiable;

public enum BlockDyeColor implements StringIdentifiable {
    UNCOLORED("uncolored", MapColor.STONE),
    WHITE("white", MapColor.WHITE),
    LIGHT_GRAY("light_gray", MapColor.LIGHT_GRAY),
    GRAY("gray", MapColor.GRAY),
    BLACK("black", MapColor.BLACK),
    BROWN("brown", MapColor.BROWN),
    RED("red", MapColor.RED),
    ORANGE("orange", MapColor.ORANGE),
    YELLOW("yellow", MapColor.YELLOW),
    LIME("lime", MapColor.LIME),
    GREEN("green", MapColor.GREEN),
    BLUE("blue", MapColor.BLUE),
    CYAN("cyan", MapColor.CYAN),
    LIGHT_BLUE("light_blue", MapColor.LIGHT_BLUE),
    PINK("pink", MapColor.PINK),
    MAGENTA("magenta", MapColor.MAGENTA),
    PURPLE("purple", MapColor.PURPLE);

    String name;
    MapColor color;

    BlockDyeColor(String name, MapColor color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String asString() {
        return name;
    }

    public static BlockDyeColor fromDyeColor(DyeColor color) {
        return BlockDyeColor.valueOf(color.toString().toUpperCase());
    }
}
