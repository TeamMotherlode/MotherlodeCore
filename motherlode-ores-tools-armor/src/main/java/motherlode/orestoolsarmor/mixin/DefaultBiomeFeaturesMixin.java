package motherlode.orestoolsarmor.mixin;

import motherlode.orestoolsarmor.MotherlodeOresToolsArmorBlocks;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {

    @Inject(method = "addDefaultOres", at = @At("TAIL"))
    private static void addMotherlodeOverworldOres(GenerationSettings.Builder builder, CallbackInfo ci) {

        MotherlodeOresToolsArmorBlocks.COPPER_ORE.accept(builder);
        MotherlodeOresToolsArmorBlocks.SILVER_ORE.accept(builder);
        MotherlodeOresToolsArmorBlocks.TITANIUM_ORE.accept(builder);
        MotherlodeOresToolsArmorBlocks.ADAMANTITE_ORE.accept(builder);
        MotherlodeOresToolsArmorBlocks.AMETHYST_ORE.accept(builder);
        MotherlodeOresToolsArmorBlocks.HOWLITE_ORE.accept(builder);
        MotherlodeOresToolsArmorBlocks.RUBY_ORE.accept(builder);
        MotherlodeOresToolsArmorBlocks.SAPPHIRE_ORE.accept(builder);
        MotherlodeOresToolsArmorBlocks.TOPAZ_ORE.accept(builder);
        MotherlodeOresToolsArmorBlocks.ONYX_ORE.accept(builder);
    }

    @Inject(method = "addNetherMineables", at = @At("TAIL"))
    private static void addMotherlodeNetherOres(GenerationSettings.Builder builder, CallbackInfo ci) {

        MotherlodeOresToolsArmorBlocks.CHARITE_ORE.accept(builder);
        MotherlodeOresToolsArmorBlocks.ECHERITE_ORE.accept(builder);
    }
}
