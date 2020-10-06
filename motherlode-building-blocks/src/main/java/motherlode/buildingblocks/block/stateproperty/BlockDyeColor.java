package motherlode.buildingblocks.block.stateproperty;

import net.minecraft.block.MaterialColor;
import net.minecraft.util.DyeColor;
import net.minecraft.util.StringIdentifiable;

public enum BlockDyeColor implements StringIdentifiable {

    UNCOLORED("uncolored", MaterialColor.STONE),
    WHITE("white", MaterialColor.WHITE),
    LIGHT_GRAY("light_gray", MaterialColor.LIGHT_GRAY),
    GRAY("gray", MaterialColor.GRAY),
    BLACK("black", MaterialColor.BLACK),
    BROWN("brown", MaterialColor.BROWN),
    RED("red", MaterialColor.RED),
    ORANGE("orange", MaterialColor.ORANGE),
    YELLOW("yellow", MaterialColor.YELLOW),
    LIME("lime", MaterialColor.LIME),
    GREEN("green", MaterialColor.GREEN),
    BLUE("blue", MaterialColor.BLUE),
    CYAN("cyan", MaterialColor.CYAN),
    LIGHT_BLUE("light_blue", MaterialColor.LIGHT_BLUE),
    PINK("pink", MaterialColor.PINK),
    MAGENTA("magenta", MaterialColor.MAGENTA),
    PURPLE("purple", MaterialColor.PURPLE);

    String name;
    MaterialColor color;
    BlockDyeColor(String name, MaterialColor color) {
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
