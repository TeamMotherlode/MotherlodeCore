package motherlode.accessories;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import motherlode.accessories.api.Quality;
import motherlode.accessories.api.TrinketModifier;
import motherlode.accessories.api.TrinketTicker;
import com.google.common.collect.Multimap;

public enum Qualities implements Quality {
    FAULTY(new TranslatableText("quality.motherlode-accessories.faulty").formatted(Formatting.GRAY)),
    AQUATIC(new TranslatableText("quality.motherlode-accessories.aquatic").formatted(Formatting.AQUA), ((attributeEfficiency, player, stack) -> {
        if (player.isTouchingWaterOrRain()) {
            player.applyStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1, (int) attributeEfficiency - 1));
        }
    })),
    PROTECTOR(new TranslatableText("quality.motherlode-accessories.protector").formatted(Formatting.GREEN), (attributeEfficiency, player, stack) -> {
        if (player.getHealth() <= 6)
            player.applyStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, (int) (attributeEfficiency * 2), (int) attributeEfficiency));
    }),
    STURDY(new TranslatableText("quality.motherlode-accessories.sturdy").formatted(Formatting.DARK_RED),
        (map, attributeEfficiency, group, slot, uuid, stack) -> {
            map.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(uuid, "Knockback Resistance", attributeEfficiency, EntityAttributeModifier.Operation.ADDITION));
            map.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(uuid, "Max Health", attributeEfficiency * 2, EntityAttributeModifier.Operation.ADDITION));
        }),
    DEXTEROUS(new TranslatableText("quality.motherlode-accessories.dexterous").formatted(Formatting.RED),
        (map, attributeEfficiency, group, slot, uuid, stack) ->
            map.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(uuid, "Movement Speed", attributeEfficiency / 40, EntityAttributeModifier.Operation.ADDITION)));

    private final Text text;
    private final TrinketModifier modifier;
    private final TrinketTicker ticker;

    Qualities(Text text) {
        this(text, null, null);
    }

    Qualities(Text text, TrinketModifier modifier) {
        this(text, modifier, null);
    }

    Qualities(Text text, TrinketTicker ticker) {
        this(text, null, ticker);
    }

    Qualities(Text text, TrinketModifier modifier, TrinketTicker ticker) {
        this.text = text;
        this.modifier = modifier;
        this.ticker = ticker;
    }

    @Override
    public Text getText() {
        return this.text;
    }

    @Override
    public void putModifiers(Multimap<EntityAttribute, EntityAttributeModifier> map, float attributeEfficiency, String group, String slot, UUID uuid, ItemStack stack) {
        Optional.ofNullable(this.modifier)
            .ifPresent(modifier -> modifier.putModifiers(map, attributeEfficiency, group, slot, uuid, stack));
    }

    @Override
    public void tick(float attributeEfficiency, PlayerEntity player, ItemStack stack) {
        Optional.ofNullable(this.ticker).ifPresent(tick -> tick.tick(attributeEfficiency, player, stack));
    }
}
