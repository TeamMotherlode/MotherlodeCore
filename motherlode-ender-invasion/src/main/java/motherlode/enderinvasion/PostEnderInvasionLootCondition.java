package motherlode.enderinvasion;

import java.util.Set;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.util.JsonSerializer;
import motherlode.enderinvasion.component.EnderInvasionComponent;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public class PostEnderInvasionLootCondition implements LootCondition {
    private static final PostEnderInvasionLootCondition INSTANCE = new PostEnderInvasionLootCondition();

    private PostEnderInvasionLootCondition() {
    }

    public LootConditionType getType() {
        return EnderInvasion.POST_ENDER_INVASION_LOOT_CONDITION;
    }

    public Set<LootContextParameter<?>> getRequiredParameters() {
        return ImmutableSet.of();
    }

    public boolean test(LootContext lootContext) {
        return EnderInvasion.STATE.get(lootContext.getWorld().getLevelProperties()).value()
            .ordinal() >= EnderInvasionComponent.State.ENDER_INVASION.ordinal();
    }

    public static LootCondition.Builder builder() {
        return () -> INSTANCE;
    }

    public static class Serializer implements JsonSerializer<PostEnderInvasionLootCondition> {
        public Serializer() {
        }

        public void toJson(JsonObject jsonObject, PostEnderInvasionLootCondition postEnderInvasionLootCondition, JsonSerializationContext jsonSerializationContext) {
        }

        public PostEnderInvasionLootCondition fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return PostEnderInvasionLootCondition.INSTANCE;
        }
    }
}
