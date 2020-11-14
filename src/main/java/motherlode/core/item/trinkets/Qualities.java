package motherlode.core.item.trinkets;

import net.minecraft.text.BaseText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public enum Qualities {
    FAULTY(new TranslatableText("quality.motherlode.faulty").formatted(Formatting.GRAY)),
    AQUATIC(new TranslatableText("quality.motherlode.aquatic").formatted(Formatting.AQUA)),
    PROTECTOR(new TranslatableText("quality.motherlode.protector").formatted(Formatting.GREEN)),
    STURDY(new TranslatableText("quality.motherlode.sturdy").formatted(Formatting.DARK_RED)),
    DEXTEROUS(new TranslatableText("quality.motherlode.dexterous").formatted(Formatting.RED));

    private final Text text;

    public Text getText() {
        return this.text;
    }

    Qualities(Text text) {
        this.text = text;
    }
}
