package motherlode.core.registry;

import motherlode.core.Motherlode;
import motherlode.core.block.DefaultOreBlock;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.Target;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

/**
 * @author Indigo Amann
 */
public class MotherlodeFeatures {
	public static void init() {
		// CALLED TO MAINTAIN REGISTRY ORDER
	}

	public static void register() {
		Registry.BIOME.forEach(biome -> addToBiome(biome));
		RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> addToBiome(biome));
	}

	public static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
		return Registry.register(Registry.FEATURE, Motherlode.id(name), feature);
	}

	public static <C extends SurfaceConfig, F extends SurfaceBuilder<C>> F register(String name, F surfaceBuilder) {
		return Registry.register(Registry.SURFACE_BUILDER, Motherlode.id(name), surfaceBuilder);
	}

	public static void addToBiome(Biome biome) {
		if(biome.getCategory() == Category.NETHER){
			addOre(biome, Target.NETHERRACK, MotherlodeBlocks.CHARITE_ORE);
			addOre(biome, Target.NETHERRACK, MotherlodeBlocks.ECHERITE_ORE);
			addOre(biome, Target.NETHERRACK, MotherlodeBlocks.ADAMANTITE_ORE);
		}
		else if(biome.getCategory() == Category.THEEND){

		}
		else{
			addOre(biome, Target.NATURAL_STONE, MotherlodeBlocks.COPPER_ORE);
			addOre(biome, Target.NATURAL_STONE, MotherlodeBlocks.SILVER_ORE);
			addOre(biome, Target.NATURAL_STONE, MotherlodeBlocks.TITANIUM_ORE);
			addOre(biome, Target.NATURAL_STONE, MotherlodeBlocks.AMETHYST_ORE);
			addOre(biome, Target.NATURAL_STONE, MotherlodeBlocks.RUBY_ORE);
			addOre(biome, Target.NATURAL_STONE, MotherlodeBlocks.SAPPHIRE_ORE);
			addOre(biome, Target.NATURAL_STONE, MotherlodeBlocks.TOPAZ_ORE);
		}
	}
	
	private static void addOre(Biome biome, Target canReplaceIn, Block block) {
		DefaultOreBlock ore = (DefaultOreBlock) block;
		biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, Feature.ORE.configure(
				new OreFeatureConfig(canReplaceIn, ore.getBlockInstance().getDefaultState(), ore.veinSize())).createDecoratedFeature(Decorator.COUNT_RANGE.configure(
				new RangeDecoratorConfig(ore.veinsPerChunk(), ore.minY(), ore.minY(), ore.maxY()))));
	}

}
